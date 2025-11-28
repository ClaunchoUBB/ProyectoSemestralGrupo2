package choripan_solutions.db_exporter.Servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import choripan_solutions.db_exporter.Modelo.Participante;
import choripan_solutions.db_exporter.Modelo.Usuario;
import choripan_solutions.db_exporter.Repositorio.ParticipanteRepo;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ParticipanteServicio {

    @Autowired
    private ParticipanteRepo participanteRepositorio;

    @Autowired
    private UsuarioServicio usuarioServicio;

    // Lista para almacenar los datos dicotomizados
    private List<Integer> datosDicotomizados = new ArrayList<>();

    // ======================
    // Métodos CRUD
    // ======================

    public List<Participante> findAllParticipantes() {
        return participanteRepositorio.findAll();
    }

    @SuppressWarnings("null")
    public Optional<Participante> findByCodigo(String codigo) {
        
        if (!participanteRepositorio.existsById(codigo)) {
            throw new RuntimeException("Participante no encontrado con código: " + codigo);
        }else {
            return participanteRepositorio.findById(codigo);
        }
    }

    public Participante crearParticipante(Participante participante) {
        if (participante.getGrupo() == null) {
            throw new IllegalArgumentException("El grupo (0 = control, 1 = caso) es obligatorio.");
        }
    
        // 1. Obtener el RUT del usuario autenticado desde el contexto de seguridad
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new SecurityException("No hay un usuario autenticado para realizar esta operación.");
        }
        String rutString = authentication.getName();

        // Comprobación de nulidad para el nombre de usuario (RUT)
        if (rutString == null || rutString.isBlank()) {
            throw new SecurityException("El token de autenticación no contiene un nombre de usuario (RUT) válido.");
        }

        Integer rutUsuario = Integer.parseInt(rutString);
    
        // 2. Buscar el objeto Usuario completo
        Usuario usuarioActual = usuarioServicio.obtenerUsuarioPorRut(rutUsuario)
                .orElseThrow(() -> new RuntimeException("Error: Usuario autenticado no encontrado en la base de datos."));
    
        // 3. Asignar el usuario al participante
        participante.setUsuario(usuarioActual);
    
        // Determinar prefijo según el grupo
        String prefijo = participante.getGrupo() == 1 ? "K" : "C";
    
        // Contar cuántos hay en ese grupo
        long count = participanteRepositorio.countByGrupo(participante.getGrupo());
    
        // Generar el código (p.ej. C-001)
        // Usamos countByGrupo para asegurar que el correlativo sea por grupo
        long correlativo = participanteRepositorio.countByGrupo(participante.getGrupo()) + 1;
        String codigo = String.format("%s-%03d", prefijo, correlativo);
        participante.setCodigo(codigo);
    
        // 4. Establecer valores por defecto
        participante.setFechaInclusion(new Date());
        participante.setEstado(true); // Activo por defecto
        participante.setIMC(); // Calcular IMC si peso y estatura están presentes
    
        // 5. Guardar el participante
        return participanteRepositorio.save(participante);
    }

    @SuppressWarnings("null")
    public Participante actualizarParticipante(String codigo, Participante participanteActualizado) {
        if(!participanteRepositorio.existsById(codigo)) {
            throw new RuntimeException("Participante no encontrado con código: " + codigo);
        }
        return participanteRepositorio.findById(codigo)
                .map(p -> {
                    participanteActualizado.setCodigo(p.getCodigo());
                    return participanteRepositorio.save(participanteActualizado);
                })
                .orElseThrow(() -> new RuntimeException("Participante no encontrado con código: " + codigo));
    }

    @SuppressWarnings("null")
    public void eliminarParticipante(String codigo) {
        if (!participanteRepositorio.existsById(codigo)) {
            throw new RuntimeException("Participante no encontrado con código: " + codigo);
        }
        participanteRepositorio.deleteById(codigo);
    }

    // PATCH: Actualización parcial mediante objeto JSON
    @SuppressWarnings("null")
    public Participante actualizarParticipanteParcial(String codigo, Participante participanteDetalles) {
        Participante participante = participanteRepositorio.findById(codigo)
                .orElseThrow(() -> new RuntimeException("No se encontró el participante con código: " + codigo));
    
        // Actualización manual y segura de los campos permitidos
        if (participanteDetalles.getNombre1() != null) participante.setNombre1(participanteDetalles.getNombre1());
        if (participanteDetalles.getNombre2() != null) participante.setNombre2(participanteDetalles.getNombre2());
        if (participanteDetalles.getApellido1() != null) participante.setApellido1(participanteDetalles.getApellido1());
        if (participanteDetalles.getApellido2() != null) participante.setApellido2(participanteDetalles.getApellido2());
        if (participanteDetalles.getCorreo() != null) participante.setCorreo(participanteDetalles.getCorreo());
        if (participanteDetalles.getNumero() != null) participante.setNumero(participanteDetalles.getNumero());
        if (participanteDetalles.getEstado() != null) participante.setEstado(participanteDetalles.getEstado());
        if (participanteDetalles.getGrupo() != null) participante.setGrupo(participanteDetalles.getGrupo());
        
        // Datos sociodemográficos
        if (participanteDetalles.getEdad() != null) participante.setEdad(participanteDetalles.getEdad());
        if (participanteDetalles.getSexo() != null) participante.setSexo(participanteDetalles.getSexo());
        if (participanteDetalles.getNacionalidad() != null) participante.setNacionalidad(participanteDetalles.getNacionalidad());
        if (participanteDetalles.getDireccion() != null) participante.setDireccion(participanteDetalles.getDireccion());
        if (participanteDetalles.getComuna() != null) participante.setComuna(participanteDetalles.getComuna());
        if (participanteDetalles.getCiudad() != null) participante.setCiudad(participanteDetalles.getCiudad());
        if (participanteDetalles.getZona() != null) participante.setZona(participanteDetalles.getZona());
        if (participanteDetalles.getViveHace5annos() != null) participante.setViveHace5annos(participanteDetalles.getViveHace5annos());
        if (participanteDetalles.getNivelEducacional() != null) participante.setNivelEducacional(participanteDetalles.getNivelEducacional());
        if (participanteDetalles.getOcupacionActual() != null) participante.setOcupacionActual(participanteDetalles.getOcupacionActual());
        if (participanteDetalles.getPrevisionSalud() != null) participante.setPrevisionSalud(participanteDetalles.getPrevisionSalud());
        if (participanteDetalles.getOtraPrevision() != null) participante.setOtraPrevision(participanteDetalles.getOtraPrevision());

        // Antecedentes clínicos
        if (participanteDetalles.getDiagnosticoHistologicoAdenocarcinomaGastrico() != null) participante.setDiagnosticoHistologicoAdenocarcinomaGastrico(participanteDetalles.getDiagnosticoHistologicoAdenocarcinomaGastrico());
        if (participanteDetalles.getFechaDiagnostico() != null) participante.setFechaDiagnostico(participanteDetalles.getFechaDiagnostico());
        if (participanteDetalles.getAntCancerGastrico() != null) participante.setAntCancerGastrico(participanteDetalles.getAntCancerGastrico());
        if (participanteDetalles.getAntCancerOtro() != null) participante.setAntCancerOtro(participanteDetalles.getAntCancerOtro());
        if (participanteDetalles.getCancerOtro() != null) participante.setCancerOtro(participanteDetalles.getCancerOtro());
        if (participanteDetalles.getOtrasEnfermedades() != null) participante.setOtrasEnfermedades(participanteDetalles.getOtrasEnfermedades());
        if (participanteDetalles.getUsoCronicoMedicamentosGastrolesivos() != null) participante.setUsoCronicoMedicamentosGastrolesivos(participanteDetalles.getUsoCronicoMedicamentosGastrolesivos());
        if (participanteDetalles.getMedicamentosGastroLesivos() != null) participante.setMedicamentosGastroLesivos(participanteDetalles.getMedicamentosGastroLesivos());
        if (participanteDetalles.getCirugiaGastricaPrevia() != null) participante.setCirugiaGastricaPrevia(participanteDetalles.getCirugiaGastricaPrevia());

        // Variables antropomórficas y recalcular IMC
        boolean imcNeedsRecalculation = false;
        if (participanteDetalles.getPeso() != null) {
            participante.setPeso(participanteDetalles.getPeso());
            imcNeedsRecalculation = true;
        }
        if (participanteDetalles.getEstatura() != null) {
            participante.setEstatura(participanteDetalles.getEstatura());
            imcNeedsRecalculation = true;
        }
        if (imcNeedsRecalculation) {
            participante.setIMC();
        }

        // Tabaquismo
        if (participanteDetalles.getNuncaFumo() != null) participante.setNuncaFumo(participanteDetalles.getNuncaFumo());
        if (participanteDetalles.getExFumador() != null) participante.setExFumador(participanteDetalles.getExFumador());
        if (participanteDetalles.getFumadorActual() != null) participante.setFumadorActual(participanteDetalles.getFumadorActual());
        if (participanteDetalles.getPromedioFumaDiario() != null) participante.setPromedioFumaDiario(participanteDetalles.getPromedioFumaDiario());
        if (participanteDetalles.getTiempoTotalFumador() != null) participante.setTiempoTotalFumador(participanteDetalles.getTiempoTotalFumador());
        if (participanteDetalles.getExAnnosSinFumar() != null) participante.setExAnnosSinFumar(participanteDetalles.getExAnnosSinFumar());

        // Consumo de alcohol
        if (participanteDetalles.getEstadoConsumoAlcohol() != null) participante.setEstadoConsumoAlcohol(participanteDetalles.getEstadoConsumoAlcohol());
        if (participanteDetalles.getFrecuenciaConsumoAlcohol() != null) participante.setFrecuenciaConsumoAlcohol(participanteDetalles.getFrecuenciaConsumoAlcohol());
        if (participanteDetalles.getCantidadXOcasion() != null) participante.setCantidadXOcasion(participanteDetalles.getCantidadXOcasion());
        if (participanteDetalles.getAnnosConsumo() != null) participante.setAnnosConsumo(participanteDetalles.getAnnosConsumo());
        if (participanteDetalles.getExAnnosSinBeber() != null) participante.setExAnnosSinBeber(participanteDetalles.getExAnnosSinBeber());

        // Factores dieta
        if (participanteDetalles.getCarnesProcesadas() != null) participante.setCarnesProcesadas(participanteDetalles.getCarnesProcesadas());
        if (participanteDetalles.getAlimentosSalados() != null) participante.setAlimentosSalados(participanteDetalles.getAlimentosSalados());
        if (participanteDetalles.getFrutasVerduras() != null) participante.setFrutasVerduras(participanteDetalles.getFrutasVerduras());
        if (participanteDetalles.getFrituras() != null) participante.setFrituras(participanteDetalles.getFrituras());
        if (participanteDetalles.getConsumoAlimentosMuyCondimentados() != null) participante.setConsumoAlimentosMuyCondimentados(participanteDetalles.getConsumoAlimentosMuyCondimentados());
        if (participanteDetalles.getBebidaCaliente() != null) participante.setBebidaCaliente(participanteDetalles.getBebidaCaliente());

        // Exposiciones
        if (participanteDetalles.getPesticidas() != null) participante.setPesticidas(participanteDetalles.getPesticidas());
        if (participanteDetalles.getOtrosChemicos() != null) participante.setOtrosChemicos(participanteDetalles.getOtrosChemicos());
        if (participanteDetalles.getTipoChemicos() != null) participante.setTipoChemicos(participanteDetalles.getTipoChemicos());
        if (participanteDetalles.getHumoLenna() != null) participante.setHumoLenna(participanteDetalles.getHumoLenna());
        if (participanteDetalles.getFuentePrincipalAgua() != null) participante.setFuentePrincipalAgua(participanteDetalles.getFuentePrincipalAgua());
        if (participanteDetalles.getOtraFuenteAgua() != null) participante.setOtraFuenteAgua(participanteDetalles.getOtraFuenteAgua());
        if (participanteDetalles.getTratamientoAgua() != null) participante.setTratamientoAgua(participanteDetalles.getTratamientoAgua());

        // Helicobacter pylori
        if (participanteDetalles.getResultadoHel() != null) participante.setResultadoHel(participanteDetalles.getResultadoHel());
        if (participanteDetalles.getResultPositivHelPasado() != null) participante.setResultPositivHelPasado(participanteDetalles.getResultPositivHelPasado());
        if (participanteDetalles.getAnnoAproxExamenPasadoHel() != null) participante.setAnnoAproxExamenPasadoHel(participanteDetalles.getAnnoAproxExamenPasadoHel());
        if (participanteDetalles.getTipoExamenPasadoHel() != null) participante.setTipoExamenPasadoHel(participanteDetalles.getTipoExamenPasadoHel());
        if (participanteDetalles.getRecibioTratamientoErradHel() != null) participante.setRecibioTratamientoErradHel(participanteDetalles.getRecibioTratamientoErradHel());
        if (participanteDetalles.getAnnoTratamientoHel() != null) participante.setAnnoTratamientoHel(participanteDetalles.getAnnoTratamientoHel());
        if (participanteDetalles.getEsquemaTratamientoHel() != null) participante.setEsquemaTratamientoHel(participanteDetalles.getEsquemaTratamientoHel());
        if (participanteDetalles.getTipoTestHel() != null) participante.setTipoTestHel(participanteDetalles.getTipoTestHel());
        if (participanteDetalles.getOtroTestHel() != null) participante.setOtroTestHel(participanteDetalles.getOtroTestHel());
        if (participanteDetalles.getTiempoTest() != null) participante.setTiempoTest(participanteDetalles.getTiempoTest());
        if (participanteDetalles.getUsoIbp() != null) participante.setUsoIbp(participanteDetalles.getUsoIbp());
        if (participanteDetalles.getRepitioExamenAnteriormente() != null) participante.setRepitioExamenAnteriormente(participanteDetalles.getRepitioExamenAnteriormente());
        if (participanteDetalles.getFechaExamenAnterior() != null) participante.setFechaExamenAnterior(participanteDetalles.getFechaExamenAnterior());
        if (participanteDetalles.getResultadoExamenAnterior() != null) participante.setResultadoExamenAnterior(participanteDetalles.getResultadoExamenAnterior());

        // Histopatología
        if (participanteDetalles.getTipoHistologico() != null) participante.setTipoHistologico(participanteDetalles.getTipoHistologico());
        if (participanteDetalles.getOtroTipoHistologico() != null) participante.setOtroTipoHistologico(participanteDetalles.getOtroTipoHistologico());
        if (participanteDetalles.getTumorUbicacion() != null) participante.setTumorUbicacion(participanteDetalles.getTumorUbicacion());
        if (participanteDetalles.getEstadioClinico() != null) participante.setEstadioClinico(participanteDetalles.getEstadioClinico());

        return participanteRepositorio.save(participante);
    }

    // ======================
    // Dicotomización de datos
    // ======================

    /**
     * Dicotomiza un conjunto de valores según un umbral.
     * Si el valor >= umbral → 1, si es menor → 0.
     * Guarda los resultados en una lista interna.
     *
     * @param valores Lista de valores numéricos.
     * @param umbral  Valor de referencia para dicotomizar.
     * @return Lista de valores dicotomizados (0 o 1).
     */
    public List<Integer> dicotomizarDatos(List<Float> valores, float umbral) {
        datosDicotomizados = valores.stream()
                .map(v -> v >= umbral ? 1 : 0)
                .collect(Collectors.toList());
        return datosDicotomizados;
    }

    /**
     * Devuelve la lista actual de datos dicotomizados.
     */
    public List<Integer> obtenerDatosDicotomizados() {
        return datosDicotomizados;
    }

    /**
     * Limpia la lista de datos dicotomizados.
     */
    public void limpiarDatosDicotomizados() {
        datosDicotomizados.clear();
    }
}
