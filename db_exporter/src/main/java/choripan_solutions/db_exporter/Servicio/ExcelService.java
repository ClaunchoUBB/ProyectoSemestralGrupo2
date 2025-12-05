package choripan_solutions.db_exporter.Servicio;

import choripan_solutions.db_exporter.Modelo.Participante;
import choripan_solutions.db_exporter.Repositorio.ParticipanteRepo;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;


@Service
public class ExcelService {

    @Autowired
    private ParticipanteRepo participanteRepository;

    public ByteArrayInputStream crearExcelDeParticipantes() throws IOException {
        // Nombres de las columnas
        String[] columns = {"ID", "Edad", "Edad_promedio", "Edad_mediana", "Edad_50", "Edad_60", "Sexo","Residencia_Urbana5a", "Residencia_Rural5a", "NivelEduc_Basico", "NivelEduc_BasicoMedio", "Prevension_FonasaVsOtro", "Prevension_IsapreVsOtros", "CA_FamiliaGastrico", "CA_FamiliaOtros", "EnfermedadesRelevantes", "UsoCronico_Medicamento", "CirugiaGastricaPrevia", "Peso", "peso_promedio", "peso_mediana", "Estatura", "estatura_promedio", "estatura_mediana","IMC", "imc_promedio", "imc_mediana", "IMC_Menor25", "Tabaco_nunca_vs_otros", "tabaco_actual_o_reciente","tabaco_reciente_agudo","tabaco_moderada_carga","tabaco_grave_carga","tabaco_cronico","tabaco_leve_cronico","tabaco:reciente_alta_carga", "alcohol_alguna_vez","alcohol_actual_o_reciente","alcohol_reciente_agudo","alcohol_moderada_carga","alcohol_grave_carga","alcohol_cronico","alcohol_leve_cronico","alcohol_intenso_cronico"};

        // Creamos un nuevo libro de trabajo de Excel (formato .xlsx)
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
            
            // Creamos una nueva hoja en el libro
            Sheet sheet = workbook.createSheet("Participantes");

            // Creamos la fila de encabezado
            Row headerRow = sheet.createRow(0);

            // Llenamos las celdas del encabezado
            for (int col = 0; col < columns.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(columns[col]);
            }

            // Obtenemos los datos de la base de datos
            List<Participante> participantes = participanteRepository.findAll();

            int rowIdx = 1;
            // Llenamos las filas con los datos de los participantes
            for (Participante participante : participantes) {
                Row row = sheet.createRow(rowIdx++);

                row.createCell(0).setCellValue(participante.getCodigo());
                row.createCell(1).setCellValue(participante.getNombre1());
                row.createCell(2).setCellValue(participante.getApellido1());
                row.createCell(3).setCellValue(participante.getCorreo());
     
                // row.createCell(4).setCellValue(participante.getFechaRegistro().toString());
            }

            
            workbook.write(out);
            
            // Se devuelve un ByteArrayInputStream a partir del contenido del ByteArrayOutputStream
            return new ByteArrayInputStream(out.toByteArray());
        }
    }
}


