package com.gabrielmonteiro.qrcode.generator.port;

public interface StoragePort {

    String uploadFile(byte[] fileData, String fileName, String contentType);
}
