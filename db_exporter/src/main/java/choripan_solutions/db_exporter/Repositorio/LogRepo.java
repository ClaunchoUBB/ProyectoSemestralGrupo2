package choripan_solutions.db_exporter.Repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import choripan_solutions.db_exporter.Modelo.Log;

public interface LogRepo extends JpaRepository<Log, Long>  {
    
}
