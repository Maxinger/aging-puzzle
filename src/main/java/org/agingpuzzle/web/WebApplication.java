package org.agingpuzzle.web;

import nz.net.ultraq.thymeleaf.LayoutDialect;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan("org.agingpuzzle")
@EnableJpaRepositories("org.agingpuzzle.repo")
@EntityScan("org.agingpuzzle.model")
public class WebApplication {

    public static final String[] SUPPORTED_LANGUAGES = {"en", "ru"};

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class);
    }

    @Bean
    public LayoutDialect layoutDialect() {
        return new LayoutDialect();
    }
}

