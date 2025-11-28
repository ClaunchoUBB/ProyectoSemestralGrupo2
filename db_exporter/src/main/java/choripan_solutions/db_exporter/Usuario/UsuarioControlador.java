package choripan_solutions.db_exporter.Usuario;

import choripan_solutions.db_exporter.Usuario.Usuario;
import choripan_solutions.db_exporter.Usuario.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController // Le dice a Spring que esta clase es un Controlador REST
@RequestMapping("/api/usuarios") // Todas las rutas en esta clase empezarán con "/api/usuarios"
public class UsuarioControlador {

    @Autowired // Inyecta el servicio
    private UsuarioServicio usuarioServicio;

    // --- CREATE ---
    // Responde a peticiones POST en /api/usuarios
    @PostMapping
    public Usuario crearUsuario(@RequestBody Usuario usuario) {
        // @RequestBody convierte el JSON de la petición en un objeto Usuario
        return usuarioServicio.crearUsuario(usuario);
    }

    // --- READ (Todos) ---
    // Responde a peticiones GET en /api/usuarios
    @GetMapping
    public List<Usuario> obtenerTodosLosUsuarios() {
        return usuarioServicio.obtenerTodosLosUsuarios();
    }

    // --- READ (Uno por RUT) ---
    // Responde a peticiones GET en /api/usuarios/12345678
    @GetMapping("/{rut}")
    public ResponseEntity<Usuario> obtenerUsuarioPorRut(@PathVariable Integer rut) {
        // @PathVariable toma el "rut" de la URL
        return usuarioServicio.obtenerUsuarioPorRut(rut)
                .map(usuario -> ResponseEntity.ok(usuario)) // Si lo encuentra, devuelve 200 OK
                .orElse(ResponseEntity.notFound().build()); // Si no, devuelve 404 Not Found
    }

    // --- UPDATE ---
    // Responde a peticiones PUT en /api/usuarios/12345678
    @PutMapping("/{rut}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable Integer rut, @RequestBody Usuario usuarioDetalles) {
        try {
            Usuario usuarioActualizado = usuarioServicio.actualizarUsuario(rut, usuarioDetalles);
            return ResponseEntity.ok(usuarioActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // --- DELETE ---
    // Responde a peticiones DELETE en /api/usuarios/12345678
    @DeleteMapping("/{rut}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Integer rut) {
        try {
            usuarioServicio.eliminarUsuario(rut);
            return ResponseEntity.ok().build(); // Devuelve 200 OK sin cuerpo
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // --- PATCH (Actualizar parcialmente) ---
    // Responde a peticiones PATCH en /api/usuarios/12345678
    @PatchMapping("/{rut}")
    public ResponseEntity<Usuario> actualizarUsuarioParcial(@PathVariable Integer rut, @RequestBody Usuario usuarioDetalles) {
        try {
            Usuario usuarioActualizado = usuarioServicio.actualizarUsuarioParcial(rut, usuarioDetalles);
            return ResponseEntity.ok(usuarioActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}