package com.gabrielmonteiro.qrcode.generator.repositories;

import com.gabrielmonteiro.qrcode.generator.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
