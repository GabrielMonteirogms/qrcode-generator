package com.gabrielmonteiro.qrcode.generator.dto;

import java.math.BigDecimal;

public record ProductResponseDto(
        Long id,
        String name,
        BigDecimal price,
        String description,
        Boolean available,
        String category,
        String imageUrl
) {
}
