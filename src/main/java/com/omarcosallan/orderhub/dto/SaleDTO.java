package com.omarcosallan.orderhub.dto;

import java.util.Set;

public record SaleDTO(Long customerId, Long sellerId, Set<SaleItemDTO> items) {
}
