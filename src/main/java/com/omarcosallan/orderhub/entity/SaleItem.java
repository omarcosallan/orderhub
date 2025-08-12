package com.omarcosallan.orderhub.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.omarcosallan.orderhub.entity.pk.SaleItemPK;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "tb_sale_item")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SaleItem {

    @EmbeddedId
    @JsonIgnore
    private SaleItemPK id = new SaleItemPK();

    @NotNull
    @Min(value = 1, message = "Quantidade deve ser maior que zero")
    @Column(nullable = false)
    private Integer quantity;

    @NotNull
    @DecimalMin(value = "0.01", message = "Preço unitário deve ser maior que zero")
    @Column(name = "unit_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPrice;

    @DecimalMin(value = "0.01", message = "Sub total deve ser maior que zero")
    @Column(name = "sub_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal subTotal;

    @DecimalMin(value = "0.0", inclusive = true, message = "Desconto não pode ser negativo")
    @DecimalMax(value = "100.0", inclusive = true, message = "Desconto não pode exceder 100%")
    private Double discount;

    public SaleItem(Sale sale, Item item, Integer quantity, Double discount, BigDecimal subTotal) {
        setSale(sale);
        setItem(item);
        this.quantity = quantity;
        this.unitPrice = getItem().getPrice();
        this.discount = discount;
        this.subTotal = subTotal;
    }

    @JsonIgnore
    public Sale getSale() {
        return id.getSale();
    }

    public void setSale(Sale sale) {
        id.setSale(sale);
    }

    @JsonIgnore
    public Item getItem() {
        return id.getItem();
    }

    public void setItem(Item item) {
        id.setItem(item);
    }
}
