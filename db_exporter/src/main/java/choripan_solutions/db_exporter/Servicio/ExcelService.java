package choripan_solutions.db_exporter.Servicio;

import choripan_solutions.db_exporter.Modelo.Participante;
import choripan_solutions.db_exporter.Repositorio.ParticipanteRepo;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ExcelService {

    @Autowired
    private ParticipanteRepo participanteRepository;

    public ByteArrayInputStream crearExcelDeParticipantes() throws IOException {
        
        List<Participante> participantes = participanteRepository.findAll();

        if (participantes.isEmpty()) {
            throw new IllegalStateException("No existen participantes para exportar");
        }

        // --- Cálculos estadísticos ---
        double edadPromedio = calcularPromedio(participantes, "edad");
        double edadMediana = calcularMediana(participantes, "edad");
        double pesoPromedio = calcularPromedio(participantes, "peso");
        double pesoMediana = calcularMediana(participantes, "peso");
        double estaturaPromedio = calcularPromedio(participantes, "estatura");
        double estaturaMediana = calcularMediana(participantes, "estatura");
        double imcPromedio = calcularPromedio(participantes, "imc");
        double imcMediana = calcularMediana(participantes, "imc");

        String[] columns = {
            "Código del participante", // 0
            
            // Sociodemográficas (1-12)
            "Edad", "Edad_promedio", "Edad_mediana", "Edad_50", "Edad_60", 
            "Sexo", "Residencia_Urbana5", "Residencia_Rural5a", 
            "NivelEduc_Basico", "NivelEduc_BasicoMedio", 
            "Prevision_FonasaVsOtros", "Prevision_IsapreVsOtros",

            // Clínicos (13-17)
            "CA_FamiliaGastrico", "CA_FamiliaOtros", "EnfermedadesRelevantes", 
            "UsoCronico_Medicamentos", "CirugiaGastricaPrevia",

            // Antropométricas (18-27)
            "Peso", "peso_promedio", "peso_mediana",
            "Estatura", "estatura_promedio", "estatura_mediana",
            "IMC", "imc_promedio", "imc_mediana", "IMC_Menor25",

            // Tabaquismo (28-30)
            "tabaco_nunca_vs_otros", "tabaco_actual", "tabaco_carga_alta",

            // Alcohol (31-32)
            "alcohol_alguna_vez", "alcohol_frecuencia_alta",

            // Dietarios y Ambientales (33-45)
            "CarnesProcesadas_Menos3", "CarnesProcesadas_Max1",
            "FrutasVerduras_3oMas", "FrutasVerduras_5oMas",
            "CondimentosFrecAlta", "BebidasCalientes_AltaFrecuencia",
            "AñadeSal_Comida", "Frituras_Frecuente",
            "Pesticidas_Exposicion", "CompuestosQuimicos_Exposicion",
            "FuenteYTratamientoAgua", "Lena_Frecuente", "Lena_AlgunaExposicion",
            
            // H. Pylori (46)
            "HPylori_AlgunaVezPositivo"
        };

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Variables Análisis");
            
            // --- DEFINICIÓN DE ESTILOS (ENCABEZADO vs DATOS) ---
            // Usamos un color "Fuerte" para el encabezado y uno "Pastel/Claro" para los datos

            // ID: Gris
            CellStyle idHeader = crearEstilo(workbook, IndexedColors.GREY_40_PERCENT, true);
            CellStyle idData   = crearEstilo(workbook, IndexedColors.GREY_25_PERCENT, false);

            // Sociodemográficas: Azul Cielo -> Azul Pálido
            CellStyle socioHeader = crearEstilo(workbook, IndexedColors.SKY_BLUE, true);
            CellStyle socioData   = crearEstilo(workbook, IndexedColors.PALE_BLUE, false);

            // Clínicos: Coral/Rojo Suave -> Rosa
            CellStyle clinicoHeader = crearEstilo(workbook, IndexedColors.CORAL, true);
            CellStyle clinicoData   = crearEstilo(workbook, IndexedColors.ROSE, false);

            // Antropométricas: Lima/Verde -> Verde Claro
            CellStyle antroHeader = crearEstilo(workbook, IndexedColors.LIME, true);
            CellStyle antroData   = crearEstilo(workbook, IndexedColors.LIGHT_GREEN, false);

            // Tabaquismo: Bronceado (Tan) -> Amarillo Claro (simulando tabaco/seco)
            CellStyle tabacoHeader = crearEstilo(workbook, IndexedColors.TAN, true);
            CellStyle tabacoData   = crearEstilo(workbook, IndexedColors.LEMON_CHIFFON, false);

            // Alcohol: Oro -> Amarillo muy claro
            CellStyle alcoholHeader = crearEstilo(workbook, IndexedColors.GOLD, true);
            CellStyle alcoholData   = crearEstilo(workbook, IndexedColors.LIGHT_YELLOW, false);

            // Dieta: Violeta -> Lavanda
            CellStyle dietaHeader = crearEstilo(workbook, IndexedColors.VIOLET, true);
            CellStyle dietaData   = crearEstilo(workbook, IndexedColors.LAVENDER, false);

            // H. Pylori: Turquesa -> Turquesa Claro
            CellStyle hpHeader = crearEstilo(workbook, IndexedColors.TURQUOISE, true);
            CellStyle hpData   = crearEstilo(workbook, IndexedColors.LIGHT_TURQUOISE, false);


            // --- CREAR ENCABEZADO ---
            Row headerRow = sheet.createRow(0);
            for (int col = 0; col < columns.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(columns[col]);
                cell.setCellStyle(obtenerEstiloPorColumna(col, true, 
                        idHeader, socioHeader, clinicoHeader, antroHeader, 
                        tabacoHeader, alcoholHeader, dietaHeader, hpHeader));
            }

            // --- LLENAR DATOS ---
            int rowIdx = 1;
            for (Participante p : participantes) {
                Row row = sheet.createRow(rowIdx++);
                int col = 0;

                // Auxiliar para crear celda y asignar estilo automáticamente
                // Nota: Java evalua los argumentos de izquierda a derecha, así que 'col' se incrementará si lo pasamos como col++
                
                // ID
                crearCelda(row, col++, p.getCodigo(), idData);

                // SOCIODEMOGRÁFICAS
                Integer edad = p.getEdad() != null ? p.getEdad() : 0;
                crearCelda(row, col++, edad, socioData);
                crearCelda(row, col++, (edad > edadPromedio ? 1 : 0), socioData);
                crearCelda(row, col++, (edad > edadMediana ? 1 : 0), socioData);
                crearCelda(row, col++, (edad >= 50 ? 1 : 0), socioData);
                crearCelda(row, col++, (edad >= 60 ? 1 : 0), socioData);
                crearCelda(row, col++, (Boolean.TRUE.equals(p.getSexo()) ? 1 : 0), socioData);
                
                boolean esUrbana = Boolean.FALSE.equals(p.getZona()); 
                boolean masDe5 = Boolean.TRUE.equals(p.getViveHace5annos());
                crearCelda(row, col++, ((esUrbana && masDe5) || (!esUrbana && !masDe5) ? 1 : 0), socioData);
                crearCelda(row, col++, ((!esUrbana && masDe5) || (esUrbana && !masDe5) ? 1 : 0), socioData);
                
                Integer educ = p.getNivelEducacional();
                crearCelda(row, col++, ((educ != null && educ > 0) ? 1 : 0), socioData);
                crearCelda(row, col++, ((educ != null && educ == 2) ? 1 : 0), socioData);
                
                Integer prev = p.getPrevisionSalud();
                boolean esFonasaOSin = (prev != null && (prev == 0 || prev == 1));
                crearCelda(row, col++, (esFonasaOSin ? 0 : 1), socioData);
                boolean esIsapre = (prev != null && prev == 2);
                crearCelda(row, col++, (esIsapre ? 0 : 1), socioData);

                // CLÍNICOS
                crearCelda(row, col++, (Boolean.TRUE.equals(p.getAntCancerGastrico()) ? 1 : 0), clinicoData);
                crearCelda(row, col++, (Boolean.TRUE.equals(p.getAntCancerOtro()) ? 1 : 0), clinicoData);
                boolean tieneEnf = p.getOtrasEnfermedades() != null && !p.getOtrasEnfermedades().isEmpty();
                crearCelda(row, col++, (tieneEnf ? 1 : 0), clinicoData);
                crearCelda(row, col++, (Boolean.TRUE.equals(p.getUsoCronicoMedicamentosGastrolesivos()) ? 1 : 0), clinicoData);
                crearCelda(row, col++, (Boolean.TRUE.equals(p.getCirugiaGastricaPrevia()) ? 1 : 0), clinicoData);

                // ANTROPOMÉTRICAS
                Float peso = p.getPeso() != null ? p.getPeso() : 0f;
                Float estatura = p.getEstatura() != null ? p.getEstatura() : 0f;
                Float imc = p.getImc() != null ? p.getImc() : 0f;

                crearCelda(row, col++, peso, antroData);
                crearCelda(row, col++, (peso > pesoPromedio ? 1 : 0), antroData);
                crearCelda(row, col++, (peso > pesoMediana ? 1 : 0), antroData);
                crearCelda(row, col++, estatura, antroData);
                crearCelda(row, col++, (estatura >= estaturaPromedio ? 0 : 1), antroData);
                crearCelda(row, col++, (estatura >= estaturaMediana ? 0 : 1), antroData);
                crearCelda(row, col++, imc, antroData);
                crearCelda(row, col++, (imc > imcPromedio ? 1 : 0), antroData);
                crearCelda(row, col++, (imc > imcMediana ? 1 : 0), antroData);
                crearCelda(row, col++, (imc >= 25 ? 1 : 0), antroData);

                // TABAQUISMO
                crearCelda(row, col++, (Boolean.TRUE.equals(p.getNuncaFumo()) ? 0 : 1), tabacoData);
                crearCelda(row, col++, (Boolean.TRUE.equals(p.getFumadorActual()) ? 1 : 0), tabacoData);
                Integer fumaDiario = p.getPromedioFumaDiario();
                crearCelda(row, col++, ((fumaDiario != null && fumaDiario == 2) ? 1 : 0), tabacoData);

                // ALCOHOL
                Integer estadoAlcohol = p.getEstadoConsumoAlcohol();
                crearCelda(row, col++, ((estadoAlcohol != null && estadoAlcohol > 0) ? 1 : 0), alcoholData);
                Integer frecAlcohol = p.getFrecuenciaConsumoAlcohol();
                crearCelda(row, col++, ((frecAlcohol != null && frecAlcohol >= 3) ? 1 : 0), alcoholData);

                // DIETARIOS Y AMBIENTALES
                Integer carnes = p.getCarnesProcesadas();
                crearCelda(row, col++, ((carnes != null && carnes == 2) ? 1 : 0), dietaData);
                crearCelda(row, col++, ((carnes != null && carnes >= 1) ? 1 : 0), dietaData);
                Integer frutas = p.getFrutasVerduras();
                crearCelda(row, col++, ((frutas != null && frutas >= 1) ? 0 : 1), dietaData);
                crearCelda(row, col++, ((frutas != null && frutas == 2) ? 0 : 1), dietaData);
                Integer condimentos = p.getConsumoAlimentosMuyCondimentados();
                crearCelda(row, col++, ((condimentos != null && condimentos == 2) ? 1 : 0), dietaData);
                Integer calientes = p.getBebidaCaliente();
                crearCelda(row, col++, ((calientes != null && calientes == 2) ? 1 : 0), dietaData);
                crearCelda(row, col++, (Boolean.TRUE.equals(p.getAlimentosSalados()) ? 1 : 0), dietaData);
                crearCelda(row, col++, (Boolean.TRUE.equals(p.getFrituras()) ? 1 : 0), dietaData);
                crearCelda(row, col++, (Boolean.TRUE.equals(p.getPesticidas()) ? 1 : 0), dietaData);
                crearCelda(row, col++, (Boolean.TRUE.equals(p.getOtrosChemicos()) ? 1 : 0), dietaData);
                
                Integer fuente = p.getFuentePrincipalAgua();
                Integer trat = p.getTratamientoAgua();
                boolean fuenteRiesgosa = (fuente != null && fuente != 0);
                boolean sinTratamiento = (trat != null && trat == 0);
                crearCelda(row, col++, ((fuenteRiesgosa && sinTratamiento) ? 1 : 0), dietaData);
                
                Integer lena = p.getHumoLenna();
                crearCelda(row, col++, ((lena != null && lena == 2) ? 1 : 0), dietaData);
                crearCelda(row, col++, ((lena != null && lena > 0) ? 1 : 0), dietaData);

                // H PYLORI
                boolean hpPasado = (p.getResultPositivHelPasado() != null && p.getResultPositivHelPasado() == 1);
                boolean hpActual = (p.getResultadoHel() != null && p.getResultadoHel() == 1);
                crearCelda(row, col++, ((hpPasado || hpActual) ? 1 : 0), hpData);
            }

            // Autoajustar ancho de columnas (opcional, puede ser lento con muchos datos)
            // for(int i=0; i<columns.length; i++) sheet.autoSizeColumn(i);

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }

    // --- MÉTODOS AUXILIARES DE ESTILO Y CELDAS ---

    private void crearCelda(Row row, int colIndex, Object valor, CellStyle estilo) {
        Cell cell = row.createCell(colIndex);
        if (valor instanceof Number) {
            cell.setCellValue(((Number) valor).doubleValue());
        } else if (valor instanceof String) {
            cell.setCellValue((String) valor);
        } else if (valor instanceof Boolean) {
            cell.setCellValue((Boolean) valor);
        } else {
            cell.setCellValue(valor != null ? valor.toString() : "");
        }
        cell.setCellStyle(estilo);
    }

    private CellStyle crearEstilo(Workbook workbook, IndexedColors color, boolean esHeader) {
        CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(color.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);

        if (esHeader) {
            style.setAlignment(HorizontalAlignment.CENTER);
            Font font = workbook.createFont();
            font.setBold(true);
            style.setFont(font);
        }
        return style;
    }

    private CellStyle obtenerEstiloPorColumna(int col, boolean esHeader, 
                                              CellStyle id, CellStyle socio, CellStyle clinico, 
                                              CellStyle antro, CellStyle tabaco, CellStyle alcohol, 
                                              CellStyle dieta, CellStyle hp) {
        if (col == 0) return id;
        if (col <= 12) return socio;
        if (col <= 17) return clinico;
        if (col <= 27) return antro;
        if (col <= 30) return tabaco;
        if (col <= 32) return alcohol;
        if (col <= 45) return dieta;
        return hp;
    }

    // --- MÉTODOS ESTADÍSTICOS (Sin cambios) ---
    private double calcularPromedio(List<Participante> lista, String campo) {
        return lista.stream()
                .mapToDouble(p -> obtenerValorNumerico(p, campo))
                .filter(val -> val > 0)
                .average()
                .orElse(0.0);
    }

    private double calcularMediana(List<Participante> lista, String campo) {
        List<Double> valores = lista.stream()
                .map(p -> obtenerValorNumerico(p, campo))
                .filter(val -> val > 0)
                .sorted()
                .collect(Collectors.toList());

        if (valores.isEmpty()) return 0.0;
        int size = valores.size();
        if (size % 2 == 0) {
            return (valores.get(size / 2 - 1) + valores.get(size / 2)) / 2.0;
        } else {
            return valores.get(size / 2);
        }
    }

    private double obtenerValorNumerico(Participante p, String campo) {
        switch (campo) {
            case "edad": return p.getEdad() != null ? p.getEdad() : 0.0;
            case "peso": return p.getPeso() != null ? p.getPeso() : 0.0;
            case "estatura": return p.getEstatura() != null ? p.getEstatura() : 0.0;
            case "imc": return p.getImc() != null ? p.getImc() : 0.0;
            default: return 0.0;
        }
    }
}