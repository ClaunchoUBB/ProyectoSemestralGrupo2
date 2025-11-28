package choripan_solutions.db_exporter.Usuario;
import choripan_solutions.db_exporter.Logs.Log;
import choripan_solutions.db_exporter.Participante.Participante;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "usuarios")

public class Usuario {

    
    
    @Id
    @Column(name = "rut")
    private Integer rut;

    @Column(name = "nombre1")
    private String nombre1;

    @Column(name = "nombre2")
    private String nombre2;

    @Column(name = "apellido1")
    private String apellido1;

    @Column(name = "apellido2")
    private String apellido2;

    @NotNull
    @Min(1) @Max(4)
    //1: ADMIN, 2: INVESTIGADOR, 3: RECLUTADOR, 4:MEDICO
    @Column(name = "rol")   
    private Integer rol;




    @Column(name = "numero")
    private String numero;

    @Column(name = "correo")
    private String correo;

    @Column(name = "activo")
    private Boolean activo;

    //Esta anotación evita que el hash de la contraseña se envíe en los JSON de respuesta (por seguridad)
    //pero sí permite que se reciba en peticiones POST o PUT.
    @Column(name = "password_hash", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String passwordHash;

   
    //Relaciones
    //Un usuario puede tener muchos Logs
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Log> logs;

    //Un usuario puede tener muchos Participantes
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Participante> participantes;
    


    // Getters y Setters
    public Integer getRut() {
        return rut;
    }
    public void setRut(int rut) {
        this.rut = rut;
    }

    public String getNombre1() {
        return nombre1;
    }
    public void setNombre1(String nombre1) {
        this.nombre1 = nombre1;
    }

    public String getNombre2() {
        return nombre2;
    }
    public void setNombre2(String nombre2) {
        this.nombre2 = nombre2;
    }

    public String getApellido1() {
        return apellido1;
    }
    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }
    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public Integer getRol() {
        return rol;
    }
    public void setRol(Integer rol) {
        this.rol = rol;
    }

    public String getNumero() {
        return numero;
    }
    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCorreo() {
        return correo;
    }
    public void setCorreo(String correo) {
        this.correo = correo;
    } 
    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
    public void setRut(Integer rut) {
        this.rut = rut;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
    public Object findByRut(String rut2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByRut'");
    }


}
