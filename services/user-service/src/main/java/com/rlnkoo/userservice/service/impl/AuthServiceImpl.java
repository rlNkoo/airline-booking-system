package com.rlnkoo.userservice.service.impl;

import com.rlnkoo.dto.UserDTO;
import com.rlnkoo.enums.UserRole;
import com.rlnkoo.payload.response.AuthResponse;
import com.rlnkoo.userservice.config.JwtProvider;
import com.rlnkoo.userservice.mapper.UserMapper;
import com.rlnkoo.userservice.model.User;
import com.rlnkoo.userservice.repository.UserRepository;
import com.rlnkoo.userservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.webmvc.autoconfigure.WebMvcProperties;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Override
    public AuthResponse singUp(UserDTO request) throws Exception {
        User existingUser = userRepository.findByEmail(request.getEmail());
        if (existingUser != null) {
            throw new Exception("Email already registered");
        }

        if (request.getRole() == UserRole.ROLE_SYSTEM_ADMIN) {
            throw new Exception("You cannot sign up system admin!");
        }

        User newUser = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phone(request.getPhone())
                .role(request.getRole())
                .fullName(request.getFullName())
                .lastLogin(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        User savedUser = userRepository.save(newUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(savedUser.getEmail(), savedUser.getPassword());
        String jwt = jwtProvider.generateToken(authentication, savedUser.getId());

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setUser(UserMapper.toDTO(savedUser));
        authResponse.setTitle("Welcome!");
        authResponse.setMessage("Registered successfully!");

        return authResponse;
    }

    @Override
    public AuthResponse login(String email, String password) {
        return null;
    }

}