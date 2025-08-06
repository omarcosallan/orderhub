package com.omarcosallan.orderhub.service;

import com.omarcosallan.orderhub.dto.LoginDTO;
import com.omarcosallan.orderhub.entity.User;
import com.omarcosallan.orderhub.security.JWTUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;

    public AuthService(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    public String login(LoginDTO dto) {
        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(dto.email(), dto.password());
        Authentication auth = authenticationManager.authenticate(usernamePassword);
        return jwtUtil.generateToken(((User) auth.getPrincipal()).getEmail());
    }
}
