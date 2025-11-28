package choripan_solutions.db_exporter.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginRequest {
    @NotNull(message = "El RUT no puede ser nulo")
    private Integer rut;
    @NotBlank(message = "La contraseña no puede estar vacía")
    private String password;
}