package com.gabrielmonteiro.qrcode.generator.controller;

import com.gabrielmonteiro.qrcode.generator.dto.RestaurantTableRequestDto;
import com.gabrielmonteiro.qrcode.generator.dto.RestaurantTableResponseDto;
import com.gabrielmonteiro.qrcode.generator.service.RestaurantTableService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tables")
@CrossOrigin // Essencial para o seu painel Angular conseguir acessar a API depois
public class RestaurantTableController {

    private final RestaurantTableService tableService;

    // Injeção de dependência pelo construtor
    public RestaurantTableController(RestaurantTableService tableService) {
        this.tableService = tableService;
    }

    @PostMapping
    public ResponseEntity<RestaurantTableResponseDto> create(@RequestBody @Valid RestaurantTableRequestDto dto) {
        RestaurantTableResponseDto response = tableService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<RestaurantTableResponseDto>> findAll() {
        return ResponseEntity.ok(tableService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantTableResponseDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(tableService.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        tableService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}