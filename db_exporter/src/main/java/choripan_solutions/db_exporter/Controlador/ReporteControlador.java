package choripan_solutions.db_exporter.Controlador;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import choripan_solutions.db_exporter.Servicio.ExcelService;

@RestController
@RequestMapping("/api/reportes")
@PreAuthorize("hasAnyRole('INVESTIGADOR', 'ADMINISTRADOR')")
public class ReporteControlador {

    @Autowired
    private ExcelService excelService;

     @SuppressWarnings("null")
    @GetMapping("/participantes/export/excel")
    public ResponseEntity<InputStreamResource> exportarParticipantesAExcel() throws IOException {
        ByteArrayInputStream in = excelService.crearExcelDeParticipantes();

        HttpHeaders headers = new HttpHeaders();
        String fechaActual = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String nombreArchivo = "participantes_" + fechaActual + ".xlsx";
        
        // El header "Content-Disposition" le dice al navegador que debe descargar el archivo con un nombre específico.
        headers.add("Content-Disposition", "attachment; filename=" + nombreArchivo);

        return ResponseEntity
                .ok()
                .headers(headers)
                // El "MediaType" es importante para que el cliente sepa qué tipo de archivo está recibiendo.
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(new InputStreamResource(in));
    }
}
