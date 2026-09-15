package com.gabrielmonteiro.qrcode.generator.dto;

public record RestaurantTableRequestDto(
    Integer tableNumber,
    String qrCodeUrl,
    Boolean active
) {
}
