package com.rlnkoo.userservice.service.impl;

import com.rlnkoo.dto.UserDTO;
import com.rlnkoo.enums.UserRole;
import com.rlnkoo.payload.response.AuthResponse;
import com.rlnkoo.userservice.config.JwtProvider;
import com.rlnkoo.userservice.mapper.UserMapper;
import com.rlnkoo.userservice.model.User;
import com.rlnkoo.userservice.repository.UserRepository;
import com.rlnkoo.userservice.service.AuthService;
import com.rlnkoo.userservice.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final CustomUserDetailsService customUserDetailsService;

    @Override
    public AuthResponse signUp(UserDTO request) throws Exception {
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
    public AuthResponse login(String email, String password) throws Exception {
        Authentication authentication = authenticate(email, password);

        User user = userRepository.findByEmail(email);
        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);

        String jwt = jwtProvider.generateToken(authentication, user.getId());

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setUser(UserMapper.toDTO(user));
        authResponse.setTitle("Welcome!");
        authResponse.setMessage("Login successfully!");

        return authResponse;
    }

    private Authentication authenticate(String email, String password) throws Exception {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new Exception("Invalid password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}