package choripan_solutions.db_exporter.Controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
@PreAuthorize("hasRole('ADMINISTRADOR')") 
public class LogControlador {

    @Autowired
    private LogServicio logServicio;

    // Solo accesible para ADMIN por la anotación a nivel de clase
    @GetMapping
    public List<Log> getLogs() {
        return logServicio.obtenerTodosLosLogs();
    }

    // Solo accesible para ADMIN por la anotación a nivel de clase
    @GetMapping("/usuario/{rut}")
    public List<Log> getLogsPorUsuario(@PathVariable Integer rut) {
        return logServicio.obtenerLogsPorUsuario(rut);
    }
    
    // Este endpoint probablemente no debería existir, ya que los logs se crean
    // automáticamente. Lo comentamos para evitar que se puedan crear logs manuales.
    /*
    @PostMapping
    public Log crearLog(@RequestBody Log log) {
        return logServicio.guardarLog(log);
    }
    */
}