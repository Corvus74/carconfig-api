package com.applicationdemo.carconfig.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@Profile("dev")
@EnableMethodSecurity
@RequiredArgsConstructor
public class DevSecurityConfiguration {

    @Bean
    @SuppressWarnings("java:S4502") // Suppress SonarQube warning for CSRF disabling in stateless REST API never use dev in prod
    public SecurityFilterChain securityFilterChain(HttpSecurity http){
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // Alle Endpunkte ohne Token/Login freigeben!
                );
        return http.build();
    }
}


