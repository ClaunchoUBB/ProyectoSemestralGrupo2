package choripan_solutions.db_exporter.Config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import choripan_solutions.db_exporter.Modelo.Usuario;
import choripan_solutions.db_exporter.Repositorio.UsuarioRepositorio;

@Component
public class AdminUserInitializer implements CommandLineRunner {

    private final UsuarioRepositorio usuarioRepositorio;
    private final PasswordEncoder passwordEncoder;

    public AdminUserInitializer(UsuarioRepositorio usuarioRepositorio, PasswordEncoder passwordEncoder) {
        this.usuarioRepositorio = usuarioRepositorio;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        // Verificamos si ya existe un usuario con rol de Administrador (rol = 1)
        if (usuarioRepositorio.existsByRol(1)) {
            System.out.println("Ya existe un usuario administrador. No se creará uno nuevo.");
            return;
        }

        // Si no existe, creamos uno por defecto
        System.out.println("No se encontró un usuario administrador. Creando uno por defecto...");
        Usuario admin = new Usuario();
        admin.setRut(11111111); // Puedes cambiar este RUT
        admin.setNombre1("Admin");
        admin.setApellido1("Principal");
        admin.setCorreo("admin@choripan.cl");
        admin.setRol(1); // 1 = ADMINISTRADOR
        admin.setActivo(true);
        admin.setPasswordHash(passwordEncoder.encode("admin123")); // ¡Cambia esta contraseña!

        usuarioRepositorio.save(admin);
        System.out.println("Usuario administrador por defecto creado con éxito.");
    }
}