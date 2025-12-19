package choripan_solutions.db_exporter.Servicio;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import choripan_solutions.db_exporter.Modelo.Log;
import choripan_solutions.db_exporter.Modelo.Usuario;
import choripan_solutions.db_exporter.Repositorio.LogRepo;
import choripan_solutions.db_exporter.Repositorio.UsuarioRepositorio;
import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LogServicioTest {

    @Autowired
    private LogServicio logServ;

    @Autowired
    private LogRepo logRepo;

    @Autowired
    private UsuarioRepositorio usrRepo;

    private Usuario usr1;
    private Usuario usr2;

    @BeforeAll
    void setUp(){
        logRepo.deleteAll();
        usrRepo.deleteAll();

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

        usrRepo.saveAll(List.of(usr1,usr2));

        Log log1 = new Log();
        log1.setDetalle("Login exitoso");
        log1.setUsuario(usr1);

        Log log2 = new Log();
        log2.setDetalle("Creación de participante");
        log2.setUsuario(usr2);

        Log log3 = new Log();
        log3.setDetalle("Descarga de Excel");
        log3.setUsuario(usr2);

        Log log4 = new Log();
        log4.setDetalle("Log sin usuario"); // Caso borde
        log4.setUsuario(null);

        logRepo.saveAll(List.of(log1, log2, log3, log4));
    }


    @Test
    void testObtenerTodosLosLogs(){

        List<Log> listita = logServ.obtenerTodosLosLogs();

        assertEquals(listita.getFirst().getUsuario().getApellido1(), "Eriza");
        assertEquals(listita.get(1).getUsuario().getNombre1(),"Dani");
    }
    
    
}
