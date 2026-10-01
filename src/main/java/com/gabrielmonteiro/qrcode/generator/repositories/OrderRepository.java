package com.gabrielmonteiro.qrcode.generator.repositories;

import com.gabrielmonteiro.qrcode.generator.entities.Order;
import com.gabrielmonteiro.qrcode.generator.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    // O Spring lê esse nome e gera o SQL: SELECT * FROM tb_orders WHERE table_id = ? AND status = ?
    Optional<Order> findByTableIdAndStatus(Long tableId, OrderStatus status);
}
