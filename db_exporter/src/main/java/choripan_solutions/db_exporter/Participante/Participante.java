package choripan_solutions.db_exporter.Participante;
import choripan_solutions.db_exporter.Usuario.Usuario;



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
    //perdón por lo largo de la variable :(
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
    //nivel ocupacional
    @Column(name = "pesticidas")
    private boolean pesticidas;

    //nivel ocupacional
    @Column(name = "otros_chemicos")
    private boolean otrosChemicos;

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

    // Getters and Setters ----------------------------------------------------------------------------------------------------------------------------------------------|
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

    // Datos sociodemográficos-------------------------------------------------------------------------------------------------------------|
    public Integer getEdad() {
        return edad;
    }
    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public boolean isSexo() {
        return sexo;
    }
    public void setSexo(boolean sexo) {
        this.sexo = sexo;
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

    public String getComuna() {
        return comuna;
    }
    public void setComuna(String comuna) {
        this.comuna = comuna;
    }

    public String getCiudad() {
        return ciudad;
    }
    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public boolean isZona() {
        return zona;
    }
    public void setZona(boolean zona) {
        this.zona = zona;
    }

    public boolean isViveHace5annos() {
        return viveHace5annos;
    }
    public void setViveHace5annos(boolean viveHace5annos) {
        this.viveHace5annos = viveHace5annos;
    }

    public Integer getNivelEducacional() {
        return nivelEducacional;
    }
    public void setNivelEducacional(Integer nivelEducacional) {
        this.nivelEducacional = nivelEducacional;
    }

    public String getOcupacionActual() {
        return ocupacionActual;
    }
    public void setOcupacionActual(String ocupacionActual) {
        this.ocupacionActual = ocupacionActual;
    }

    public Integer getPrevisionSalud() {
        return previsionSalud;
    }
    public void setPrevisionSalud(Integer previsionSalud) {
        this.previsionSalud = previsionSalud;
    }

    public String getOtraPrevision() {
        return otraPrevision;
    }
    public void setOtraPrevision(String otraPrevision) {
        this.otraPrevision = otraPrevision;
    }

    // Antecedentes clínicos-------------------------------------------------------------------------------------------------------------|
    public Boolean getDiagnosticoHistologicoAdenocarcinomaGastrico() {
        return diagnosticoHistologicoAdenocarcinomaGastrico;
    }
    public void setDiagnosticoHistologicoAdenocarcinomaGastrico(Boolean diagnosticoHistologicoAdenocarcinomaGastrico) {
        this.diagnosticoHistologicoAdenocarcinomaGastrico = diagnosticoHistologicoAdenocarcinomaGastrico;
    }

    public Date getFechaDiagnostico() {
        return fechaDiagnostico;
    }
    public void setFechaDiagnostico(Date fechaDiagnostico) {
        this.fechaDiagnostico = fechaDiagnostico;
    }

    public boolean isAntCancerGastrico() {
        return antCancerGastrico;
    }
    public void setAntCancerGastrico(boolean antCancerGastrico) {
        this.antCancerGastrico = antCancerGastrico;
    }

    public boolean isAntCancerOtro() {
        return antCancerOtro;
    }
    public void setAntCancerOtro(boolean antCancerOtro) {
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

    public Boolean getUsoCronicoMedicamentosGastrolesivos() {
        return usoCronicoMedicamentosGastrolesivos;
    }
    public void setUsoCronicoMedicamentosGastrolesivos(Boolean usoCronicoMedicamentosGastrolesivos) {
        this.usoCronicoMedicamentosGastrolesivos = usoCronicoMedicamentosGastrolesivos;
    }

    public String getMedicamentosGastroLesivos() {
        return medicamentosGastroLesivos;
    }
    public void setMedicamentosGastroLesivos(String medicamentosGastroLesivos) {
        this.medicamentosGastroLesivos = medicamentosGastroLesivos;
    }

    public boolean isCirugiaGastricaPrevia() {
        return cirugiaGastricaPrevia;
    }
    public void setCirugiaGastricaPrevia(boolean cirugiaGastricaPrevia) {
        this.cirugiaGastricaPrevia = cirugiaGastricaPrevia;
    }


    // Variables antropomórficas-------------------------------------------------------------------------------------------------------------|
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
    public void setImc() {
        this.imc = this.peso / (this.estatura * this.estatura);
    }

    // Tabaquismo-------------------------------------------------------------------------------------------------------------|
    public boolean isNuncaFumo() {
        return nuncaFumo;
    }
    public void setNuncaFumo(boolean nuncaFumo) {
        this.nuncaFumo = nuncaFumo;
    }

    public boolean isExFumador() {
        return exFumador;
    }
    public void setExFumador(boolean exFumador) {
        this.exFumador = exFumador;
    }

    public boolean isFumadorActual() {
        return fumadorActual;
    }
    public void setFumadorActual(boolean fumadorActual) {
        this.fumadorActual = fumadorActual;
    }

    public Integer getPromedioFumaDiario() {
        return promedioFumaDiario;
    }
    public void setPromedioFumaDiario(Integer promedioFumaDiario) {
        this.promedioFumaDiario = promedioFumaDiario;
    }

    public Integer getTiempoTotalFumador() {
        return tiempoTotalFumador;
    }
    public void setTiempoTotalFumador(Integer tiempoTotalFumador) {
        this.tiempoTotalFumador = tiempoTotalFumador;
    }

    public Integer getExAnnosSinFumar() {
        return exAnnosSinFumar;
    }
    public void setExAnnosSinFumar(Integer exAnnosSinFumar) {
        this.exAnnosSinFumar = exAnnosSinFumar;
    }

    // Consumo de alcohol-------------------------------------------------------------------------------------------------------------|
    public Integer getEstadoConsumoAlcohol() {
        return estadoConsumoAlcohol;
    }
    public void setEstadoConsumoAlcohol(Integer estadoConsumoAlcohol) {
        this.estadoConsumoAlcohol = estadoConsumoAlcohol;
    }

    public Integer getFrecuenciaConsumoAlcohol() {
        return frecuenciaConsumoAlcohol;
    }
    public void setFrecuenciaConsumoAlcohol(Integer frecuenciaConsumoAlcohol) {
        this.frecuenciaConsumoAlcohol = frecuenciaConsumoAlcohol;
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

    public Integer getExAnnosSinBeber() {
        return exAnnosSinBeber;
    }
    public void setExAnnosSinBeber(Integer exAnnosSinBeber) {
        this.exAnnosSinBeber = exAnnosSinBeber;
    }

    // Factores dieta-------------------------------------------------------------------------------------------------------------|
    public Integer getCarnesProcesadas() {
        return carnesProcesadas;
    }
    public void setCarnesProcesadas(Integer carnesProcesadas) {
        this.carnesProcesadas = carnesProcesadas;
    }

    public boolean isAlimentosSalados() {
        return alimentosSalados;
    }
    public void setAlimentosSalados(boolean alimentosSalados) {
        this.alimentosSalados = alimentosSalados;
    }

    public Integer getFrutasVerduras() {
        return frutasVerduras;
    }
    public void setFrutasVerduras(Integer frutasVerduras) {
        this.frutasVerduras = frutasVerduras;
    }

    public boolean isFrituras() {
        return frituras;
    }
    public void setFrituras(boolean frituras) {
        this.frituras = frituras;
    }

    public Integer getConsumoAlimentosMuyCondimentados() {
        return consumoAlimentosMuyCondimentados;
    }
    public void setConsumoAlimentosMuyCondimentados(Integer consumoAlimentosMuyCondimentados) {
        this.consumoAlimentosMuyCondimentados = consumoAlimentosMuyCondimentados;
    }

    public Integer getBebidaCaliente() {
        return bebidaCaliente;
    }
    public void setBebidaCaliente(Integer bebidaCaliente) {
        this.bebidaCaliente = bebidaCaliente;
    }

    // Exposiciones-------------------------------------------------------------------------------------------------------------|
    public boolean isPesticidas() {
        return pesticidas;
    }
    public void setPesticidas(boolean pesticidas) {
        this.pesticidas = pesticidas;
    }

    public boolean isOtrosChemicos() {
        return otrosChemicos;
    }
    public void setOtrosChemicos(boolean otrosChemicos) {
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

    public Integer getFuentePrincipalAgua() {
        return fuentePrincipalAgua;
    }
    public void setFuentePrincipalAgua(Integer fuentePrincipalAgua) {
        this.fuentePrincipalAgua = fuentePrincipalAgua;
    }

    public String getOtraFuenteAgua() {
        return otraFuenteAgua;
    }
    public void setOtraFuenteAgua(String otraFuenteAgua) {
        this.otraFuenteAgua = otraFuenteAgua;
    }

    public Integer getTratamientoAgua() {
        return tratamientoAgua;
    }
    public void setTratamientoAgua(Integer tratamientoAgua) {
        this.tratamientoAgua = tratamientoAgua;
    }

    //Infección por Helicobacter pylori-------------------------------------------------------------------------------------------------------------|
    public Integer getResultadoHel() {
        return resultadoHel;
    }
    public void setResultadoHel(Integer resultadoHel) {
        this.resultadoHel = resultadoHel;
    }

    public Integer getResultPositivHelPasado() {
        return resultPositivHelPasado;
    }
    public void setResultPositivHelPasado(Integer resultPositivHelPasado) {
        this.resultPositivHelPasado = resultPositivHelPasado;
    }

    public Integer getAnnoAproxExamenPasadoHel() {
        return annoAproxExamenPasadoHel;
    }
    public void setAnnoAproxExamenPasadoHel(Integer annoAproxExamenPasadoHel) {
        this.annoAproxExamenPasadoHel = annoAproxExamenPasadoHel;
    }

    public String getTipoExamenPasadoHel() {
        return tipoExamenPasadoHel;
    }
    public void setTipoExamenPasadoHel(String tipoExamenPasadoHel) {
        this.tipoExamenPasadoHel = tipoExamenPasadoHel;
    }

    public Integer getRecibioTratamientoErradHel() {
        return recibioTratamientoErradHel;
    }
    public void setRecibioTratamientoErradHel(Integer recibioTratamientoErradHel) {
        this.recibioTratamientoErradHel = recibioTratamientoErradHel;
    }

    public Integer getAnnoTratamientoHel() {
        return annoTratamientoHel;
    }
    public void setAnnoTratamientoHel(Integer annoTratamientoHel) {
        this.annoTratamientoHel = annoTratamientoHel;
    }

    public String getEsquemaTratamientoHel() {
        return esquemaTratamientoHel;
    }
    public void setEsquemaTratamientoHel(String esquemaTratamientoHel) {
        this.esquemaTratamientoHel = esquemaTratamientoHel;
    }

    public Integer getTipoTestHel() {
        return tipoTestHel;
    }
    public void setTipoTestHel(Integer tipoTestHel) {
        this.tipoTestHel = tipoTestHel;
    }

    public String getOtroTestHel() {
        return otroTestHel;
    }
    public void setOtroTestHel(String otroTestHel) {
        this.otroTestHel = otroTestHel;
    }

    public Integer getTiempoTest() {
        return tiempoTest;
    }
    public void setTiempoTest(Integer tiempoTest) {
        this.tiempoTest = tiempoTest;
    }

    public Integer getUsoIbp() {
        return usoIbp;
    }
    public void setUsoIbp(Integer usoIbp) {
        this.usoIbp = usoIbp;
    }

    public Boolean getRepitioExamenAnteriormente() {
        return repitioExamenAnteriormente;
    }
    public void setRepitioExamenAnteriormente(Boolean repitioExamenAnteriormente) {
        this.repitioExamenAnteriormente = repitioExamenAnteriormente;
    }

    public Date getFechaExamenAnterior() {
        return fechaExamenAnterior;
    }
    public void setFechaExamenAnterior(Date fechaExamenAnterior) {
        this.fechaExamenAnterior = fechaExamenAnterior;
    }

    public String getResultadoExamenAnterior() {
        return resultadoExamenAnterior;
    }
    public void setResultadoExamenAnterior(String resultadoExamenAnterior) {
        this.resultadoExamenAnterior = resultadoExamenAnterior;
    }

    //Histopatología (solo casos)-------------------------------------------------------------------------------------------------------------|
    public Integer getTipoHistologico() {
        return tipoHistologico;
    }
    public void setTipoHistologico(Integer tipoHistologico) {
        this.tipoHistologico = tipoHistologico;
    }

    public String getOtroTipoHistologico() {
        return otroTipoHistologico;
    }
    public void setOtroTipoHistologico(String otroTipoHistologico) {
        this.otroTipoHistologico = otroTipoHistologico;
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



