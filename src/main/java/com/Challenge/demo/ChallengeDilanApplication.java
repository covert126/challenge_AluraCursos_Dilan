package com.Challenge.demo;

import com.Challenge.demo.principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChallengeDilanApplication implements CommandLineRunner {

    private final Principal principal;

    // Spring inyecta Principal con su repositorio automáticamente
    public ChallengeDilanApplication(Principal principal) {
        this.principal = principal;
    }

    @Override
    public void run(String... args) {
        principal.muestraElMenu();
    }

    public static void main(String[] args) {
        SpringApplication.run(ChallengeDilanApplication.class, args);
    }
}
