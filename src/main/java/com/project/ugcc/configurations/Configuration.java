package com.project.ugcc.configurations;

import com.project.ugcc.services.fileService.FileStorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class Configuration {

    @Bean
    public CommandLineRunner run(final FileStorageService fileService) {
        return strings -> {
            fileService.deleteAll();
            fileService.init();
        };
    }
}
