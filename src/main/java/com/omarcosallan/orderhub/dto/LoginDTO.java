package com.omarcosallan.orderhub.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginDTO(@Email(message = "Email deve ter formato válido") String email,
                       @NotBlank(message = "Senha é obrigatória") String password) {
}
