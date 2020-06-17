package com.bortni.config;

import com.bortni.service.util.GameStringGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class BeanConfig {

    @Bean
    public BCryptPasswordEncoder getBCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public GameStringGenerator gameStringGenerator() {
        return new GameStringGenerator();
    }
}
