package choripan_solutions.db_exporter.Controlador;

import choripan_solutions.db_exporter.Servicio.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;

@RestController
@RequestMapping("/api/excel")
@CrossOrigin(origins = "*")
public class ExcelControlador {

    @Autowired
    private ExcelService excelService;

    @GetMapping("/descargar")
    
    public ResponseEntity<?> descargarExcel() {

        try {
            ByteArrayInputStream excel = excelService.crearExcelDeParticipantes();

            HttpHeaders headers = new HttpHeaders();
            headers.add(
                HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=Participantes_Analisis.xlsx"
            );

            
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(
                        MediaType.parseMediaType(
                            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
                        )
                    )
                    
                    .body(new InputStreamResource(excel));

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Error al generar Excel");
        }
    }
}
