package ru.burunsuzian;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.*;

@EnableScheduling
@EntityScan(basePackages = {"ru.burunsuzian.data.jpa"})
@EnableJpaRepositories(basePackages = {"ru.burunsuzian.data.persistence.repositories"})
@SpringBootApplication
public class StorageApplication {
    public static void main(String[] args){
        SpringApplication.run(StorageApplication.class, args);
        System.out.println("Storage service is running!");
        System.out.println("http://localhost:8082/swagger-ui/index.html");
    }
}
