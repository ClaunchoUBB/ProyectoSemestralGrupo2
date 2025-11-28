package choripan_solutions.db_exporter.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import choripan_solutions.db_exporter.Modelo.Log;
import choripan_solutions.db_exporter.Modelo.Usuario;
import choripan_solutions.db_exporter.Servicio.LogServicio;

@Aspect
@Component
public class LoggingAspect {

    private final LogServicio logServicio;

    public LoggingAspect(LogServicio logServicio) {
        this.logServicio = logServicio;
    }

    /**
     * Define un "pointcut" que captura la ejecución de cualquier método que
     * empiece con "crear", "actualizar", "eliminar", "activar" o "desactivar"
     * dentro de cualquier clase en el paquete "Servicio".
     */
    @Pointcut("execution(* choripan_solutions.db_exporter.Servicio.*.crear*(..)) || " +
              "execution(* choripan_solutions.db_exporter.Servicio.*.actualizar*(..)) || " +
              "execution(* choripan_solutions.db_exporter.Servicio.*.eliminar*(..)) || " +
              "execution(* choripan_solutions.db_exporter.Servicio.*.activar*(..)) || " +
              "execution(* choripan_solutions.db_exporter.Servicio.*.desactiv*(..))")
    public void serviceMethodsPointcut() {}

    /**
     * Este "advice" se ejecuta DESPUÉS de que un método capturado por el pointcut
     * termine con éxito.
     * @param joinPoint Contiene información sobre el método que fue ejecutado.
     * @param result El objeto que devolvió el método.
     */
    @AfterReturning(pointcut = "serviceMethodsPointcut()", returning = "result")
    public void logAfterServiceMethod(JoinPoint joinPoint, Object result) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof Usuario)) {
            // No registrar si no hay un usuario autenticado o si es una acción del sistema
            return;
        }

        Usuario usuario = (Usuario) authentication.getPrincipal();
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();

        String detalle = String.format("Usuario '%s' (RUT: %d) ejecutó '%s' en '%s'.",
                usuario.getNombre1(), usuario.getRut(), methodName, className);

        Log nuevoLog = new Log();
        nuevoLog.setUsuario(usuario);
        nuevoLog.setDetalle(detalle);

        logServicio.guardarLog(nuevoLog);
    }
}