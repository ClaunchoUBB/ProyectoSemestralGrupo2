package choripan_solutions.db_exporter.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Desactiva CSRF para pruebas con Postman
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login", "/public/**").permitAll() // Permitir rutas públicas
                .anyRequest().permitAll() // Permite todo temporalmente
            )
            .httpBasic(httpBasic -> {}); // Habilita autenticación básica

        return http.build();
    }
}

