package com.omarcosallan.orderhub.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

public record SellerDTO(@NotBlank(message = "Nome é obrigatório") String name,
                        @CPF(message = "CPF deve ter formato válido") String cpf,
                        LocalDate birthDate,
                        Double commissionRate,
                        Long ownerId,
                        @NotBlank(message = "Telefone é obrigatório") String phone,
                        @Valid @NotNull AddressDTO address) {
}
