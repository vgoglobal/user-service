package de.exchange.services;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    void store(MultipartFile file, String fileName) throws Exception;
}