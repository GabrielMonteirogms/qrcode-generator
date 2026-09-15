package com.gabrielmonteiro.qrcode.generator.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_tables")
@Getter
@Setter
@NoArgsConstructor
public class RestaurantTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false, unique = true)
    private Integer tableNumber;

    // URL pública do QR Code gerado e armazenado no AWS S3
    @Column(nullable = false)
    private String qrCodeUrl;

    // Controla se a mesa está disponível ou ocupada no momento
    @Column(nullable = false)
    private Boolean active = true;

    public RestaurantTable(Integer tableNumber, String qrCodeUrl) {
        this.tableNumber = tableNumber;
        this.qrCodeUrl = qrCodeUrl;
        this.active = true;
    }
}