package com.gabrielmonteiro.qrcode.generator.service;

import com.gabrielmonteiro.qrcode.generator.dto.QrCodeGenerateResponse;
import com.gabrielmonteiro.qrcode.generator.port.StoragePort;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

@Service
public class QrCodeGenerateService {

    private final StoragePort storage;

        public QrCodeGenerateService(StoragePort storage) {
            this.storage = storage;

        }
        public QrCodeGenerateResponse generateAndUploadCode(String text) throws WriterException, IOException {
            //para passa o texto recebido em string e transforma-lo para qrcode
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            //gerar o qrcode em bytes, que transforma a na imagem do qrcode (encodar)
            BitMatrix bitMatrix = qrCodeWriter.encode(text, com.google.zxing.BarcodeFormat.QR_CODE, 200, 200);

            ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
            byte[] pngQrCodeData = pngOutputStream.toByteArray();


            //Upload to storage service
            String url = storage.uploadFile(pngQrCodeData, UUID.randomUUID().toString(), "image/png");

            return new QrCodeGenerateResponse(url);
        }
}
