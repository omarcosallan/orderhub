package com.omarcosallan.orderhub.service;

import com.omarcosallan.orderhub.entity.User;
import com.omarcosallan.orderhub.security.JWTUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;

    public AuthService(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    public String login(String email, String password) {
        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(email, password);
        Authentication auth = authenticationManager.authenticate(usernamePassword);
        return JWTUtil.generateToken(((User) auth.getPrincipal()).getEmail());
    }
}
