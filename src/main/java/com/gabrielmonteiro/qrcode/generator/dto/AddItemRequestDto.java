package com.gabrielmonteiro.qrcode.generator.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record AddItemRequestDto(
        @NotNull(message = "O ID do produto não pode ser nulo")
        Long productId,

        @NotNull(message = "A quantidade não pode ser nula")
        @Min(value = 1, message = "A quantidade deve ser de pelo menos 1")
        Integer quantity
) {
}