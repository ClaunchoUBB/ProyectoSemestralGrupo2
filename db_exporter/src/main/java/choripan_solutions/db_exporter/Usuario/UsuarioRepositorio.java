package choripan_solutions.db_exporter.Usuario;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import choripan_solutions.db_exporter.Usuario.Usuario;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Integer> {
    public  Optional<Usuario> findByCorreo(String correo);

    public Optional <Usuario> findByRut(Integer rut);
}