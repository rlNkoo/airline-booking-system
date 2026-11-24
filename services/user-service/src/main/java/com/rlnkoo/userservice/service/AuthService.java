package com.rlnkoo.userservice.service;

import com.rlnkoo.dto.UserDTO;
import com.rlnkoo.payload.response.AuthResponse;

public interface AuthService {

    AuthResponse login(String email, String password) throws Exception;
    AuthResponse signUp(UserDTO request) throws Exception;
}
