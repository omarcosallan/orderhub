package com.omarcosallan.orderhub.dto;

import com.omarcosallan.orderhub.entity.SaleStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record SaleResponseDTO(Long id,
                              SaleStatus status,
                              Long sellerId,
                              Long customerId,
                              List<SaleItemResponseDTO> items,
                              BigDecimal totalAmount,
                              LocalDateTime createdAt) {
}
