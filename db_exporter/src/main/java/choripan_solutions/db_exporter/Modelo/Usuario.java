package choripan_solutions.db_exporter.Modelo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "usuarios") // Asegúrate que el nombre de la tabla sea correcto
@Getter
@Setter
public class Usuario implements UserDetails { // <-- ¡Implementamos UserDetails!

    @Id
    @Column(name = "rut", nullable = false, unique = true)
    private Integer rut;

    @Column(name = "nombre1", length = 40)
    private String nombre1;

    @Column(name = "nombre2", length = 200)
    private String nombre2;

    @Column(name = "apellido1", length = 40)
    private String apellido1;

    @Column(name = "apellido2", length = 40)
    private String apellido2;

    @Column(name = "rol", length = 20)
    private Integer rol;

    @Column(name = "numero", length = 15)
    private String numero;

    @Column(name = "correo", length = 30)
    private String correo;

    @Column(name = "password_hash", length = 60)
    private String passwordHash;

    @Column(name = "activo")
    private Boolean activo;

    public Usuario() {
    }

    // --- MÉTODOS DE LA INTERFAZ UserDetails ---

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Creamos una autoridad basada en el rol, añadiendo el prefijo "ROLE_"
        // que Spring Security espera.
        String roleName = switch (this.rol) {
            case 1 -> "ADMINISTRADOR";
            case 2 -> "INVESTIGADOR";
            case 3 -> "RECLUTADOR";
            case 4 -> "MEDICO";
            default -> "UNKNOWN";
        };

        return List.of(new SimpleGrantedAuthority("ROLE_" + roleName));
    }

    @Override
    public String getPassword() {
        // Devuelve el hash de la contraseña.
        return this.passwordHash;
    }

    @Override
    public String getUsername() {
        // Devuelve el identificador único del usuario (en nuestro caso, el RUT).
        return this.rut.toString();
    }

    @Override
    public boolean isAccountNonExpired() {
        // Podemos devolver 'true' si no manejamos la expiración de cuentas.
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // Podemos devolver 'true' si no manejamos el bloqueo de cuentas.
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // Podemos devolver 'true' si no manejamos la expiración de credenciales.
        return true;
    }

    @Override
    public boolean isEnabled() {
        // Devuelve si la cuenta está activa o no.
        return this.activo;
    }
}
