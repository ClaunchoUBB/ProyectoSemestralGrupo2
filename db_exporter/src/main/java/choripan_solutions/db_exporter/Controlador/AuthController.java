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
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getRut().toString(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // --- REGISTRO DE LOG DE LOGIN ---
        Usuario usuario = (Usuario) authentication.getPrincipal();
        Log loginLog = new Log();
        loginLog.setUsuario(usuario);
        loginLog.setDetalle(String.format("Usuario '%s' (RUT: %d) inició sesión.", usuario.getNombre1(), usuario.getRut()));
        logServicio.guardarLog(loginLog);
        // --- FIN DE REGISTRO ---

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new AuthResponse(jwt));
    }
}