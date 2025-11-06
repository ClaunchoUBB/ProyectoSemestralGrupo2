package choripan_solutions.db_exporter.Controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import choripan_solutions.db_exporter.Modelo.Log;
import choripan_solutions.db_exporter.Servicio.LogServicio;

@RestController
@RequestMapping("/api/logs")
public class LogControlador {

    @Autowired
    private LogServicio logServicio;

    @GetMapping
    public List<Log> getLogs() {
        return logServicio.obtenerTodosLosLogs();
    }

    @GetMapping("/usuario/{rut}")
    public List<Log> getLogsPorUsuario(@PathVariable Integer rut) {
        return logServicio.obtenerLogsPorUsuario(rut);
    }
    
    @PostMapping
    public Log crearLog(@RequestBody Log log) {
        return logServicio.guardarLog(log);
    }
}