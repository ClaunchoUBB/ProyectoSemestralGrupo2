package choripan_solutions.db_exporter.Controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import choripan_solutions.db_exporter.Modelo.Log;
import choripan_solutions.db_exporter.Modelo.Usuario;
import choripan_solutions.db_exporter.auth.AuthResponse;
import choripan_solutions.db_exporter.auth.JwtTokenProvider;
import choripan_solutions.db_exporter.auth.LoginRequest;
import choripan_solutions.db_exporter.Servicio.LogServicio;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private LogServicio logServicio;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticateUser(
            @Valid @RequestBody LoginRequest loginRequest,
            HttpServletResponse response) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getRut().toString(),
                        loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        Usuario usuario = (Usuario) authentication.getPrincipal();

        String jwt = tokenProvider.generateToken(authentication);

        Cookie cookie = new Cookie("jwt", jwt);
        cookie.setHttpOnly(true);
        cookie.setSecure(false); // true en prod HTTPS
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 8); // 8 horas

        response.addCookie(cookie);

        return ResponseEntity.ok(
                new AuthResponse(
                        mapRol(usuario.getRol()),
                        usuario.getRut(),
                        usuario.getNombre1()));
    }

    private String mapRol(int rol) {
        return switch (rol) {
            case 1 -> "ADMIN";
            case 2 -> "INVESTIGADOR";
            case 3 -> "RECLUTADOR";
            case 4 -> "MEDICO";
            default -> "INVITADO";
        };
    }

}