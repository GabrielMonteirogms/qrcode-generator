package com.gabrielmonteiro.qrcode.generator.dto;

public record RestaurantTableResponseDto (
    Long id,
    Integer tableNumber,
    String qrCodeUrl,
    Boolean active
) {
}
