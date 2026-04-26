package com.vtduarte.pizzahub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Projeto Spring Boot
 * Os seguintes modulos foram selecionados:
 * - Spring Web
 * - Spring Data JPA
 * - H2 Database
 * - OpenFeign
 * - Lombok
 * - SpringDoc OpenAPI
 *
 * @author vtduarte
 */

@SpringBootApplication
@EnableFeignClients
public class PizzahubApplication {

    public static void main(String[] args) {
        SpringApplication.run(PizzahubApplication.class, args);
    }

}
