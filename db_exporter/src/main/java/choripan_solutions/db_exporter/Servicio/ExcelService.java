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
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ExcelService {

    @Autowired
    private ParticipanteRepo participanteRepository;

    public ByteArrayInputStream crearExcelDeParticipantes() throws IOException {
        
        // 1. Obtener todos los participantes primero para cálculos estadísticos
        List<Participante> participantes = participanteRepository.findAll();

        // 2. Calcular Promedios y Medianas globales requeridos por el documento
        double edadPromedio = calcularPromedio(participantes, "edad");
        double edadMediana = calcularMediana(participantes, "edad");
        
        double pesoPromedio = calcularPromedio(participantes, "peso");
        double pesoMediana = calcularMediana(participantes, "peso");
        
        double estaturaPromedio = calcularPromedio(participantes, "estatura");
        double estaturaMediana = calcularMediana(participantes, "estatura");
        
        double imcPromedio = calcularPromedio(participantes, "imc");
        double imcMediana = calcularMediana(participantes, "imc");

        // 3. Definir Encabezados según el documento DOCX
        String[] columns = {
            "Código del participante", // ID
            
            // Sociodemográficas
            "Edad", "Edad_promedio", "Edad_mediana", "Edad_50", "Edad_60", 
            "Sexo", "Residencia_Urbana5", "Residencia_Rural5a", 
            "NivelEduc_Basico", "NivelEduc_BasicoMedio", 
            "Prevision_FonasaVsOtros", "Prevision_IsapreVsOtros",

            // Clínicos
            "CA_FamiliaGastrico", "CA_FamiliaOtros", "EnfermedadesRelevantes", 
            "UsoCronico_Medicamentos", "CirugiaGastricaPrevia",

            // Antropométricas
            "Peso", "peso_promedio", "peso_mediana",
            "Estatura", "estatura_promedio", "estatura_mediana",
            "IMC", "imc_promedio", "imc_mediana", "IMC_Menor25",

            // Tabaquismo (Simplificado a categorías disponibles en Entidad)
            "tabaco_nunca_vs_otros", "tabaco_actual", "tabaco_carga_alta",

            // Alcohol
            "alcohol_alguna_vez", "alcohol_frecuencia_alta",

            // Dietarios y Ambientales
            "CarnesProcesadas_Menos3", "CarnesProcesadas_Max1",
            "FrutasVerduras_3oMas", "FrutasVerduras_5oMas",
            "CondimentosFrecAlta", "BebidasCalientes_AltaFrecuencia",
            "AñadeSal_Comida", "Frituras_Frecuente",
            "Pesticidas_Exposicion", "CompuestosQuimicos_Exposicion",
            "FuenteYTratamientoAgua", "Lena_Frecuente", "Lena_AlgunaExposicion",
            
            // H. Pylori
            "HPylori_AlgunaVezPositivo"
        };

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Variables Análisis");
            Row headerRow = sheet.createRow(0);

            // Crear encabezados
            for (int col = 0; col < columns.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(columns[col]);
            }

            int rowIdx = 1;
            for (Participante p : participantes) {
                Row row = sheet.createRow(rowIdx++);
                int col = 0;

                // --- ID ---
                row.createCell(col++).setCellValue(p.getCodigo());

                // --- SOCIODEMOGRÁFICAS ---
                Integer edad = p.getEdad() != null ? p.getEdad() : 0;
                
                // 1. Edad (cuantitativa)
                row.createCell(col++).setCellValue(edad);
                // 2. Edad_promedio (> promedio)
                row.createCell(col++).setCellValue(edad > edadPromedio ? 1 : 0);
                // 3. Edad_mediana (> mediana)
                row.createCell(col++).setCellValue(edad > edadMediana ? 1 : 0);
                // 4. Edad_50 (>= 50)
                row.createCell(col++).setCellValue(edad >= 50 ? 1 : 0);
                // 5. Edad_60 (>= 60)
                row.createCell(col++).setCellValue(edad >= 60 ? 1 : 0);
                
                // 6. Sexo (Participante: 0=Fem, 1=Masc. Doc: Categoría 0=Mujer, 1=Hombre. Coincide)
                row.createCell(col++).setCellValue(Boolean.TRUE.equals(p.getSexo()) ? 1 : 0);

                // 7. Residencia_Urbana5 (Vive en zona urbana >=5 años o rural <5 años)
                // Zona: 0=Urbana, 1=Rural. ViveHace5: 1=Si, 0=No.
                boolean esUrbana = Boolean.FALSE.equals(p.getZona()); 
                boolean masDe5 = Boolean.TRUE.equals(p.getViveHace5annos());
                // Riesgo (1): Urbana y >5 (esUrbana && masDe5) O Rural y <5 (!esUrbana && !masDe5)
                row.createCell(col++).setCellValue((esUrbana && masDe5) || (!esUrbana && !masDe5) ? 1 : 0);

                // 8. Residencia_Rural5a (Inverso lógico del anterior según doc)
                row.createCell(col++).setCellValue((!esUrbana && masDe5) || (esUrbana && !masDe5) ? 1 : 0);

                // 9. NivelEduc_Basico (0=Básico vs Medio/Sup)
                // Entidad: 0=básico, 1=medio, 2=univ
                // Doc Cat 0: Básico, Cat 1: Medio/Sup. OJO: Doc dice Cat 0 es riesgo.
                // Asumiremos lógica binaria simple: 0 = Basico, 1 = Medio/Sup
                Integer educ = p.getNivelEducacional();
                row.createCell(col++).setCellValue((educ != null && educ > 0) ? 1 : 0);

                // 10. NivelEduc_BasicoMedio (Basico/Medio vs Superior)
                row.createCell(col++).setCellValue((educ != null && educ == 2) ? 1 : 0);

                // 11. Prevision_FonasaVsOtros
                // Entidad: 0=ninguna, 1=fonasa, 2=isapre, 3=capre/dipre, 4=otra
                // Doc Cat 1: Isapre/Otras. Cat 0: Fonasa/Sin prev.
                Integer prev = p.getPrevisionSalud();
                boolean esFonasaOSin = (prev != null && (prev == 0 || prev == 1));
                row.createCell(col++).setCellValue(esFonasaOSin ? 0 : 1);

                // 12. Prevision_IsapreVsOtros
                boolean esIsapre = (prev != null && prev == 2);
                row.createCell(col++).setCellValue(esIsapre ? 0 : 1);

                // --- CLÍNICOS ---
                // 1. Familia Gastrico
                row.createCell(col++).setCellValue(Boolean.TRUE.equals(p.getAntCancerGastrico()) ? 1 : 0);
                // 2. Familia Otros
                row.createCell(col++).setCellValue(Boolean.TRUE.equals(p.getAntCancerOtro()) ? 1 : 0);
                // 3. Enfermedades Relevantes (se usa 'otrasEnfermedades' como proxy si no es null/vacio)
                boolean tieneEnf = p.getOtrasEnfermedades() != null && !p.getOtrasEnfermedades().isEmpty();
                row.createCell(col++).setCellValue(tieneEnf ? 1 : 0);
                // 4. Uso Cronico Medicamentos
                row.createCell(col++).setCellValue(Boolean.TRUE.equals(p.getUsoCronicoMedicamentosGastrolesivos()) ? 1 : 0);
                // 5. Cirugia Previa
                row.createCell(col++).setCellValue(Boolean.TRUE.equals(p.getCirugiaGastricaPrevia()) ? 1 : 0);

                // --- ANTROPOMÉTRICAS ---
                Float peso = p.getPeso() != null ? p.getPeso() : 0f;
                Float estatura = p.getEstatura() != null ? p.getEstatura() : 0f;
                Float imc = p.getImc() != null ? p.getImc() : 0f;

                row.createCell(col++).setCellValue(peso);
                row.createCell(col++).setCellValue(peso > pesoPromedio ? 1 : 0);
                row.createCell(col++).setCellValue(peso > pesoMediana ? 1 : 0);

                row.createCell(col++).setCellValue(estatura);
                row.createCell(col++).setCellValue(estatura >= estaturaPromedio ? 0 : 1); // Lógica invertida según doc
                row.createCell(col++).setCellValue(estatura >= estaturaMediana ? 0 : 1);  // Lógica invertida según doc

                row.createCell(col++).setCellValue(imc);
                row.createCell(col++).setCellValue(imc > imcPromedio ? 1 : 0);
                row.createCell(col++).setCellValue(imc > imcMediana ? 1 : 0);
                // IMC Menor 25 (Cat 0 < 25, Cat 1 >= 25)
                row.createCell(col++).setCellValue(imc >= 25 ? 1 : 0);

                // --- TABAQUISMO ---
                // Mapeo aproximado basado en campos disponibles
                // 1. Nunca fumó vs Otros
                row.createCell(col++).setCellValue(Boolean.TRUE.equals(p.getNuncaFumo()) ? 0 : 1);
                // 2. Actual
                row.createCell(col++).setCellValue(Boolean.TRUE.equals(p.getFumadorActual()) ? 1 : 0);
                // 3. Carga Alta (Promedio diario 2 = >20 cigarrillos)
                Integer fumaDiario = p.getPromedioFumaDiario();
                row.createCell(col++).setCellValue((fumaDiario != null && fumaDiario == 2) ? 1 : 0);

                // --- ALCOHOL ---
                // 1. Alguna vez (Si estado != null y no es 0/Nunca) - Ajustar según lógica de negocio exacta
                Integer estadoAlcohol = p.getEstadoConsumoAlcohol();
                // Asumiendo lógica genérica donde 0 suele ser abstemio en encuestas
                row.createCell(col++).setCellValue((estadoAlcohol != null && estadoAlcohol > 0) ? 1 : 0);
                
                // 2. Frecuencia alta (frecuenciaConsumoAlcohol: 2=regular, 3=frecuente)
                Integer frecAlcohol = p.getFrecuenciaConsumoAlcohol();
                row.createCell(col++).setCellValue((frecAlcohol != null && frecAlcohol >= 3) ? 1 : 0);


                // --- DIETARIOS ---
                // Carnes Procesadas (0=<=1, 1=2, 2=>=3)
                Integer carnes = p.getCarnesProcesadas();
                // Menos3: Cat 0 (<=2 veces) vs Cat 1 (>=3 veces). En Java valor 2 es >=3.
                row.createCell(col++).setCellValue((carnes != null && carnes == 2) ? 1 : 0);
                // Max1: Cat 0 (<=1 vez) vs Cat 1 (>=2 veces). En Java valor 1 y 2 son >=2.
                row.createCell(col++).setCellValue((carnes != null && carnes >= 1) ? 1 : 0);

                // Frutas y Verduras (0=<=2, 1=3-4, 2=>=5)
                Integer frutas = p.getFrutasVerduras();
                // 3oMas: Cat 0 (3-4 o >=5) vs Cat 1 (<=2). OJO: Doc dice Cat 0 es Protector (>3).
                // Java valor 1 y 2 son >=3 porciones.
                row.createCell(col++).setCellValue((frutas != null && frutas >= 1) ? 0 : 1); // 0 es protector
                
                // 5oMas: Cat 0 (>=5) vs Cat 1 (<5). Java valor 2 es >=5.
                row.createCell(col++).setCellValue((frutas != null && frutas == 2) ? 0 : 1);

                // Condimentos (0=nunca, 1=1-2, 2=>=3)
                Integer condimentos = p.getConsumoAlimentosMuyCondimentados();
                // Doc: Alta Frec (>=3 veces). Java valor 2.
                row.createCell(col++).setCellValue((condimentos != null && condimentos == 2) ? 1 : 0);

                // Bebidas Calientes (0=nunca, 1=1-2, 2=>=3)
                Integer calientes = p.getBebidaCaliente();
                // Doc: Alta Frec (>=3). Java valor 2.
                row.createCell(col++).setCellValue((calientes != null && calientes == 2) ? 1 : 0);

                // Sal
                row.createCell(col++).setCellValue(Boolean.TRUE.equals(p.getAlimentosSalados()) ? 1 : 0);

                // Frituras
                row.createCell(col++).setCellValue(Boolean.TRUE.equals(p.getFrituras()) ? 1 : 0);

                // Exposiciones
                row.createCell(col++).setCellValue(Boolean.TRUE.equals(p.getPesticidas()) ? 1 : 0);
                row.createCell(col++).setCellValue(Boolean.TRUE.equals(p.getOtrosChemicos()) ? 1 : 0);

                // Agua (0=red, 1=pozo, 2=aljibe, 3=otro)
                // Tratamiento (0=ninguno, 1=hervir, 2=filtro, 3=cloro)
                // Doc: Cat 0 (Red/Pozo + Tratamiento). Cat 1 (Pozo/Otro + Sin tratamiento)
                // Simplificación lógica: Si fuente es pozo/otro Y tratamiento es ninguno -> Riesgo 1.
                Integer fuente = p.getFuentePrincipalAgua();
                Integer trat = p.getTratamientoAgua();
                boolean fuenteRiesgosa = (fuente != null && fuente != 0); // No es red pública
                boolean sinTratamiento = (trat != null && trat == 0);
                row.createCell(col++).setCellValue((fuenteRiesgosa && sinTratamiento) ? 1 : 0);

                // Leña Frecuente (Humo Lena: 0=no, 1=estacional, 2=diario)
                Integer lena = p.getHumoLenna();
                // Doc: Frecuente = Diario (Java 2)
                row.createCell(col++).setCellValue((lena != null && lena == 2) ? 1 : 0);
                // Doc: Alguna vez = Estacional o Diario (Java 1 o 2)
                row.createCell(col++).setCellValue((lena != null && lena > 0) ? 1 : 0);

                // --- H PYLORI ---
                // Alguna vez positivo (resultPositivHelPasado: 1=si, resultadoHel: 1=si)
                boolean hpPasado = (p.getResultPositivHelPasado() != null && p.getResultPositivHelPasado() == 1);
                boolean hpActual = (p.getResultadoHel() != null && p.getResultadoHel() == 1);
                row.createCell(col++).setCellValue((hpPasado || hpActual) ? 1 : 0);
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }

    // --- Métodos Auxiliares para Estadísticas ---

    private double calcularPromedio(List<Participante> lista, String campo) {
        return lista.stream()
                .mapToDouble(p -> obtenerValorNumerico(p, campo))
                .filter(val -> val > 0) // Ignorar ceros o nulos en promedio
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