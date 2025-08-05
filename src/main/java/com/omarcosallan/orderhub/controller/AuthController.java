package com.omarcosallan.orderhub.controller;

import com.omarcosallan.orderhub.dto.LoginDTO;
import com.omarcosallan.orderhub.dto.RegisterDTO;
import com.omarcosallan.orderhub.dto.UserResponseDTO;
import com.omarcosallan.orderhub.service.AuthService;
import com.omarcosallan.orderhub.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    public AuthController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<UserResponseDTO> register(@Valid @RequestBody RegisterDTO dto) {
        UserResponseDTO user = userService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<Map<String, String>> login(@Valid @RequestBody LoginDTO dto) {
        String token = authService.login(dto);
        return ResponseEntity.ok(Map.of("token", token));
    }
}
