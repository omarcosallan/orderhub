package com.omarcosallan.orderhub.dto;

import com.omarcosallan.orderhub.entity.Address;

public record CustomerResponseDTO(Long id, String companyName, String cnpj, String email, String phone, String ownerName, String ownerEmail, Address address) {
}
