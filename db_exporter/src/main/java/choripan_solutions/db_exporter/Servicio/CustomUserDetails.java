package choripan_solutions.db_exporter.Servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import choripan_solutions.db_exporter.Modelo.Usuario;
import choripan_solutions.db_exporter.Repositorio.UsuarioRepositorio;

public class CustomUserDetails implements UserDetailsService{

     @Autowired
    private UsuarioRepositorio usuarioRepo;

    @Override
    public UserDetails loadUserByUsername(String rut) throws UsernameNotFoundException {
        
        String rutR = rut.replaceAll("[^0-9]", "");
        
        Integer rutInt;

        try {
            rutInt = Integer.parseInt(rutR);
        } catch (NumberFormatException e) {
            throw new UsernameNotFoundException("RUT inválido: " + rutR);
        }

        Usuario usuario = usuarioRepo.findByRut(rutInt)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con RUT: " + rutR));

        // El rol es un Integer, así que lo comparamos con números.
        String roleName = switch (usuario.getRol()) {
            case 1 -> "ADMINISTRADOR";
            case 2 -> "INVESTIGADOR";
            case 3 -> "RECLUTADOR";
            case 4 -> "MEDICO";
            default -> "UNKNOWN"; // Si el rol no es ninguno de los esperados
        };

        return User.builder()
                .username(usuario.getRut().toString())
                .password(usuario.getPasswordHash())
                .authorities(List.of(new SimpleGrantedAuthority("ROLE_" + roleName)))
                .build();
    }
    
}
