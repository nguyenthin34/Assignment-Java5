package com.poly.service;


import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.stereotype.Service;

@Service
public class DotEnvService {
    public String getValueDotenv(String keyName){
        Dotenv dotenv = Dotenv.configure()
                .directory("./")
                .ignoreIfMalformed()
                .ignoreIfMissing()
                .load();
        return dotenv.get(keyName);
    }
}
