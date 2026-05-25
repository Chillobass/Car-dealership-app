package ru.burunsuzian;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EntityScan(basePackages = {"ru.burunsuzian.data.jpa"})
@EnableJpaRepositories(basePackages = {"ru.burunsuzian.data.persistence.repositories"})
@SpringBootApplication
public class OrderApplication {
    public static void main(String[] args){

        SpringApplication.run(OrderApplication.class, args);
        System.out.println("http://localhost:8083/swagger-ui/index.html");
    }
}