package com.omarcosallan.orderhub.dto;

import com.omarcosallan.orderhub.entity.ItemType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record ItemDTO(@NotBlank String ncmCode,
                      @NotBlank
                      @Size(min = 5, message = "Nome deve ter pelo menos 5 caracteres") String name,
                      String description,
                      String slug,
                      @NotNull
                      @DecimalMin(value = "0.01", message = "Preço deve ser maior que zero") BigDecimal price,
                      @NotNull Integer stockQuantity,
                      @NotNull ItemType type) {
}
