package choripan_solutions.db_exporter.Controlador;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
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
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/participantes")
public class ParticipanteControlador {

    @Autowired
    private ParticipanteServicio participanteServicio;

    @GetMapping
    public List<Participante> findAllParticipantes() {
        return participanteServicio.findAllParticipantes();
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<Participante> findParticipante(@PathVariable String codigo) {
        Optional<Participante> participante = participanteServicio.findByCodigo(codigo);
        if (participante.isPresent()) {
            return ResponseEntity.ok(participante.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasAnyRole('MEDICO', 'ADMINISTRADOR')")
    @PostMapping("/participantes")
    public ResponseEntity<?> crearParticipante(@Valid @RequestBody Participante participante) {
        Participante nuevo = participanteServicio.crearParticipante(participante);
        return ResponseEntity.ok(nuevo);
    }

    @PreAuthorize("hasAnyRole('RECLUTADOR', 'MEDICO', 'ADMINISTRADOR')")
    @GetMapping("/participantes/excel")
    public ResponseEntity<?> generarExcel() {
        return ResponseEntity.ok("Generando Excel...");
    }

    @PreAuthorize("hasAnyRole('INVESTIGADOR', 'ADMINISTRADOR')")
    @GetMapping("/participantes/datos")
    public ResponseEntity<?> verDatos() {
        return ResponseEntity.ok("Acceso a datos permitidos");
    }


    @PreAuthorize("hasAnyRole('MEDICO', 'ADMINISTRADOR')")
    @PutMapping("/{codigo}")
    public ResponseEntity<Participante> actualizarParticipante(@PathVariable String codigo,
            @RequestBody Participante detalles) {
        try {
            Participante actualizado = participanteServicio.actualizarParticipante(codigo, detalles);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @PreAuthorize("hasAnyRole('MEDICO', 'ADMINISTRADOR')")
    @PatchMapping("/{codigo}")
    public ResponseEntity<Participante> actualizarParticipanteParcial(@PathVariable String codigo,
            @RequestBody Participante detalles) {
        try {
            Participante actualizado = participanteServicio.actualizarParticipanteParcial(codigo, detalles);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR')")
    @DeleteMapping("/{codigo}")
    public ResponseEntity<?> eliminarParticipante(@PathVariable String codigo) {
        participanteServicio.eliminarParticipante(codigo);
        return ResponseEntity.ok().build();
    }
}