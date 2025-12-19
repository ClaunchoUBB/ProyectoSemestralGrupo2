package choripan_solutions.db_exporter.Modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "Participante")
@Getter
@Setter
public class Participante {

    //Identificador Participante-----------------------------------------------------------------------------------------------------------------------------|
    @Id
    @Column(name = "codigo", length = 10)
    private String codigo;
    

    @Column(name = "nombre1", length = 40)
    private String nombre1;

    @Column(name = "nombre2", length = 200)
    private String nombre2;

    @Column(name = "apellido1", length = 40)
    private String apellido1;

    @Column(name = "apellido2", length = 40)
    private String apellido2;

    @Column(name = "correo", length = 30)
    private String correo;

    @Column(name = "numero", length = 15)
    private String numero;

    @Column(name = "estado")
    private Boolean estado;

    @ManyToOne
    @JoinColumn(name = "rut_usuario")
    private Usuario usuario;

    @Column(name = "grupo")
    private Integer grupo;

    @Column(name = "fecha_inclusion")
    @Temporal(TemporalType.DATE)
    private Date fechaInclusion;

    // Datos sociodemográficos-------------------------------------------------------------------------------------------------------------|
    @Min(value = 18, message = "La edad mínima permitida es 18 años")
    @Max(value = 110, message = "La edad máxima permitida es 110 años")
    @Column(name = "edad")
    private Integer edad;

    // 0 = femenino, 1 = masculino
    @Column(name = "sexo")
    private Boolean sexo;

    @Column(name = "nacionalidad", length = 20)
    private String nacionalidad;

    @Column(name = "direccion", length = 100)
    private String direccion;

    @Column(name = "comuna", length = 50)
    private String comuna;

    @Column(name = "ciudad", length = 50)
    private String ciudad;

    // 0 = urbana, 1 = rural
    @Column(name = "zona")
    private Boolean zona;

    // 0 = no, 1 = si
    @Column(name = "vive_hace_5_annos")
    private Boolean viveHace5annos;

    // 0 = básico, 1 = medio, 2 = universitario
    @Column(name = "nivel_educacional")
    private Integer nivelEducacional;

    @Column(name = "ocupacion_actual", length = 50)
    private String ocupacionActual;

    // 0 = ninguna, 1 = fonasa, 2 = isapre, 3 = capredema/Dipreca, 4 = otra
    @Column(name = "prevision_salud")
    private Integer previsionSalud;

    @Column(name = "otra_prevision", length = 50)
    private String otraPrevision;



    // Antecedentes clínicos-------------------------------------------------------------------------------------------------------------|
    //SOLO CASOS
    //perdón por lo largo de la variable :(
    @Column(name = "diagnostico_histologico_adenocarcinoma_gastrico")
    private Boolean diagnosticoHistologicoAdenocarcinomaGastrico;

    //SOLO CASOS
    @Column(name = "fecha_diagnostico")
    @Temporal(TemporalType.DATE)
    private Date fechaDiagnostico;

    @Column(name = "ant_fam_cancer_gastrico")
    private Boolean antCancerGastrico;

    @Column(name = "ant_fam_cancer_otro")
    private Boolean antCancerOtro;

    @Column(name = "cancer_otro", length = 50)
    private String cancerOtro;

    @Column(name = "otras_enfermedades", length = 100)
    private String otrasEnfermedades;

    @Column(name = "uso_cronico_medicametos_gastrolesivos")
    private Boolean usoCronicoMedicamentosGastrolesivos;

    @Column(name = "medicamento_gastrolesivo", length = 100)
    private String medicamentosGastroLesivos;

    @Column(name = "cirugia_gastrica_previa")
    private Boolean cirugiaGastricaPrevia;

    // Variables antropomórficas-------------------------------------------------------------------------------------------------------------|
    @DecimalMin(value = "40.0", message = "El peso mínimo permitido es 40 kg")
    @DecimalMax(value = "150.0", message = "El peso máximo permitido es 150 kg")
    @Column(name = "peso")
    private Float peso;

    @Column(name = "estatura")
    private Float estatura;

    @Column(name = "imc")
    private Float imc;

    // Tabaquismo y alcohol-------------------------------------------------------------------------------------------------------------|
    //Tabaquismo--------------------------
    // 0 = no, 1 = si
    @Column(name = "nunca_fumo")
    private Boolean nuncaFumo;

    // 0 = no, 1 = si
    @Column(name = "ex_fumador")
    private Boolean exFumador;

    // 0 = no, 1 = si
    @Column(name = "fuma_actualmente")
    private Boolean fumadorActual;

    // 0 = 1-9 (poco), 1 = 10-19 (moderado), 2 = más de 20 (mucho)
    @Column(name = "promedio_fuma_diario")
    private Integer promedioFumaDiario;

    // 0 = menor a 10 años, 1 = entre 10 y 20 años, 2 = más de 20 años
    @Column(name = "tiempo_total_fumador")
    private Integer tiempoTotalFumador;

    // 0 = menos de 5 años, 1 = entre 5 y 10 años, 2 = más de 10 años
    @Column(name = "ex_annos_sin_fumar")
    private Integer exAnnosSinFumar;

    //Consumo de alcohol----------------------
    @Column(name = "estado_consumo_alcohol")
    private Integer estadoConsumoAlcohol;

    // 0 = ocasional, 2 = regular,3 = frecuente
    @Column(name = "frecuencia_consumo_alcohol")
    private Integer frecuenciaConsumoAlcohol;

