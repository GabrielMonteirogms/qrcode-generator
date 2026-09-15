package com.gabrielmonteiro.qrcode.generator.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


@Entity
@Table (name = "tbl_products")
@Getter
@Setter
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private BigDecimal price;
    private String description;
    private Boolean available;
    private String category;
    private String imageUrl;

    public Product(String name, BigDecimal price, String description, Boolean available, String category, String imageUrl) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.available = available;
        this.category = category;
        this.imageUrl = imageUrl;
    }



}
