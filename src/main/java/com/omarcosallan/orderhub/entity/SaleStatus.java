package com.omarcosallan.orderhub.entity;

import lombok.Getter;

@Getter
public enum SaleStatus {
    PENDING_PAYMENT("Aguardando Pagamento"),
    PAID("Pago"),
    FAILED("Falha no Pagamento"),
    READY_TO_SHIP("Pronto para Envio"),
    SHIPPED("Enviado"),
    COMPLETED("Concluído"),
    CANCELLED("Cancelado");

    private final String description;

    SaleStatus(String description) {
        this.description = description;
    }
}
