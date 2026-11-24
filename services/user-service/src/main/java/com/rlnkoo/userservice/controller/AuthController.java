package com.rlnkoo.userservice.controller;

import com.rlnkoo.dto.UserDTO;
import com.rlnkoo.payload.request.LoginRequest;
import com.rlnkoo.payload.response.AuthResponse;
import com.rlnkoo.userservice.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signUp(@RequestBody @Valid UserDTO userDTO) throws Exception {
        AuthResponse response = authService.signUp(userDTO);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid LoginRequest req) throws Exception {
        AuthResponse response = authService.login(req.getEmail(), req.getPassword());

        return ResponseEntity.ok(response);
    }
}
