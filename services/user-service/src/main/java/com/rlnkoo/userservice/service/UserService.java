package com.rlnkoo.userservice.service;

import com.rlnkoo.dto.UserDTO;

import java.util.List;

public interface UserService {

    UserDTO getUserByEmail(String email) throws Exception;

    UserDTO getUserById(Long id) throws Exception;

    List<UserDTO> getAllUsers();
}