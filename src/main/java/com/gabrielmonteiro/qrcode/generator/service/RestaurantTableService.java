package com.gabrielmonteiro.qrcode.generator.service;

import com.gabrielmonteiro.qrcode.generator.dto.RestaurantTableRequestDto;
import com.gabrielmonteiro.qrcode.generator.dto.RestaurantTableResponseDto;
import com.gabrielmonteiro.qrcode.generator.dto.QrCodeGenerateResponse;
import com.gabrielmonteiro.qrcode.generator.entities.RestaurantTable;
import com.gabrielmonteiro.qrcode.generator.repositories.RestaurantTableRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class RestaurantTableService {

    private final RestaurantTableRepository tableRepository;
    private final QrCodeGenerateService qrCodeGenerateService; // Usando o seu serviço!

    public RestaurantTableService(RestaurantTableRepository tableRepository, QrCodeGenerateService qrCodeGenerateService) {
        this.tableRepository = tableRepository;
        this.qrCodeGenerateService = qrCodeGenerateService;
    }

    public RestaurantTableResponseDto create(RestaurantTableRequestDto dto) {

        //validar mesa existente
        if (tableRepository.existsByTableNumber(dto.tableNumber())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "A mesa numero " + dto.tableNumber() + " ja esta cadastrada.");
        }


        String  frontendMenuUrl = "https://meucardapio.com.br/menu?table=" + dto.tableNumber();

        try {
            // gera o qrcode e faz upload para o S3
            QrCodeGenerateResponse qrResponse = qrCodeGenerateService.generateAndUploadCode(frontendMenuUrl);

            // Salva no banco de dados com a URL que voltou do S3
            RestaurantTable table = new RestaurantTable(dto.tableNumber(), qrResponse.url());
            RestaurantTable savedTable = tableRepository.save(table);

            return toResponseDto(savedTable);

        } catch (Exception e) {

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao gerar e salvar o QR Code: " + e.getMessage());
        }
    }

    public List<RestaurantTableResponseDto> findAll() {
        return tableRepository.findAll()
                .stream()
                .map(this::toResponseDto)
                .toList();
    }

    public RestaurantTableResponseDto findById(Long id) {
        RestaurantTable table = tableRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Mesa nao encontrada com o ID: " + id));
        return toResponseDto(table);
    }

    public void deleteById(Long id) {
        if (!tableRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Mesa nao encontrada com o ID: " + id);
        }
        tableRepository.deleteById(id);
    }

    private RestaurantTableResponseDto toResponseDto(RestaurantTable table) {
        return new RestaurantTableResponseDto(
                table.getId(),
                table.getTableNumber(),
                table.getQrCodeUrl(),
                table.getActive()
        );
    }
}