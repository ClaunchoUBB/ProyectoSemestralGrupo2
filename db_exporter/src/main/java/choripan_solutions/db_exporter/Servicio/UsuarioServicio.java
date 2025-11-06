package choripan_solutions.db_exporter.Servicio;

import choripan_solutions.db_exporter.Modelo.Usuario;
import choripan_solutions.db_exporter.Modelo.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager; // Asegúrate que sea "jakarta.persistence"
import jakarta.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServicio {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

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

        // --- ¡ESTA ES LA LÍNEA MÁGICA! ---
        // 'merge' es la operación correcta para guardar un objeto "detached"
        // (un objeto con un ID que no está siendo gestionado por la sesión).
        // Si el RUT no existe, 'merge' hará un INSERT.
        // Si el RUT ya existe, 'merge' hará un UPDATE.
        Usuario managedUsuario = entityManager.merge(usuario);
        
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

    // --- DELETE ---
    @Transactional
    public void eliminarUsuario(Integer rut) {
        Usuario usuario = usuarioRepositorio.findById(rut)
                .orElseThrow(() -> new RuntimeException("No se encontró el usuario con rut: " + rut));
        
        usuarioRepositorio.delete(usuario);
    }
}