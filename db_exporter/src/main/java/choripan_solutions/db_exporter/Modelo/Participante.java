package choripan_solutions.db_exporter.Modelo;

import jakarta.persistence.*;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@Entity
@Table(name = "Participante")
public class Participante {

    @Id // La clave primaria es "codigo" (String)
    @Column(name = "codigo", length = 10)
    private String codigo;

    @Column(name = "nombre1")
    private String nombre1;

    @Column(name = "nombre2")
    private String nombre2;

    @Column(name = "apellido1")
    private String apellido1;

    @Column(name = "apellido2")
    private String apellido2;

    @Column(name = "correo")
    private String correo;

    @Column(name = "numero")
    private String numero;

    @Column(name = "estado", nullable = false)
    private Boolean estado;

    // --- Relación ---

    // Muchos Participantes pertenecen a un Usuario
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rut_usuario") // Esta es la columna FOREIGN KEY
    @JsonIgnore
    private Usuario usuario;
}