package choripan_solutions.db_exporter.Usuario;

import choripan_solutions.db_exporter.Usuario.Usuario;
import choripan_solutions.db_exporter.Usuario.UsuarioRepositorio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import jakarta.persistence.EntityManager; // Asegúrate que sea "jakarta.persistence"
import jakarta.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServicio {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    // private final BcryptPasswordEncoder passwordEncoder = new
    // BcryptPasswordEncoder();

    // Inyectamos el EntityManager (lo necesitamos para 'merge')
    @PersistenceContext
    private EntityManager entityManager;

    // --- CREATE ---
    @Transactional
    public Usuario crearUsuario(Usuario usuario) {

        // (Buena práctica) Verificar si el usuario ya existe.
        if (usuario.getRut() != null && usuarioRepositorio.existsById(usuario.getRut())) {
            throw new RuntimeException("Error: Ya existe un usuario con el RUT: " + usuario.getRut());
        }

        // Usar save para crear un nuevo usuario
        Usuario managedUsuario = usuarioRepositorio.save(usuario);
        return managedUsuario;
    }

    // --- READ ---
    public List<Usuario> obtenerTodosLosUsuarios() {
        return usuarioRepositorio.findAll();
    }

    public Optional<Usuario> obtenerUsuarioPorRut(Integer rut) {
        return usuarioRepositorio.findById(rut);
    }

    // --- UPDATE ---
    // (Este método ya usaba 'save' correctamente)
    @Transactional
    public Usuario actualizarUsuario(Integer rut, Usuario usuarioDetalles) {
        Usuario usuario = usuarioRepositorio.findById(rut)
                .orElseThrow(() -> new RuntimeException("No se encontró el usuario con rut: " + rut));

        // ... (código de actualización de campos)
        usuario.setNombre1(usuarioDetalles.getNombre1());
        usuario.setNombre2(usuarioDetalles.getNombre2());
        usuario.setApellido1(usuarioDetalles.getApellido1());
        usuario.setApellido2(usuarioDetalles.getApellido2());
        usuario.setRol(usuarioDetalles.getRol());
        usuario.setNumero(usuarioDetalles.getNumero());
        usuario.setCorreo(usuarioDetalles.getCorreo());

        if (usuarioDetalles.getPasswordHash() != null && !usuarioDetalles.getPasswordHash().isEmpty()) {
            usuario.setPasswordHash(usuarioDetalles.getPasswordHash());
        }

        // 'save' aquí funciona bien porque el objeto 'usuario' fue
        // cargado de la BD y está "managed"
        return usuarioRepositorio.save(usuario);
    }

    // --- PATCH --- pa actualizar a media
    @Transactional
    public Usuario actualizarUsuarioParcial(Integer rut, Usuario usuarioDetalles) {
        Usuario usuario = usuarioRepositorio.findById(rut)
                .orElseThrow(() -> new RuntimeException("No se encontró el usuario con rut: " + rut));

        if (usuarioDetalles.getNombre1() != null)
            usuario.setNombre1(usuarioDetalles.getNombre1());
        if (usuarioDetalles.getNombre2() != null)
            usuario.setNombre2(usuarioDetalles.getNombre2());
        if (usuarioDetalles.getApellido1() != null)
            usuario.setApellido1(usuarioDetalles.getApellido1());
        if (usuarioDetalles.getApellido2() != null)
            usuario.setApellido2(usuarioDetalles.getApellido2());
        if (usuarioDetalles.getRol() != null)
            usuario.setRol(usuarioDetalles.getRol());
        if (usuarioDetalles.getNumero() != null)
            usuario.setNumero(usuarioDetalles.getNumero());
        if (usuarioDetalles.getCorreo() != null)
            usuario.setCorreo(usuarioDetalles.getCorreo());
        if (usuarioDetalles.getPasswordHash() != null && !usuarioDetalles.getPasswordHash().isEmpty()) {
            usuario.setPasswordHash(usuarioDetalles.getPasswordHash());
        }

        return usuarioRepositorio.save(usuario);
    }

    private Usuario actualizarUsuarioActivo(Integer rut, Boolean activo) {
        Usuario usuario = usuarioRepositorio.findById(rut)
                .orElseThrow(() -> new RuntimeException("No se encontró el usuario con rut: " + rut));

        usuario.setActivo(activo);

        return usuarioRepositorio.save(usuario);
    }

    public Usuario desactivUsuario(Integer rut) {
        return actualizarUsuarioActivo(rut, false);
    }

    public Usuario activarUsuario(Integer rut) {
        return actualizarUsuarioActivo(rut, true);
    }

    // --- DELETE ---
    @Transactional
    public void eliminarUsuario(Integer rut) {
        Usuario usuario = usuarioRepositorio.findById(rut)
                .orElseThrow(() -> new RuntimeException("No se encontró el usuario con rut: " + rut));

        usuarioRepositorio.delete(usuario);
    }
}