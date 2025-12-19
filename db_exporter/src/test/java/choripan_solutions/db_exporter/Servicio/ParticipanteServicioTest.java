package choripan_solutions.db_exporter.Servicio;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.transaction.Transactional;


@SpringBootTest
@Transactional
@DisplayName("Pruebas Unitarias Puras para ParticipanteServicio (Lógica sin DB)")
class ParticipanteServicioTest {

    // Instancia de la clase a probar (no necesita inyección de Spring)
    private ParticipanteServicio participanteServicio = new ParticipanteServicio();

    @Test
    @DisplayName("Debe dicotomizar correctamente los valores respecto al umbral")
    void dicotomizarDatos_DebeRetornarBinarios() {
        // ARRANGE
        List<Float> valores = Arrays.asList(5.5f, 1.2f, 10.0f, 4.9f, 5.0f);
        float umbral = 5.0f; // Si valor >= 5.0f -> 1, sino -> 0
        
        // ACT
        List<Integer> resultado = participanteServicio.dicotomizarDatos(valores, umbral);
        
        // ASSERT
        List<Integer> esperado = Arrays.asList(1, 0, 1, 0, 1);
        
        assertEquals(esperado.size(), resultado.size());
        assertEquals(esperado, resultado, "La lista de valores dicotomizados no coincide con lo esperado.");
        
        // Verificar que el método auxiliar guardó el resultado correctamente
        assertEquals(esperado, participanteServicio.obtenerDatosDicotomizados());
    }
    
    // ... Se agregarían más tests para la lógica de generación de código, etc.
}