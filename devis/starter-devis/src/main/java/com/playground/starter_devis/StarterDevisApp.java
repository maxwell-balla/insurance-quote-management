package com.playground.starter_devis;

import com.playground.core_devis.DomainDevisService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.resilience.annotation.EnableResilientMethods;

@SpringBootApplication
@EnableResilientMethods
@EntityScan(basePackages = "com.playground.adapter_devis.entity")
@EnableJpaRepositories(basePackages = "com.playground.adapter_devis.repository")
@ComponentScan(
        basePackages = {
                "com.playground.adapter_devis",
                "com.playground.core_devis",
                "com.playground.inproc"
        },
        includeFilters = @ComponentScan.Filter(
                type = FilterType.ANNOTATION,
                classes = DomainDevisService.class
        )
)
public class StarterDevisApp {

    public static void main(String[] args) {
        SpringApplication.run(StarterDevisApp.class, args);
    }
}
