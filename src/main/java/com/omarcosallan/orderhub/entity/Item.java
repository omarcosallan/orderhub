package com.omarcosallan.orderhub.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.text.Normalizer;

@Entity
@Table(name = "tb_items")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String ncmCode;

    @NotBlank
    @Size(min = 5, message = "Nome deve ter pelo menos 5 caracteres")
    @Column(nullable = false)
    private String name;

    private String description;
    private String slug;

    @NotNull
    @DecimalMin(value = "0.01", message = "Preço deve ser maior que zero")
    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @NotNull
    @Column(nullable = false)
    private Integer stockQuantity;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(nullable = false)
    private ItemType type;

    @PrePersist
    @PreUpdate
    protected void onCreate() {
        if (slug == null) {
            slug = Normalizer.normalize(name, Normalizer.Form.NFD).replaceAll("\\p{M}", "").replaceAll("[^\\w\\s]", "").trim().replaceAll("\\s+", "-").toLowerCase();
        }
    }
}
