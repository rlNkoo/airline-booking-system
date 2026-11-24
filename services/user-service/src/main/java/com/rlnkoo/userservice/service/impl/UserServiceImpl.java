package com.rlnkoo.userservice.service.impl;

import com.rlnkoo.dto.UserDTO;
import com.rlnkoo.userservice.mapper.UserMapper;
import com.rlnkoo.userservice.model.User;
import com.rlnkoo.userservice.repository.UserRepository;
import com.rlnkoo.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDTO getUserByEmail(String email) throws Exception {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new Exception("User does not exist");
        }

        return UserMapper.toDTO(user);
    }

    @Override
    public UserDTO getUserById(Long id) throws Exception {
        User user = userRepository.findById(id).orElseThrow(() -> new Exception( "User does not exist"));

        return UserMapper.toDTO(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users=userRepository.findAll();

        return UserMapper.toDTOList(users);
    }
}