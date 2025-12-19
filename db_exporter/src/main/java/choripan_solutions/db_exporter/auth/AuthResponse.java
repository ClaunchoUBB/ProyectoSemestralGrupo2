package choripan_solutions.db_exporter.auth;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class AuthResponse {

    private String rol;
    private Integer rut;
    private String nombre;

    public AuthResponse(String rol, Integer rut, String nombre) {
        this.rol = rol;
        this.rut = rut;
        this.nombre = nombre;
    }

    public String getRol() {
        return rol;
    }

    public Integer getRut() {
        return rut;
    }

    public String getNombre() {
        return nombre;
    }
}
