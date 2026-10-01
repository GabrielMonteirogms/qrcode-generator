package com.gabrielmonteiro.qrcode.generator.dto;

import java.math.BigDecimal;

public record AddItemResponseDto(
        Long orderItemId,
        String productName,
        Integer quantity,
        BigDecimal unitPrice,
        BigDecimal subTotal
) {
}