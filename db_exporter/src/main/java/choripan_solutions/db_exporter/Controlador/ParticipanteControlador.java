package choripan_solutions.db_exporter.Controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import choripan_solutions.db_exporter.Modelo.Participante;
import choripan_solutions.db_exporter.Servicio.ParticipanteServicio;

@RestController
@RequestMapping("/api/participantes")
public class ParticipanteControlador {

    @Autowired
    private ParticipanteServicio participanteServicio;

    @GetMapping
    public List<Participante> getParticipantes() {
        return participanteServicio.getParticipantes();
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<Participante> getParticipante(@PathVariable String codigo) {
        Participante participante = participanteServicio.obtenerParticipantePorRut(codigo);
        if (participante != null) {
            return ResponseEntity.ok(participante);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Participante crearParticipante(@RequestBody Participante participante) {
        return participanteServicio.guardarParticipante(participante);
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<Participante> actualizarParticipante(@PathVariable String codigo, @RequestBody Participante detalles) {
        try {
            Participante actualizado = participanteServicio.actualizarParticipante(codigo, detalles);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{codigo}")
    public ResponseEntity<Participante> actualizarParticipanteParcial(@PathVariable String codigo, @RequestBody Participante detalles) {
        try {
            Participante actualizado = participanteServicio.actualizarParticipanteParcial(codigo, detalles);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<?> eliminarParticipante(@PathVariable String codigo) {
        participanteServicio.eliminarParticipante(codigo);
        return ResponseEntity.ok().build();
    }
}