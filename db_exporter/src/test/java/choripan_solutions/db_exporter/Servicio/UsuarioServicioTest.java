package choripan_solutions.db_exporter.Servicio;


import java.util.List;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import choripan_solutions.db_exporter.Modelo.Usuario;
import choripan_solutions.db_exporter.Repositorio.UsuarioRepositorio;
import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioServicioTest{

    @Autowired
    private UsuarioRepositorio repo;

    @Autowired
    private UsuarioServicio service;

    private Usuario usr1;
    private Usuario usr2;

    @BeforeEach
    void setUp(){
        repo.deleteAll();

        usr1 = new Usuario();
        usr1.setRut(1111111);
        usr1.setNombre1("Valentina");
        usr1.setApellido1("Eriza");
        usr1.setRol(2);
        usr1.setPasswordHash("hash");
        usr1.setCorreo("valentina@test.cl");
        usr1.setActivo(true);

        usr2 = new Usuario();
        usr2.setRut(2222222);
        usr2.setNombre1("Dani");
        usr2.setApellido1("California");
        usr2.setRol(3);
        usr2.setPasswordHash("StadiumArcadium");
        usr2.setCorreo("rhcp@test.cl");
        usr2.setActivo(true);

        repo.save(usr1);
        repo.save(usr2);
    }

    @AfterEach
    void cleanUp(){
        repo.deleteAll();
    }

    @Test
    void crearUsuarioRutRepetido(){
        Usuario usrRepeat = new Usuario();
        usrRepeat.setRut(2222222);
        usrRepeat.setNombre1("Kurt");
        usrRepeat.setApellido1("Cobain");
        usrRepeat.setRol(1);
        usrRepeat.setPasswordHash("Nevermind");
        usrRepeat.setCorreo("nirvana@test.cl");
        usrRepeat.setActivo(false);

        assertThrows(RuntimeException.class, ()->service.crearUsuario(usrRepeat));


        List<Usuario> list = service.obtenerTodosLosUsuarios();
        
        Exception ex = assertThrows(RuntimeException.class,()->list.get(67));
        assertEquals(ex.getMessage(), "Index 67 out of bounds for length 2");

    }
}