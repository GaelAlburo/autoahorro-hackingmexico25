package com.gaelalburo.hackingmx25.autoahorroapi.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

/**
 * Filtro de seguridad que autentica las solicitudes HTTP basándose en un token configurado.
 */
@Slf4j
@Component
public class TokenAuthenticatorFilter extends OncePerRequestFilter {

    @Value("${api.security.token}")
    private String configuredToken;

    /**
     * Procesa cada solicitud HTTP para validar el token de autorización.
     *
     * - Extrae el encabezado "Authorization" y verifica que comience con "Bearer ".
     * - Compara el token recibido con un token configurado.
     * - Si el token es válido, establece la autenticación con rol de usuario.
     * - Si el token es inválido o falta, registra un error.
     * - Continúa con el siguiente filtro en la cadena.
     *
     * @param request La solicitud HTTP entrante.
     * @param response La respuesta HTTP.
     * @param filterChain La cadena de filtros para continuar el procesamiento.
     * @throws ServletException En caso de error en el filtrado.
     * @throws IOException En caso de error de entrada/salida.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        log.info("Authorization header: {}", header);
        log.info("Token from config: {}", configuredToken);
        log.info("Request URI: {}", request.getRequestURI());

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);

            if (configuredToken.equals(token)) {
                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(
                                "user",
                                null,
                                List.of(() -> "ROLE_USER")
                        );

                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
            else {
                log.error("Invalid token provided: {}", token);
            }
        }
        else {
            log.error("Missing or malformed Authorization header");
        }
        filterChain.doFilter(request, response);
    }
}
