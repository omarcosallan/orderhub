package com.omarcosallan.orderhub.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.Set;

public record RegisterDTO(@NotBlank(message = "Nome é obrigatório") String name,
                          @Email(message = "Email deve ter formato válido") String email,
                          @NotBlank(message = "Senha é obrigatória") String password, Set<String> roles) {
}
