package choripan_solutions.db_exporter.Servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import choripan_solutions.db_exporter.Modelo.Participante;
import choripan_solutions.db_exporter.Repositorio.ParticipanteRepo;

@Service
public class ParticipanteServicio {
    // PUT: Actualización total

    @Autowired
    private ParticipanteRepo ParticipanteRepo;

    public List<Participante> getParticipantes() {
        return ParticipanteRepo.findAll();
    }

    public Participante guardarParticipante(Participante participante) {
        return ParticipanteRepo.save(participante);
    }

    public void eliminarParticipante(String rut) {
        ParticipanteRepo.deleteById(rut);
    }

    public Participante obtenerParticipantePorRut(String rut) {
        return ParticipanteRepo.findById(rut).orElse(null);
    }

    public Participante actualizarParticipante(String codigo, Participante participanteDetalles) {
        Participante participante = ParticipanteRepo.findById(codigo)
                .orElseThrow(() -> new RuntimeException("No se encontró el participante con código: " + codigo));

        participante.setNombre1(participanteDetalles.getNombre1());
        participante.setNombre2(participanteDetalles.getNombre2());
        participante.setApellido1(participanteDetalles.getApellido1());
        participante.setApellido2(participanteDetalles.getApellido2());
        participante.setCorreo(participanteDetalles.getCorreo());
        participante.setNumero(participanteDetalles.getNumero());
        participante.setEstado(participanteDetalles.getEstado());
        participante.setUsuario(participanteDetalles.getUsuario());

        return ParticipanteRepo.save(participante);
    }

    // PATCH: Actualización parcial
    public Participante actualizarParticipanteParcial(String codigo, Participante participanteDetalles) {
        Participante participante = ParticipanteRepo.findById(codigo)
                .orElseThrow(() -> new RuntimeException("No se encontró el participante con código: " + codigo));

        if (participanteDetalles.getNombre1() != null)
            participante.setNombre1(participanteDetalles.getNombre1());
        if (participanteDetalles.getNombre2() != null)
            participante.setNombre2(participanteDetalles.getNombre2());
        if (participanteDetalles.getApellido1() != null)
            participante.setApellido1(participanteDetalles.getApellido1());
        if (participanteDetalles.getApellido2() != null)
            participante.setApellido2(participanteDetalles.getApellido2());
        if (participanteDetalles.getCorreo() != null)
            participante.setCorreo(participanteDetalles.getCorreo());
        if (participanteDetalles.getNumero() != null)
            participante.setNumero(participanteDetalles.getNumero());
        if (participanteDetalles.getEstado() != null)
            participante.setEstado(participanteDetalles.getEstado());
        if (participanteDetalles.getUsuario() != null)
            participante.setUsuario(participanteDetalles.getUsuario());

        return ParticipanteRepo.save(participante);
    }
}
