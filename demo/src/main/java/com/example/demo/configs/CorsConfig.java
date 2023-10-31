package com.example.demo.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173")
                .allowedMethods("*")
                .allowedHeaders("*") // Разрешите передавать все заголовки
                .allowCredentials(true) // Разрешите передавать учетные данные (например, куки) между источниками
                .maxAge(3600); // Установите значение времени в секундах, на которое предварительные запросы могут быть кэшированы
    }
}
