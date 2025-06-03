package com.example.academyspring2corsi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AcademySpring2CorsiApplication {

    public static void main(String[] args) {
        SpringApplication.run(AcademySpring2CorsiApplication.class, args);
    }

}
