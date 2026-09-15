package com.gabrielmonteiro.qrcode.generator.repositories;

import com.gabrielmonteiro.qrcode.generator.entities.RestaurantTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface RestaurantTableRepository extends JpaRepository<RestaurantTable, Long> {
    boolean existsByTableNumber(Integer tableNumber);
}
