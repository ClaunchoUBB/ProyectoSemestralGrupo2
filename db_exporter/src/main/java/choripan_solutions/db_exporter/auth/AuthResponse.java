package choripan_solutions.db_exporter.auth;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
@Getter
@Setter
public class AuthResponse {
    private String token;
    private String rol;
    private Integer rut;
    private String nombre;

}