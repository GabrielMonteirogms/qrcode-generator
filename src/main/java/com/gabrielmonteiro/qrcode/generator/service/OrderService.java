package com.gabrielmonteiro.qrcode.generator.service;

import com.gabrielmonteiro.qrcode.generator.dto.AddItemRequestDto;
import com.gabrielmonteiro.qrcode.generator.dto.AddItemResponseDto;
import com.gabrielmonteiro.qrcode.generator.entities.Order;
import com.gabrielmonteiro.qrcode.generator.entities.OrderItem;
import com.gabrielmonteiro.qrcode.generator.entities.Product;
import com.gabrielmonteiro.qrcode.generator.entities.RestaurantTable;
import com.gabrielmonteiro.qrcode.generator.enums.OrderStatus;
import com.gabrielmonteiro.qrcode.generator.repositories.OrderItemRepository;
import com.gabrielmonteiro.qrcode.generator.repositories.OrderRepository;
import com.gabrielmonteiro.qrcode.generator.repositories.ProductRepository;
import com.gabrielmonteiro.qrcode.generator.repositories.RestaurantTableRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final RestaurantTableRepository restaurantTableRepository;
    private final OrderItemRepository orderItemRepository;
    public AddItemResponseDto addItemOrder(AddItemRequestDto dto, Long tableId) { // Tirei o productId inútil daqui

        // 1. Busca ou cria a comanda (Isso aqui tá perfeito, não mexe)
        Order order = orderRepository.findByTableIdAndStatus(tableId, OrderStatus.OPEN)
                .orElseGet(() -> {
                    RestaurantTable table = restaurantTableRepository.findById(tableId)
                            .orElseThrow(() -> new RuntimeException("Mesa não encontrada na base de dados."));

                    Order newOrder = new Order();
                    newOrder.setTable(table);
                    newOrder.setStatus(OrderStatus.OPEN);

                    return orderRepository.save(newOrder);
                });

        // 2. Busca o Produto de forma SIMPLES e DIRETA
        Product product = productRepository.findById(dto.productId())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado na base de dados."));

        // 3. Monta o Item da Comanda
        OrderItem item = new OrderItem();
        item.setOrder(order);           // Pendura na comanda do passo 1
        item.setProduct(product);       // Pendura o produto do passo 2
        item.setQuantity(dto.quantity()); // Pega a quantidade do DTO

        // REGRA DE NEGÓCIO DE SÊNIOR: O SNAPSHOT DE PREÇO!
        item.setUnitPrice(product.getPrice()); // Supondo que na sua entidade Product seja getPrice() ou getUnitPrice()

        // 4. Salva o item no banco
        OrderItem savedItem = orderItemRepository.save(item);

        // 5. Retorna o DTO cumprindo o contrato do método
        return new AddItemResponseDto(
                savedItem.getId(),
                product.getName(),
                savedItem.getQuantity(),
                savedItem.getUnitPrice(),
                savedItem.getSubTotal() // Supondo que a sua entidade OrderItem tenha esse método calculando unitPrice * quantity
        );
    }
}