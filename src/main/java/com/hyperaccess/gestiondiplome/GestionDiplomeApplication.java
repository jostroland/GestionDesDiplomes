package com.hyperaccess.gestiondiplome;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
@SpringBootApplication
@EnableJpaAuditing
public class GestionDiplomeApplication {

    public static void main(String[] args) {
        SpringApplication.run(GestionDiplomeApplication.class, args);
    }


}
