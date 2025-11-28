package choripan_solutions.db_exporter.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import choripan_solutions.db_exporter.Repositorio.UsuarioRepositorio;
import choripan_solutions.db_exporter.auth.JwtTokenProvider;
import choripan_solutions.db_exporter.auth.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // Habilita @PreAuthorize en los controladores
public class SecurityConfig {

    private final UsuarioRepositorio usuarioRepositorio;
    private final JwtTokenProvider tokenProvider;

    public SecurityConfig(UsuarioRepositorio usuarioRepositorio, JwtTokenProvider tokenProvider) {
        this.usuarioRepositorio = usuarioRepositorio;
        this.tokenProvider = tokenProvider;
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(tokenProvider, userDetailsService());
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll() // Permite el acceso al login
                        .anyRequest().authenticated() // Todas las demás peticiones requieren autenticación
                );

        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        // Esto define la lógica que se ejecutará CADA VEZ que un usuario intente autenticarse.
        // Spring le pasará el 'username' (nuestro RUT) a esta expresión lambda.
        return username -> usuarioRepositorio.findById(Integer.parseInt(username))
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con RUT: " + username));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // IMPORTANTE: Debes usar BCrypt para codificar las contraseñas en tu base de datos.
        // Si no lo haces, la autenticación fallará.
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}