package choripan_solutions.db_exporter.Participante;

import org.springframework.data.jpa.repository.JpaRepository;

import choripan_solutions.db_exporter.Participante.Participante;

public interface ParticipanteRepo extends JpaRepository<Participante, String> {
    long countByGrupo(Integer grupo);
}
