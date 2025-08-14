package com.omarcosallan.orderhub.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CNPJ;

public record CustomerDTO(@NotBlank(message = "Nome é obrigatório") String companyName,
                          @CNPJ(message = "CNPJ deve ter formato válido") String cnpj,
                          @Email(message = "Email deve ter formato válido")
                          @NotBlank(message = "Email é obrigatório") String email,
                          @NotBlank(message = "Telefone é obrigatório") String phone,
                          Long ownerId,
                          @Valid @NotNull AddressDTO address) {
}
