package com.gabrielmonteiro.qrcode.generator.enums;

public enum OrderStatus {
    OPEN, // cliente esta realizando o pedido
    PREPARING, // pedido esta sendo preparado
    READY, // pedido esta pronto para ser entregue
    DELIVERED, // pedido foi entregue
    CANCELED // pedido foi cancelado
}
