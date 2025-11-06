package choripan_solutions.db_exporter.Modelo;

import jakarta.persistence.*;
import lombok.Data;
import java.sql.Timestamp;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@Entity
@Table(name = "Log")
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Le dice a JPA que es un AUTO_INCREMENT
    @Column(name = "id_log")
    private Integer idLog;

    @Column(name = "detalle", columnDefinition = "TEXT")
    private String detalle;

    // Le decimos a JPA que no intente insertar o actualizar este campo,
    // ya que la base de datos lo manejará con "DEFAULT CURRENT_TIMESTAMP"
    @Column(name = "fecha", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private Timestamp fecha;

    // --- Relación ---

    // Muchos Logs pertenecen a un Usuario
    @ManyToOne(fetch = FetchType.LAZY) // LAZY: solo carga el usuario cuando se pide
    @JoinColumn(name = "rut_usuario") // Esta es la columna FOREIGN KEY
    @JsonIgnore // Evita bucles infinitos al convertir a JSON
    private Usuario usuario;
}