package com.omarcosallan.orderhub.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.Set;

public record RegisterDTO(@NotBlank String name, @Email String email, @NotBlank String password, Set<String> roles) {
}
