package choripan_solutions.db_exporter.Logs;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepo extends JpaRepository<Log, Integer>  {
    
}
