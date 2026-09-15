package com.gabrielmonteiro.qrcode.generator.service;

import com.gabrielmonteiro.qrcode.generator.dto.ProductRequestDto;
import com.gabrielmonteiro.qrcode.generator.dto.ProductResponseDto;
import com.gabrielmonteiro.qrcode.generator.entities.Product;
import com.gabrielmonteiro.qrcode.generator.repositories.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductResponseDto create(ProductRequestDto dto) {
        Product product = new Product(
                dto.name(),
                dto.price(),
                dto.description(),
                dto.available(),
                dto.category(),
                dto.imageUrl()
        );
        Product savedProduct = productRepository.save(product);
        return toResponseDto(savedProduct);
    }

    public List<ProductResponseDto> findAll() {
        return productRepository.findAll()
                .stream()
                .map(this::toResponseDto)
                .toList();
    }

    private ProductResponseDto toResponseDto(Product product) {
        return new ProductResponseDto(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getDescription(),
                product.getAvailable(),
                product.getCategory(),
                product.getImageUrl()
        );
    }

    // Buscar por ID
    public ProductResponseDto findById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado com o ID: " + id));
        return toResponseDto(product);
    }

    // Atualizar
    public ProductResponseDto update(Long id, ProductRequestDto dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado com o ID: " + id));

        product.setName(dto.name());
        product.setPrice(dto.price());
        product.setDescription(dto.description());
        product.setAvailable(dto.available());
        product.setCategory(dto.category());
        product.setImageUrl(dto.imageUrl());

        Product updatedProduct = productRepository.save(product);
        return toResponseDto(updatedProduct);
    }

    // Deletar por ID
    public void deleteById(Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Produto não encontrado com o ID: " + id);
        }
        productRepository.deleteById(id);
    }

}
