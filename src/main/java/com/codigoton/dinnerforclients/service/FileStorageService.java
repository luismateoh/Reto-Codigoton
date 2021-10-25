package com.codigoton.dinnerforclients.service;


import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    void init();

    void saveFilters(MultipartFile file);

    Resource loadFilters(String filename);

    void deleteAll();
}