package com.omarcosallan.orderhub.dto;

import com.omarcosallan.orderhub.entity.ItemType;

import java.math.BigDecimal;

public record ItemResponseDTO(String ncmCode,
                              String description,
                              String slug,
                              BigDecimal price,
                              Integer stockQuantity,
                              ItemType type) {
}
