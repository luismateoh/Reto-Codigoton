package com.codigoton.dinnerforclients;

import com.codigoton.dinnerforclients.service.FileStorageService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "DinnerForClients", version = "1.0", description = "Dinner for clients, Codigotón Bancolombia"))
public class DinnerForClientsApplication implements CommandLineRunner {
    @Resource
    private FileStorageService storageService;

    public static void main(String[] args) {
        SpringApplication.run(DinnerForClientsApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Override
    public void run(String... arg) throws Exception {
        storageService.deleteAll();
        storageService.init();
    }

}
