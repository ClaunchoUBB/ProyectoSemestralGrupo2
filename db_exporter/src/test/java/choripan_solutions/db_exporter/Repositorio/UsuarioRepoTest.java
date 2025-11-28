package choripan_solutions.db_exporter.Repositorio;

import choripan_solutions.db_exporter.Usuario.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

// Solo carga componentes relacionados con JPA/Hibernate
@DataJpaTest 
@DisplayName("Pruebas de Integración para UsuarioRepositorio")
class UsuarioRepositorioTest {

    @Autowired
    private TestEntityManager entityManager; // Utilidad para insertar datos en la BD de prueba

    @Autowired
    private UsuarioRepositorio usuarioRepositorio; // Asumimos que esta es tu interfaz de Repositorio

    @Test
    @DisplayName("Debe encontrar un Usuario por su RUT")
    void findByRut_DebeRetornarUsuarioExistente() {
        // ARRANGE (Preparar)
        Usuario usuario = new Usuario();
        usuario.setRut(12345678);
        usuario.setNombre1("Test");
        usuario.setRol(1); 
        usuario.setActivo(true);
        // Persistir el usuario en la BD en memoria
        entityManager.persist(usuario);
        entityManager.flush();

        // ACT (Ejecutar)
        // Asumimos que findByRut está definido en UsuarioRepositorio
        Usuario encontrado = usuarioRepositorio.findByRut(12345678).orElse(null);

        // ASSERT (Asegurar)
        assertThat(encontrado).isNotNull();
        assertThat(encontrado.getNombre1()).isEqualTo("Test");
        assertThat(encontrado.getActivo()).isTrue();
    }
    
    // Si tu Repositorio tiene un existsByRut(Integer rut)
    @Test
    @DisplayName("Debe verificar la existencia de un Usuario")
    void existsByRut_DebeRetornarVerdadero() {
        // ARRANGE
        Usuario usuario = new Usuario();
        usuario.setRut(98765432);
        usuario.setNombre1("Existe");
        usuario.setRol(2); 
        usuario.setActivo(true);
        entityManager.persist(usuario);
        entityManager.flush();

        // ACT & ASSERT
        assertTrue(usuarioRepositorio.existsById(98765432));
    }
}