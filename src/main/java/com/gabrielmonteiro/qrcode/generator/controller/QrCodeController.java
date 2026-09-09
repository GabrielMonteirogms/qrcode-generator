package com.gabrielmonteiro.qrcode.generator.controller;
import com.gabrielmonteiro.qrcode.generator.dto.QrCodeGenerateRequest;
import com.gabrielmonteiro.qrcode.generator.dto.QrCodeGenerateResponse;
import com.gabrielmonteiro.qrcode.generator.service.QrCodeGenerateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/qrcode")
public class QrCodeController {

    private final QrCodeGenerateService qrCodeGenerateService;

    public QrCodeController(QrCodeGenerateService qrCodeService) {
        this.qrCodeGenerateService = qrCodeService;
    }


    @PostMapping
    public ResponseEntity<QrCodeGenerateResponse> generate(@RequestBody QrCodeGenerateRequest request) {

        try {
            QrCodeGenerateResponse response = this.qrCodeGenerateService.generateAndUploadCode(request.text());
                    return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
