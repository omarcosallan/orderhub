package com.omarcosallan.orderhub.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AddressDTO(@NotBlank(message = "Rua é obrigatória") String street,
                         @NotNull(message = "Número é obrigatório") Integer number,
                         @NotBlank(message = "Cidade é obrigatória") String city,
                         @NotBlank(message = "Estado é obrigatório") String state,
                         String complement,
                         @NotBlank(message = "Código postal é obrigatório") String postalCode) {
}
