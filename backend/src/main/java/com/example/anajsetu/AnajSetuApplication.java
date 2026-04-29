package com.example.anajsetu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AnajSetuApplication {
    public static void main(String[] args) {
        SpringApplication.run(AnajSetuApplication.class, args);
    }
}