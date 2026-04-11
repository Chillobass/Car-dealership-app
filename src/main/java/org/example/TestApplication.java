package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TestApplication {
    public static void main(String[] args){

        SpringApplication.run(TestApplication.class, args);
        System.out.println("http://localhost:8080/swagger-ui/index.html");
    }
}
