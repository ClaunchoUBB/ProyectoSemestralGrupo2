package choripan_solutions.db_exporter.Repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import choripan_solutions.db_exporter.Modelo.Participante;

public interface ParticipanteRepo extends JpaRepository<Participante, String> {
    long countByGrupo(Integer grupo);
}
