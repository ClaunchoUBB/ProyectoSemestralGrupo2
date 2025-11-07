package choripan_solutions.db_exporter.Modelo;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@Entity
@Table(name = "Participante")
public class Participante {

    @Id // La clave primaria es "codigo" (String)
    @Column(name = "codigo", length = 10)
    private String codigo;

    @Column(name = "nombre1")
    private String nombre1;

    @Column(name = "nombre2")
    private String nombre2;

    @Column(name = "apellido1")
    private String apellido1;

    @Column(name = "apellido2")
    private String apellido2;

    @Column(name = "correo")
    private String correo;

    @Column(name = "numero")
    private String numero;

    @Column(name = "estado", nullable = false)
    private Boolean estado;
    
    @Column(name = "grupo", nullable = false)
    private int grupo;

    @Column(name = "fecha_inclusion", nullable = false)
    private Date fechaInclusion;
    
    // DATOS SOCIODEMOGRAFICOS -------------------------

    @Column(name = "edad")
    private int edad;

    @Column(name = "nacionalidad", length = 20)
    private String nacionalidad;

    @Column(name = "direccion", length = 100)
    private String direccion;

    @Column(name = "sexo")
    private int sexo;

    @Column(name = "zona")
    private int zona;

    @Column(name ="años_vivienda")
    private int añosVivienda;

    @Column(name = "nivel_educacional")
    private int nivel_educacional;

    @Column(name = "ocupacion", length = 50)
    private String ocupacion;

    //--- Antecedentes clinicos ---- 

    @Column(name = "fecha_diagnostico")
    private Date fecha_diagnostico;

    @Column(name = "ant_cancer_gastrico")
    private int ant_cancer_gastrico;

    @Column(name = "ant_cancer_otro")
    private int ant_cancer_otro;

    @Column(name = "cancer_otro", length = 50)
    private String cancer_otro;

    @Column(name = "otras_enfermedades", length = 100)
    private String otras_enfermedades;

    @Column(name = "medicamentos_cronicos", length = 100)
    private String medicamentos_cronicos;

    @Column(name = "cirugia_plastica_previa")
    private int cirugia_plastica_previa;

    // --- Variables antropomorficas ------

    @Column(name = "peso")
    private float peso;

    @Column(name = "estatura")
    private float estatura;

    @Column(name = "imc")
    private float imc;

    // Tabaquismo y alcohol

    @Column(name = "nunca_fumo")
    private int nunca_fumo;
    
    @Column(name = "ex_fumador")
    private int ex_fumador;

    @Column(name = "ex_annos_sin_fumar ")
    private int ex_annos_sin_fumar ;

    @Column(name = "fumador_actualmente")
    private int fumador_actualmente;

    @Column(name = "edad_inicio_fuma")
    private int edad_inicio_fuma;

    @Column(name = "promedio_fuma_diario")
    private int promedio_fuma_diario;

    @Column(name = "annos_fumador")
    private int annos_fumador;

    @Column(name = "nunca_bebio")
    private int nunca_bebio;

    @Column(name = "ex_bebedor")
    private int ex_bebedor;

    @Column(name = "ex_annos_sin_beber")
    private int ex_annos_sin_beber;

    @Column(name = "bebe_actualmente")
    private int bebe_actualmente;

    @Column(name = "edad_inicio_bebe")
    private int edad_inicio_bebe;

    @Column(name = "frecuencia")
    private int frecuencia;

    @Column(name = "cantidad_x_ocasion")
    private int cantidad_x_ocasion;

    @Column(name = "annos_consumo")
    private int annos_consumo;

    // --- Factores dieta

    @Column(name = "carnes_procesadas")
    private int carnes_procesadas;

    @Column(name = "alimentos_salados")
    private int alimentos_salados;

    @Column(name = "frutas_verduras")
    private int frutas_verduras;   

    @Column(name = "frituras")
    private int frituras;

    @Column(name = "bebida_caliente")
    private int bebida_caliente;

    // exposiciones ---------------------

    @Column(name = "pesticidas")
    private int pesticidas;

    @Column(name = "otros_chemicos")
    private int otros_chemicos;

    @Column(name = "tipo_chemicos", length = 100)
    private String tipo_chemicos;

    @Column(name = "humo_lenna")
    private int humo_lenna;

    @Column(name = "agua", length = 20)
    private String agua;

    @Column(name = "tratamiento_agua")
    private int tratamiento_agua;

    //datos helicobacter pylori

    @Column(name = "prueba_hel")
    private int prueba_hel;

    @Column(name = "resultado_hel")
    private int resultado_hel;

    @Column(name = "tiempo_test")
    private int tiempo_test;

    //--- muestras biologicas y geneticas ---

    @Column(name = "fecha_toma_sangre")
    private Date fecha_toma_sangre;

    @Column(name = "TLR9_rs5743836")
    private int TLR9_rs5743836;

    @Column(name = "TLR9_rs187084")
    private int TLR9_rs187084;

    @Column(name = "miR-146a_rs2910164")
    private int miR_146a_rs2910164;

    @Column(name = "miR-196a2_rs11614913")
    private int miR_196a2_rs11614913;

    @Column(name = "MTHFR_rs1801133")
    private int MTHFR_rs1801133;

    @Column(name = "DNMT3B rs1569686")
    private int DNMT3B_rs1569686;

    // histopatologia (solo casos)

    @Column(name = "tipo")
    private int tipo;
    
    @Column(name = "otro", length = 50)
    private String otro;

    @Column(name = "tumor_ubicacion")
    private int tumor_ubicacion;
    
    @Column(name = "estadio_clinico", length = 30)
    private String estadio_clinico; 


    // --- Relación ---

    // Muchos Participantes pertenecen a un Usuario
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rut_usuario") // Esta es la columna FOREIGN KEY
    @JsonIgnore
    private Usuario usuario;
}