    //0 = 1-2 bebidas (poco), 1 = 3-4 bebidas (moderado), 2 = más de 5 bebidas (mucho)
    @Column(name = "cantidad_x_ocasion")
    private Integer cantidadXOcasion;

    //0 = menos de 10 años, 1 = entre 5 a 10 años, 2 = más de 10 años
    @Column(name = "annos_consumo_habitual")
    private Integer annosConsumo;

    //0 = menos de 5 años, 1 = entre 5 a 10 años, 2 = más de 10 años*
    @Column(name = "ex_annos_sin_beber")
    private Integer exAnnosSinBeber;


    // Factores dieta-------------------------------------------------------------------------------------------------------------|
    //consumo sem - 0 = <= 1, 1 = 2, 2 = >=3
    @Column(name = "carnes_procesadas")
    private Integer carnesProcesadas;

    //Agrega sal a la comida sin probar
    @Column(name = "alimentos_salados")
    private Boolean alimentosSalados;

    // 0 = <= 2 porciones, 1 = 3-4 porciones, 2 = >=5 porciones
    @Column(name = "frutas_verduras")
    private Integer frutasVerduras;

    @Column(name = "frituras")
    private Boolean frituras;

    // 0 = nunca/raramente, 1 = 1-2 sem, 2 = >=3 sem
    @Column(name = "consumo_alimentos_muy_condimentados")
    private Integer consumoAlimentosMuyCondimentados;

    // 0 = nunca, 1 = 1-2 sem, 2 = >=3 sem
    @Column(name = "bebida_caliente")
    private Integer bebidaCaliente;

    // Exposiciones---------------------------
    //nivel ocupacional
    @Column(name = "pesticidas")
    private Boolean pesticidas;

    //nivel ocupacional
    @Column(name = "otros_chemicos")
    private Boolean otrosChemicos;

    @Column(name = "tipo_chemicos", length = 100)
    private String tipoChemicos;

    // 0 = no, 1 = estacional, 2 = diario
    @Column(name = "humo_lenna")
    private Integer humoLenna;

    // 0 = red pública, 1 = pozo, 2 = camión aljibe, 3 = otro
    @Column(name = "fuente_principal_agua")
    private Integer fuentePrincipalAgua;

    @Column(name = "otra_fuente_agua", length = 30)
    private String otraFuenteAgua;

    // 0 = ninguno, 1 = hervir, 2= filtro, 3 = cloro
    @Column(name = "tratamiento_agua")
    private Integer tratamientoAgua;

    //Infección por Helicobacter pylori-------------------------------------------------------------------------------------------------------------|
    // Datos Helicobacter pylori (hel)
    /*@Column(name = "prueba_hel")
    private Integer pruebaHel;
    */
    // 0 = no, 1 = si, 2 = desconocido
    @Column(name = "resultado_hel")
    private Integer resultadoHel;

    // 0 = no, 1 = si, 2 = no recuerda
    @Column(name = "result_positiv_hel_pasado")
    private Integer resultPositivHelPasado;

    @Column(name = "anno_aprox_examen_pasado_hel")
    private Integer annoAproxExamenPasadoHel;

    @Column(name = "tipo_examen_pasado_hel", length = 20)
    private String tipoExamenPasadoHel;

    // 0 = no, 1 = si, 2 = no recuerda
    @Column(name = "recibio_tratamiento_errad_hel")
    private Integer recibioTratamientoErradHel;

    @Column(name = "anno_tratamiento_hel")
    private Integer annoTratamientoHel;

    @Column(name = "esquema_tratamiento_hel", length = 100)
    private String esquemaTratamientoHel;

    // 0 = aliento, 1 = antigeno, 2 = Serología, 3 = test rápido ureasa, 4 = histología/Biopsia, 5 otro*/
    @Column(name = "tipo_test_hel")
    private Integer tipoTestHel;

    @Column(name = "otro_test_hel", length = 50)
    private String otroTestHel;


    // (años) 0 = < 1, 1 = 1-5, 2 = > 5
    @Column(name = "tiempo_test")
    private Integer tiempoTest;

    // Uso de antibióticos o inhibidores de bomba de protones (IBP) en las 4 semanas previas al examen
    // 0 = no, 1 = si, 2 = no recuerda
    @Column(name = "uso_ibp")
    private Integer usoIbp;

    @Column(name = "repitio_examen_anteriormente")
    private Boolean repitioExamenAnteriormente;

    @Column(name = "fecha_examen_anterior")
    @Temporal(TemporalType.DATE)
    private Date fechaExamenAnterior;

    @Column(name = "resultado_examen_anterior", length = 50)
    private String resultadoExamenAnterior;

    //Histopatología (solo casos)-------------------------------------------------------------------------------------------------------------|

    // 0 = intestinal, 1 = difuso, 2 = mixto, 3 = otro
    @Column(name = "tipo_histologico")
    private Integer tipoHistologico;

    @Column(name = "otro_tipo_histologico", length = 50)
    private String otroTipoHistologico;

    // 0 = cardias, 1 = cuerpo, 2 = antro, 3 = difuso
    @Column(name = "tumor_ubicacion")
    private Integer tumorUbicacion;

    @Column(name = "estadio_clinico", length = 30)
    private String estadioClinico;


    public Participante() {}

    public void setIMC(){
        if (this.peso != null && this.estatura != null && this.estatura > 0) {
            this.imc = this.peso / (this.estatura * this.estatura);
        }
    }
}