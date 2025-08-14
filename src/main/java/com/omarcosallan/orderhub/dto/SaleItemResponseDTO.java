package com.omarcosallan.orderhub.dto;

import java.math.BigDecimal;

public record SaleItemResponseDTO(Integer quantity, BigDecimal unitPrice, BigDecimal subTotal, Double discount) {
}
