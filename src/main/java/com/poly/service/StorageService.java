package com.poly.service;

import java.io.IOException;
import java.nio.file.Path;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

    void init();

    void delete(String storedFilename) throws IOException;

    Path load(String filename);

    Resource loadAsResource(String fileName);

    void store(MultipartFile file, String storedFilename);

    String getStoredFileName(MultipartFile file, String id);

}
