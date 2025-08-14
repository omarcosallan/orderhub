package com.omarcosallan.orderhub.dto;

import com.omarcosallan.orderhub.entity.Address;

import java.time.LocalDate;

public record SellerResponseDTO(Long id, String name, String cpf, LocalDate hiringDate, LocalDate birthDate, Double commissionRate, String phone, OwnerResponseDTO owner, Address address) {
}
