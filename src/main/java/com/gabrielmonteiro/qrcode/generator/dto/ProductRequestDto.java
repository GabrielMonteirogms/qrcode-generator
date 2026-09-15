package com.gabrielmonteiro.qrcode.generator.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record ProductRequestDto(
        @NotBlank(message = "Name is required")
        String name,
        @Positive(message = "Price must be a positive value")
        BigDecimal price,
        String description,
        Boolean available,
        String category,
        String imageUrl
) {
}
