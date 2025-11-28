package choripan_solutions.db_exporter.Modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.util.Date;

@Entity
@Table(name = "Participante")
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
    private boolean sexo;

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
    private boolean zona;

    // 0 = no, 1 = si
    @Column(name = "vive_hace_5_annos")
    private boolean viveHace5annos;

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
    @Column(name = "diagnostico_histologico_adenocarcinoma_gastrico")
    private Boolean diagnosticoHistologicoAdenocarcinomaGastrico;

    //SOLO CASOS
    @Column(name = "fecha_diagnostico")
    @Temporal(TemporalType.DATE)
    private Date fechaDiagnostico;

    @Column(name = "ant_fam_cancer_gastrico")
    private boolean antCancerGastrico;

    @Column(name = "ant_fam_cancer_otro")
    private boolean antCancerOtro;

    @Column(name = "cancer_otro", length = 50)
    private String cancerOtro;

    @Column(name = "otras_enfermedades", length = 100)
    private String otrasEnfermedades;

    @Column(name = "uso_cronico_medicametos_gastrolesivos")
    private Boolean usoCronicoMedicamentosGastrolesivos;

    @Column(name = "medicamento_gastrolesivo", length = 100)
    private String medicamentosGastroLesivos;

    @Column(name = "cirugia_gastrica_previa")
    private boolean cirugiaGastricaPrevia;

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
    private boolean nuncaFumo;

    // 0 = no, 1 = si
    @Column(name = "ex_fumador")
    private boolean exFumador;

    // 0 = no, 1 = si
    @Column(name = "fuma_actualmente")
    private boolean fumadorActual;

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
    private boolean alimentosSalados;

    // 0 = <= 2 porciones, 1 = 3-4 porciones, 2 = >=5 porciones
    @Column(name = "frutas_verduras")
    private Integer frutasVerduras;

    @Column(name = "frituras")
    private boolean frituras;

    // 0 = nunca/raramente, 1 = 1-2 sem, 2 = >=3 sem
    @Column(name = "consumo_alimentos_muy_condimentados")
    private Integer consumoAlimentosMuyCondimentados;

    // 0 = nunca, 1 = 1-2 sem, 2 = >=3 sem
    @Column(name = "bebida_caliente")
    private Integer bebidaCaliente;

    // Exposiciones---------------------------
    @Column(name = "pesticidas")
    private Integer pesticidas;

    @Column(name = "otros_chemicos")
    private Integer otrosChemicos;

    @Column(name = "tipo_chemicos", length = 100)
    private String tipoChemicos;

    @Column(name = "humo_lenna")
    private Integer humoLenna;

    @Column(name = "agua", length = 20)
    private String agua;

    @Column(name = "tratamiento_agua")
    private Integer tratamientoAgua;

    // Datos Helicobacter pylori
    @Column(name = "prueba_hel")
    private Integer pruebaHel;

    @Column(name = "resultado_hel")
    private Integer resultadoHel;

    @Column(name = "tiempo_test")
    private Integer tiempoTest;

    // Muestras biológicas y genéticas
    @Column(name = "fecha_toma_sangre")
    @Temporal(TemporalType.DATE)
    private Date fechaTomaSangre;

    @Column(name = "TLR9_rs5743836")
    private Integer tlr9Rs5743836;

    @Column(name = "TLR9_rs187084")
    private Integer tlr9Rs187084;

    @Column(name = "miR_146a_rs2910164")
    private Integer mir146aRs2910164;

    @Column(name = "miR_196a2_rs11614913")
    private Integer mir196a2Rs11614913;

    @Column(name = "MTHFR_rs1801133")
    private Integer mthfrRs1801133;

    @Column(name = "DNMT3B_rs1569686")
    private Integer dnmt3bRs1569686;

    // Histopatología
    @Column(name = "tipo")
    private Integer tipo;

    @Column(name = "otro", length = 50)
    private String otro;

    @Column(name = "tumor_ubicacion")
    private Integer tumorUbicacion;

    @Column(name = "estadio_clinico", length = 30)
    private String estadioClinico;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre1() {
        return nombre1;
    }

    public void setNombre1(String nombre1) {
        this.nombre1 = nombre1;
    }

    public String getNombre2() {
        return nombre2;
    }

    public void setNombre2(String nombre2) {
        this.nombre2 = nombre2;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Integer getGrupo() {
        return grupo;
    }

    public void setGrupo(Integer grupo) {
        this.grupo = grupo;
    }

    public Date getFechaInclusion() {
        return fechaInclusion;
    }

    public void setFechaInclusion(Date fechaInclusion) {
        this.fechaInclusion = fechaInclusion;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Integer getSexo() {
        return sexo;
    }

    public void setSexo(Integer sexo) {
        this.sexo = sexo;
    }

    public Integer getZona() {
        return zona;
    }

    public void setZona(Integer zona) {
        this.zona = zona;
    }

    public Integer getAñosVivienda() {
        return añosVivienda;
    }

    public void setAñosVivienda(Integer añosVivienda) {
        this.añosVivienda = añosVivienda;
    }

    public Integer getNivelEducacional() {
        return nivelEducacional;
    }

    public void setNivelEducacional(Integer nivelEducacional) {
        this.nivelEducacional = nivelEducacional;
    }

    public String getOcupacion() {
        return ocupacion;
    }

    public void setOcupacion(String ocupacion) {
        this.ocupacion = ocupacion;
    }

    public Date getFechaDiagnostico() {
        return fechaDiagnostico;
    }

    public void setFechaDiagnostico(Date fechaDiagnostico) {
        this.fechaDiagnostico = fechaDiagnostico;
    }

    public Integer getAntCancerGastrico() {
        return antCancerGastrico;
    }

    public void setAntCancerGastrico(Integer antCancerGastrico) {
        this.antCancerGastrico = antCancerGastrico;
    }

    public Integer getAntCancerOtro() {
        return antCancerOtro;
    }

    public void setAntCancerOtro(Integer antCancerOtro) {
        this.antCancerOtro = antCancerOtro;
    }

    public String getCancerOtro() {
        return cancerOtro;
    }

    public void setCancerOtro(String cancerOtro) {
        this.cancerOtro = cancerOtro;
    }

    public String getOtrasEnfermedades() {
        return otrasEnfermedades;
    }

    public void setOtrasEnfermedades(String otrasEnfermedades) {
        this.otrasEnfermedades = otrasEnfermedades;
    }

    public String getMedicamentosCronicos() {
        return medicamentosCronicos;
    }

    public void setMedicamentosCronicos(String medicamentosCronicos) {
        this.medicamentosCronicos = medicamentosCronicos;
    }

    public Integer getCirugiaGastricaPrevia() {
        return cirugiaGastricaPrevia;
    }

    public void setCirugiaGastricaPrevia(Integer cirugiaGastricaPrevia) {
        this.cirugiaGastricaPrevia = cirugiaGastricaPrevia;
    }

    public Float getPeso() {
        return peso;
    }

    public void setPeso(Float peso) {
        this.peso = peso;
    }

    public Float getEstatura() {
        return estatura;
    }

    public void setEstatura(Float estatura) {
        this.estatura = estatura;
    }

    public Float getImc() {
        return imc;
    }

    public void setImc(Float imc) {
        this.imc = imc;
    }

    public Integer getNuncaFumo() {
        return nuncaFumo;
    }

    public void setNuncaFumo(Integer nuncaFumo) {
        this.nuncaFumo = nuncaFumo;
    }

    public Integer getExFumador() {
        return exFumador;
    }

    public void setExFumador(Integer exFumador) {
        this.exFumador = exFumador;
    }

    public Integer getExAnnosSinFumar() {
        return exAnnosSinFumar;
    }

    public void setExAnnosSinFumar(Integer exAnnosSinFumar) {
        this.exAnnosSinFumar = exAnnosSinFumar;
    }

    public Integer getFumaActualmente() {
        return fumaActualmente;
    }

    public void setFumaActualmente(Integer fumaActualmente) {
        this.fumaActualmente = fumaActualmente;
    }

    public Integer getEdadInicioFuma() {
        return edadInicioFuma;
    }

    public void setEdadInicioFuma(Integer edadInicioFuma) {
        this.edadInicioFuma = edadInicioFuma;
    }

    public Integer getPromedioFumaDiario() {
        return promedioFumaDiario;
    }

    public void setPromedioFumaDiario(Integer promedioFumaDiario) {
        this.promedioFumaDiario = promedioFumaDiario;
    }

    public Integer getAñosFumador() {
        return añosFumador;
    }

    public void setAñosFumador(Integer añosFumador) {
        this.añosFumador = añosFumador;
    }

    public Integer getNuncaBebio() {
        return nuncaBebio;
    }

    public void setNuncaBebio(Integer nuncaBebio) {
        this.nuncaBebio = nuncaBebio;
    }

    public Integer getExBebedor() {
        return exBebedor;
    }

    public void setExBebedor(Integer exBebedor) {
        this.exBebedor = exBebedor;
    }

    public Integer getExAnnosSinBeber() {
        return exAnnosSinBeber;
    }

    public void setExAnnosSinBeber(Integer exAnnosSinBeber) {
        this.exAnnosSinBeber = exAnnosSinBeber;
    }

    public Integer getBebeActualmente() {
        return bebeActualmente;
    }

    public void setBebeActualmente(Integer bebeActualmente) {
        this.bebeActualmente = bebeActualmente;
    }

    public Integer getEdadInicioBebe() {
        return edadInicioBebe;
    }

    public void setEdadInicioBebe(Integer edadInicioBebe) {
        this.edadInicioBebe = edadInicioBebe;
    }

    public Integer getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(Integer frecuencia) {
        this.frecuencia = frecuencia;
    }

    public Integer getCantidadXOcasion() {
        return cantidadXOcasion;
    }

    public void setCantidadXOcasion(Integer cantidadXOcasion) {
        this.cantidadXOcasion = cantidadXOcasion;
    }

    public Integer getAnnosConsumo() {
        return annosConsumo;
    }

    public void setAnnosConsumo(Integer annosConsumo) {
        this.annosConsumo = annosConsumo;
    }

    public Integer getCarnesProcesadas() {
        return carnesProcesadas;
    }

    public void setCarnesProcesadas(Integer carnesProcesadas) {
        this.carnesProcesadas = carnesProcesadas;
    }

    public Integer getAlimentosSalados() {
        return alimentosSalados;
    }

    public void setAlimentosSalados(Integer alimentosSalados) {
        this.alimentosSalados = alimentosSalados;
    }

    public Integer getFrutasVerduras() {
        return frutasVerduras;
    }

    public void setFrutasVerduras(Integer frutasVerduras) {
        this.frutasVerduras = frutasVerduras;
    }

    public Integer getFrituras() {
        return frituras;
    }

    public void setFrituras(Integer frituras) {
        this.frituras = frituras;
    }

    public Integer getBebidaCaliente() {
        return bebidaCaliente;
    }

    public void setBebidaCaliente(Integer bebidaCaliente) {
        this.bebidaCaliente = bebidaCaliente;
    }

    public Integer getPesticidas() {
        return pesticidas;
    }

    public void setPesticidas(Integer pesticidas) {
        this.pesticidas = pesticidas;
    }

    public Integer getOtrosChemicos() {
        return otrosChemicos;
    }

    public void setOtrosChemicos(Integer otrosChemicos) {
        this.otrosChemicos = otrosChemicos;
    }

    public String getTipoChemicos() {
        return tipoChemicos;
    }

    public void setTipoChemicos(String tipoChemicos) {
        this.tipoChemicos = tipoChemicos;
    }

    public Integer getHumoLenna() {
        return humoLenna;
    }

    public void setHumoLenna(Integer humoLenna) {
        this.humoLenna = humoLenna;
    }

    public String getAgua() {
        return agua;
    }

    public void setAgua(String agua) {
        this.agua = agua;
    }

    public Integer getTratamientoAgua() {
        return tratamientoAgua;
    }

    public void setTratamientoAgua(Integer tratamientoAgua) {
        this.tratamientoAgua = tratamientoAgua;
    }

    public Integer getPruebaHel() {
        return pruebaHel;
    }

    public void setPruebaHel(Integer pruebaHel) {
        this.pruebaHel = pruebaHel;
    }

    public Integer getResultadoHel() {
        return resultadoHel;
    }

    public void setResultadoHel(Integer resultadoHel) {
        this.resultadoHel = resultadoHel;
    }

    public Integer getTiempoTest() {
        return tiempoTest;
    }

    public void setTiempoTest(Integer tiempoTest) {
        this.tiempoTest = tiempoTest;
    }

    public Date getFechaTomaSangre() {
        return fechaTomaSangre;
    }

    public void setFechaTomaSangre(Date fechaTomaSangre) {
        this.fechaTomaSangre = fechaTomaSangre;
    }

    public Integer getTlr9Rs5743836() {
        return tlr9Rs5743836;
    }

    public void setTlr9Rs5743836(Integer tlr9Rs5743836) {
        this.tlr9Rs5743836 = tlr9Rs5743836;
    }

    public Integer getTlr9Rs187084() {
        return tlr9Rs187084;
    }

    public void setTlr9Rs187084(Integer tlr9Rs187084) {
        this.tlr9Rs187084 = tlr9Rs187084;
    }

    public Integer getMir146aRs2910164() {
        return mir146aRs2910164;
    }

    public void setMir146aRs2910164(Integer mir146aRs2910164) {
        this.mir146aRs2910164 = mir146aRs2910164;
    }

    public Integer getMir196a2Rs11614913() {
        return mir196a2Rs11614913;
    }

    public void setMir196a2Rs11614913(Integer mir196a2Rs11614913) {
        this.mir196a2Rs11614913 = mir196a2Rs11614913;
    }

    public Integer getMthfrRs1801133() {
        return mthfrRs1801133;
    }

    public void setMthfrRs1801133(Integer mthfrRs1801133) {
        this.mthfrRs1801133 = mthfrRs1801133;
    }

    public Integer getDnmt3bRs1569686() {
        return dnmt3bRs1569686;
    }

    public void setDnmt3bRs1569686(Integer dnmt3bRs1569686) {
        this.dnmt3bRs1569686 = dnmt3bRs1569686;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public String getOtro() {
        return otro;
    }

    public void setOtro(String otro) {
        this.otro = otro;
    }

    public Integer getTumorUbicacion() {
        return tumorUbicacion;
    }

    public void setTumorUbicacion(Integer tumorUbicacion) {
        this.tumorUbicacion = tumorUbicacion;
    }

    public String getEstadioClinico() {
        return estadioClinico;
    }

    public void setEstadioClinico(String estadioClinico) {
        this.estadioClinico = estadioClinico;
    }

    





}



