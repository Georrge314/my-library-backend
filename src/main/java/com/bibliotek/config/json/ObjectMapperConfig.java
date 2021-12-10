package com.bibliotek.config.json;

import com.bibliotek.config.json.serializer.LocalDateDesirializer;
import com.bibliotek.config.json.serializer.LocalDateSirializer;
import com.bibliotek.config.json.serializer.LocalDateTimeDesirializer;
import com.bibliotek.config.json.serializer.LocalDateTimeSirializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Configuration
public class ObjectMapperConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();

        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSirializer());
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDesirializer());
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSirializer());
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDesirializer());
        objectMapper.registerModule(javaTimeModule);

        return objectMapper;
    }
}
