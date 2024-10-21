package com.example.ecomarket_servicio_eventos.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable() // Desactiva CSRF (puedes activarlo más adelante si lo prefieres)
            .authorizeRequests()
            .anyRequest().authenticated()  // Todo acceso requiere estar autenticado
            .and()
            .oauth2ResourceServer()
            .jwt(); // Configura JWT como método de autenticación

        return http.build();
    }
}
