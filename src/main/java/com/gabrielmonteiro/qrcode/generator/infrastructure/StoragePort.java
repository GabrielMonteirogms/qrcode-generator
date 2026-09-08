package com.gabrielmonteiro.qrcode.generator.infrastructure;

public interface StoragePort {

    String uploadFile(byte[] fileData, String fileName, String contentType);
}
