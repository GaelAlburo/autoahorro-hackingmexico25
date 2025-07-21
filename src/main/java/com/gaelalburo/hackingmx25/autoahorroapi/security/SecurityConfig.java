package com.gaelalburo.hackingmx25.autoahorroapi.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuración de seguridad para la aplicación.
 */
@Configuration
public class SecurityConfig {
    private final TokenAuthenticatorFilter tokenFilter;

    public SecurityConfig(TokenAuthenticatorFilter tokenFilter) {
        this.tokenFilter = tokenFilter;
    }

    /**
     * Configura la cadena de filtros de seguridad para la aplicación.
     *x
     * - Desactiva la protección CSRF.
     * - Permite acceso sin autenticación a las rutas de Swagger.
     * - Requiere autenticación para cualquier otra petición.
     * - Añade un filtro personalizado para validar tokens antes del filtro de autenticación por usuario y contraseña.
     *
     * @param http Configuración de seguridad HTTP.
     * @return La cadena de filtros de seguridad configurada.
     * @throws Exception En caso de error en la configuración de seguridad.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}