package choripan_solutions.db_exporter.Servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import choripan_solutions.db_exporter.Modelo.Participante;
import choripan_solutions.db_exporter.Repositorio.ParticipanteRepo;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ParticipanteServicio {

    @Autowired
    private ParticipanteRepo participanteRepositorio;

    // Lista para almacenar los datos dicotomizados
    private List<Integer> datosDicotomizados = new ArrayList<>();

    // ======================
    // Métodos CRUD
    // ======================

    public List<Participante> findAllParticipantes() {
        return participanteRepositorio.findAll();
    }

    public Optional<Participante> findByCodigo(String codigo) {
        return participanteRepositorio.findById(codigo);
    }

    public Participante crearParticipante(Participante participante) {
        if (participante.getGrupo() == null) {
            throw new IllegalArgumentException("El grupo (0 = control, 1 = caso) es obligatorio.");
        }

        // Determinar prefijo según el grupo
        String prefijo = participante.getGrupo() == 1 ? "K" : "C";

        // Contar cuántos hay en ese grupo
        long count = participanteRepositorio.countByGrupo(participante.getGrupo());

        // Generar el código (p.ej. C-001)
        String codigo = String.format("%s-%03d", prefijo, count + 1);
        participante.setCodigo(codigo);

        // Guardar el participante
        return participanteRepositorio.save(participante);
    }

    public Participante actualizarParticipante(String codigo, Participante participanteActualizado) {
        return participanteRepositorio.findById(codigo)
                .map(p -> {
                    participanteActualizado.setCodigo(p.getCodigo());
                    return participanteRepositorio.save(participanteActualizado);
                })
                .orElseThrow(() -> new RuntimeException("Participante no encontrado con código: " + codigo));
    }

    public void eliminarParticipante(String codigo) {
        if (!participanteRepositorio.existsById(codigo)) {
            throw new RuntimeException("Participante no encontrado con código: " + codigo);
        }
        participanteRepositorio.deleteById(codigo);
    }

    // PATCH: Actualización parcial mediante objeto JSON
    public Participante actualizarParticipanteParcial(String codigo, Participante participanteDetalles) {
        Participante participante = participanteRepositorio.findById(codigo)
                .orElseThrow(() -> new RuntimeException("No se encontró el participante con código: " + codigo));

        // Recorremos todos los campos declarados en la clase Participante
        for (var field : Participante.class.getDeclaredFields()) {
            try {
                field.setAccessible(true);
                Object nuevoValor = field.get(participanteDetalles);
                if (!field.getName().equals("codigo") && nuevoValor != null) {
                    field.set(participante, nuevoValor);
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Error al actualizar el campo: " + field.getName(), e);
            }
        }

        return participanteRepositorio.save(participante);
    }

    // ======================
    // Dicotomización de datos
    // ======================

    /**
     * Dicotomiza un conjunto de valores según un umbral.
     * Si el valor >= umbral → 1, si es menor → 0.
     * Guarda los resultados en una lista interna.
     *
     * @param valores Lista de valores numéricos.
     * @param umbral  Valor de referencia para dicotomizar.
     * @return Lista de valores dicotomizados (0 o 1).
     */
    public List<Integer> dicotomizarDatos(List<Float> valores, float umbral) {
        datosDicotomizados = valores.stream()
                .map(v -> v >= umbral ? 1 : 0)
                .collect(Collectors.toList());
        return datosDicotomizados;
    }

    /**
     * Devuelve la lista actual de datos dicotomizados.
     */
    public List<Integer> obtenerDatosDicotomizados() {
        return datosDicotomizados;
    }

    /**
     * Limpia la lista de datos dicotomizados.
     */
    public void limpiarDatosDicotomizados() {
        datosDicotomizados.clear();
    }
}
