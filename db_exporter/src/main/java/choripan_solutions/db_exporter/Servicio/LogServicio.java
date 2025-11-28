package choripan_solutions.db_exporter.Servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import choripan_solutions.db_exporter.Modelo.Log;
import choripan_solutions.db_exporter.Repositorio.LogRepo;

@Service
public class LogServicio {
    
    @Autowired
    private LogRepo logRepo;

    //Traer todos los logs
    public List<Log> obtenerTodosLosLogs() {
        return logRepo.findAll();
    }

    //Guardar un log
    @SuppressWarnings("null")
    public Log guardarLog(Log log) {
        return logRepo.save(log);
    }

    public List<Log> obtenerLogsPorUsuario(Integer rut) {
        return logRepo.findAll().stream()
                .filter(log -> log.getUsuario() != null && log.getUsuario().getRut().equals(rut))
                .toList();
    }


}
