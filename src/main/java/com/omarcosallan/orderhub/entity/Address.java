package com.omarcosallan.orderhub.entity;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Embeddable
public class Address {

    @NotBlank(message = "Rua é obrigatória")
    private String street;

    @NotNull(message = "Número é obrigatório")
    private Integer number;

    @NotBlank(message = "Cidade é obrigatória")
    private String city;

    @NotBlank(message = "Estado é obrigatório")
    private String state;

    private String complement;

    @NotBlank(message = "Código postal é obrigatório")
    private String postalCode;
}
