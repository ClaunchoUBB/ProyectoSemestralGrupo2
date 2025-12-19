package choripan_solutions.db_exporter.Servicio;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import choripan_solutions.db_exporter.Modelo.Log;
import choripan_solutions.db_exporter.Repositorio.LogRepo;
import choripan_solutions.db_exporter.Repositorio.UsuarioRepositorio;
import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
public class LogServicioTest {

    @Autowired
    private LogServicio logServ;

    @Autowired
    private LogRepo logRepo;

    @Autowired
    private UsuarioRepositorio usrRepo;

    @BeforeAll
    void setUp(){

    }


    @Test
    void testObtenerTodosLosLogs(){

        List<Log> listita = logServ.obtenerTodosLosLogs();

        assertEquals(listita.getFirst().getIdLog(),1);

    }
    
    
}
