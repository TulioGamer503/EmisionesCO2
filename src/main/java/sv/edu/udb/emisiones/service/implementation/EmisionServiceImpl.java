/**
 * Servicio CRUD y análisis de emisiones (variación, totales y agregaciones).
 */
package sv.edu.udb.emisiones.service.implementation;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sv.edu.udb.emisiones.controller.request.EmisionRequest;
import sv.edu.udb.emisiones.controller.response.AnalisisVariacionResponse;
import sv.edu.udb.emisiones.controller.response.EmisionResponse;
import sv.edu.udb.emisiones.domain.Emision;
import sv.edu.udb.emisiones.domain.Sector;
import sv.edu.udb.emisiones.domain.Subsector;
import sv.edu.udb.emisiones.repository.EmisionRepository;
import sv.edu.udb.emisiones.repository.SectorRepository;
import sv.edu.udb.emisiones.repository.SubsectorRepository;
import sv.edu.udb.emisiones.service.EmisionService;
import sv.edu.udb.emisiones.service.mapper.EmisionMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmisionServiceImpl implements EmisionService {

    private final EmisionRepository emisionRepository;
    private final SectorRepository sectorRepository;
    private final SubsectorRepository subsectorRepository;
    private final EmisionMapper emisionMapper;

    /** Listar todas las emisiones (mapeadas a Response). */
    @Override
    @Transactional(readOnly = true)
    public List<EmisionResponse> findAll() {
        return emisionMapper.toResponseList(emisionRepository.findAll());
    }

    /** Buscar emisión por ID (404 si no existe). */
    @Override
    @Transactional(readOnly = true)
    public EmisionResponse findById(Long id) {
        Emision emision = emisionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Emisión no encontrada con id: " + id));
        return emisionMapper.toResponse(emision);
    }

    /** Crear nueva emisión (valida sector/subsector). */
    @Override
    @Transactional
    public EmisionResponse save(EmisionRequest request) {
        Sector sector = sectorRepository.findById(request.getSectorId())
                .orElseThrow(() -> new EntityNotFoundException("Sector no encontrado"));

        Subsector subsector = null;
        if (request.getSubsectorId() != null) {
            subsector = subsectorRepository.findById(request.getSubsectorId())
                    .orElseThrow(() -> new EntityNotFoundException("Subsector no encontrado"));
        }

        Emision emision = emisionMapper.toEntity(request);
        emision.setSector(sector);
        emision.setSubsector(subsector);

        Emision saved = emisionRepository.save(emision);
        return emisionMapper.toResponse(saved);
    }

    /** Actualizar emisión existente (valida sector/subsector). */
    @Override
    @Transactional
    public EmisionResponse update(Long id, EmisionRequest request) {
        Emision existing = emisionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Emisión no encontrada con id: " + id));

        Sector sector = sectorRepository.findById(request.getSectorId())
                .orElseThrow(() -> new EntityNotFoundException("Sector no encontrado"));

        Subsector subsector = null;
        if (request.getSubsectorId() != null) {
            subsector = subsectorRepository.findById(request.getSubsectorId())
                    .orElseThrow(() -> new EntityNotFoundException("Subsector no encontrado"));
        }

        existing.setAnio(request.getAnio());
        existing.setMes(request.getMes());
        existing.setCantidadTCO2(request.getCantidadTCO2());
        existing.setSector(sector);
        existing.setSubsector(subsector);
        existing.setFechaRegistro(request.getFechaRegistro());

        Emision updated = emisionRepository.save(existing);
        return emisionMapper.toResponse(updated);
    }

    /** Eliminar emisión por ID (404 si no existe). */
    @Override
    @Transactional
    public void delete(Long id) {
        if (!emisionRepository.existsById(id)) {
            throw new EntityNotFoundException("Emisión no encontrada con id: " + id);
        }
        emisionRepository.deleteById(id);
    }

    /** Listar emisiones por año. */
    @Override
    @Transactional(readOnly = true)
    public List<EmisionResponse> findByAnio(Integer anio) {
        return emisionMapper.toResponseList(emisionRepository.findByAnio(anio));
    }

    /** Listar emisiones por rango de años. */
    @Override
    @Transactional(readOnly = true)
    public List<EmisionResponse> findByRangoAnios(Integer inicio, Integer fin) {
        return emisionMapper.toResponseList(emisionRepository.findByRangoAnios(inicio, fin));
    }

    /** Análisis de variación total y por sector entre períodos. */
    @Override
    @Transactional(readOnly = true)
    public AnalisisVariacionResponse analizarVariacion(Integer anioBaseInicio, Integer anioBaseFin, Integer anioComparacion) {
        List<Emision> emisionesBase = emisionRepository.findByRangoAnios(anioBaseInicio, anioBaseFin);
        List<Emision> emisionesComparacion = emisionRepository.findByAnio(anioComparacion);

        Double totalBase = calcularTotalEmisiones(emisionesBase);
        Double totalComparacion = calcularTotalEmisiones(emisionesComparacion);

        return construirRespuestaAnalisis(anioBaseInicio, anioBaseFin, anioComparacion,
                totalBase, totalComparacion, emisionesBase, emisionesComparacion);
    }

    // ====== Auxiliares de agregación y análisis ======

    /** Suma TCO₂ de una lista de emisiones. */
    private Double calcularTotalEmisiones(List<Emision> emisiones) {
        return emisiones.stream()
                .mapToDouble(Emision::getCantidadTCO2)
                .sum();
    }

    /** Construye DTO con totales, variaciones, contribuciones y texto de tendencia. */
    private AnalisisVariacionResponse construirRespuestaAnalisis(Integer anioBaseInicio, Integer anioBaseFin,
                                                                 Integer anioComparacion, Double totalBase,
                                                                 Double totalComparacion, List<Emision> emisionesBase,
                                                                 List<Emision> emisionesComparacion) {
        Double variacionAbsoluta = totalComparacion - totalBase;
        Double variacionPorcentual = totalBase != 0 ? (variacionAbsoluta / totalBase) * 100 : 0;

        Map<String, Double> variacionPorSector = calcularVariacionPorSector(emisionesBase, emisionesComparacion);
        Map<String, Double> contribucionPorSector = calcularContribucionPorSector(emisionesComparacion, totalComparacion);

        String tendencia = determinarTendencia(variacionPorcentual);
        String interpretacion = generarInterpretacion(tendencia, variacionPorcentual);

        return AnalisisVariacionResponse.builder()
                .anioBaseInicio(anioBaseInicio)
                .anioBaseFin(anioBaseFin)
                .anioComparacion(anioComparacion)
                .totalEmisionesBase(totalBase)
                .totalEmisionesComparacion(totalComparacion)
                .variacionAbsoluta(variacionAbsoluta)
                .variacionPorcentual(variacionPorcentual)
                .variacionPorSector(variacionPorSector)
                .contribucionPorSector(contribucionPorSector)
                .tendencia(tendencia)
                .interpretacion(interpretacion)
                .build();
    }

    /** % variación por sector entre base y comparación. */
    private Map<String, Double> calcularVariacionPorSector(List<Emision> base, List<Emision> comparacion) {
        Map<String, Double> variacion = new HashMap<>();

        Map<String, Double> basePorSector = base.stream()
                .collect(Collectors.groupingBy(
                        e -> e.getSector().getNombre(),
                        Collectors.summingDouble(Emision::getCantidadTCO2)
                ));

        Map<String, Double> comparacionPorSector = comparacion.stream()
                .collect(Collectors.groupingBy(
                        e -> e.getSector().getNombre(),
                        Collectors.summingDouble(Emision::getCantidadTCO2)
                ));

        for (String sector : basePorSector.keySet()) {
            Double baseSector = basePorSector.get(sector);
            Double compSector = comparacionPorSector.getOrDefault(sector, 0.0);

            if (baseSector != 0) {
                Double variacionSector = ((compSector - baseSector) / baseSector) * 100;
                variacion.put(sector, variacionSector);
            }
        }

        return variacion;
    }

    /** % contribución de cada sector al total. */
    private Map<String, Double> calcularContribucionPorSector(List<Emision> emisiones, Double total) {
        if (total == 0) return new HashMap<>();

        return emisiones.stream()
                .collect(Collectors.groupingBy(
                        e -> e.getSector().getNombre(),
                        Collectors.collectingAndThen(
                                Collectors.summingDouble(Emision::getCantidadTCO2),
                                suma -> (suma / total) * 100
                        )
                ));
    }

    /** Categorización de tendencia por umbrales. */
    private String determinarTendencia(Double variacionPorcentual) {
        if (variacionPorcentual < -5) return "DECRECIENTE_FUERTE";
        if (variacionPorcentual < 0) return "DECRECIENTE_SUAVE";
        if (variacionPorcentual == 0) return "ESTABLE";
        if (variacionPorcentual <= 5) return "CRECIENTE_SUAVE";
        return "CRECIENTE_FUERTE";
    }

    /** Mensaje corto según tendencia. */
    private String generarInterpretacion(String tendencia, Double variacionPorcentual) {
        switch (tendencia) {
            case "DECRECIENTE_FUERTE":
                return String.format("Reducción significativa del %.2f%% en las emisiones", Math.abs(variacionPorcentual));
            case "DECRECIENTE_SUAVE":
                return String.format("Reducción moderada del %.2f%% en las emisiones", Math.abs(variacionPorcentual));
            case "ESTABLE":
                return "Las emisiones se mantienen estables";
            case "CRECIENTE_SUAVE":
                return String.format("Incremento moderado del %.2f%% en las emisiones", variacionPorcentual);
            case "CRECIENTE_FUERTE":
                return String.format("Incremento significativo del %.2f%% en las emisiones", variacionPorcentual);
            default:
                return "Tendencia no determinada";
        }
    }

    /** (Pendiente) Variación acotada a un sector. */
    @Override
    public AnalisisVariacionResponse analizarVariacionPorSector(Long sectorId, Integer anioBaseInicio, Integer anioBaseFin, Integer anioComparacion) {
        // Implementación específica por sector
        return null;
    }

    /** Totales por sector en un año. */
    @Override
    public Map<String, Double> obtenerTotalEmisionesPorSector(Integer anio) {
        List<Object[]> resultados = emisionRepository.findTotalEmisionesPorSector(anio);
        return resultados.stream()
                .collect(Collectors.toMap(
                        result -> (String) result[0],
                        result -> (Double) result[1]
                ));
    }

    /** Totales agregados por año. */
    @Override
    public Map<Integer, Double> obtenerTotalEmisionesPorAnio() {
        List<Object[]> resultados = emisionRepository.findTotalEmisionesPorAnio();
        return resultados.stream()
                .collect(Collectors.toMap(
                        result -> (Integer) result[0],
                        result -> (Double) result[1]
                ));
    }

    /** (Simplificado) Promedios mensuales por período. */
    @Override
    public Map<String, Double> obtenerEmisionesMensualesPromedio(Integer anioInicio, Integer anioFin) {
        // Implementación simplificada
        return new HashMap<>();
    }
}
