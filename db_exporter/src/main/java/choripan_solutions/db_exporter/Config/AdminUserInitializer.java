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
        // 1. Crear ADMIN si no existe (RUT: 11111111 / Pass: admin123)
        if (!usuarioRepositorio.existsById(11111111)) {
            System.out.println("Creando usuario Administrador por defecto...");
            Usuario admin = new Usuario();
            admin.setRut(11111111);
            admin.setNombre1("Admin");
            admin.setApellido1("Principal");
            admin.setCorreo("admin@choripan.cl");
            admin.setRol(1); // 1 = ADMINISTRADOR
            admin.setActivo(true);
            admin.setPasswordHash(passwordEncoder.encode("admin123"));
            usuarioRepositorio.save(admin);
        }

        // 2. Crear MÉDICO de prueba (RUT: 44444444 / Pass: medico123)
        if (!usuarioRepositorio.existsById(44444444)) {
            System.out.println("Creando usuario Médico de prueba...");
            Usuario medico = new Usuario();
            medico.setRut(44444444);
            medico.setNombre1("Gregory");
            medico.setApellido1("House");
            medico.setCorreo("house@hospital.cl");
            medico.setRol(4); // 4 = MEDICO
            medico.setActivo(true);
            medico.setPasswordHash(passwordEncoder.encode("medico123"));
            usuarioRepositorio.save(medico);
        }

        // 3. Crear RECLUTADOR de prueba (RUT: 33333333 / Pass: recluta123)
        if (!usuarioRepositorio.existsById(33333333)) {
            System.out.println("Creando usuario Reclutador de prueba...");
            Usuario recluta = new Usuario();
            recluta.setRut(33333333);
            recluta.setNombre1("Reclutador");
            recluta.setApellido1("Test");
            recluta.setCorreo("recluta@hospital.cl");
            recluta.setRol(3); // 3 = RECLUTADOR
            recluta.setActivo(true);
            recluta.setPasswordHash(passwordEncoder.encode("recluta123"));
            usuarioRepositorio.save(recluta);
        }
    }
}