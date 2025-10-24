/**
 * Define operaciones CRUD y análisis sobre emisiones de CO₂.
 */
package sv.edu.udb.emisiones.service;

import sv.edu.udb.emisiones.controller.request.EmisionRequest;
import sv.edu.udb.emisiones.controller.response.AnalisisVariacionResponse;
import sv.edu.udb.emisiones.controller.response.EmisionResponse;

import java.util.List;
import java.util.Map;

public interface EmisionService {

    /** Listar todas las emisiones. */
    List<EmisionResponse> findAll();

    /** Buscar emisión por ID. */
    EmisionResponse findById(Long id);

    /** Crear nueva emisión. */
    EmisionResponse save(EmisionRequest request);

    /** Actualizar una emisión existente. */
    EmisionResponse update(Long id, EmisionRequest request);

    /** Eliminar emisión por ID. */
    void delete(Long id);

    /** Buscar emisiones por año. */
    List<EmisionResponse> findByAnio(Integer anio);

    /** Buscar emisiones dentro de un rango de años. */
    List<EmisionResponse> findByRangoAnios(Integer inicio, Integer fin);

    /** Analizar variación total de emisiones entre períodos. */
    AnalisisVariacionResponse analizarVariacion(Integer anioBaseInicio, Integer anioBaseFin, Integer anioComparacion);

    /** Analizar variación de emisiones por sector. */
    AnalisisVariacionResponse analizarVariacionPorSector(Long sectorId, Integer anioBaseInicio, Integer anioBaseFin, Integer anioComparacion);

    /** Obtener total de emisiones agrupadas por sector. */
    Map<String, Double> obtenerTotalEmisionesPorSector(Integer anio);

    /** Obtener total de emisiones agrupadas por año. */
    Map<Integer, Double> obtenerTotalEmisionesPorAnio();

    /** Obtener promedio mensual de emisiones en un período. */
    Map<String, Double> obtenerEmisionesMensualesPromedio(Integer anioInicio, Integer anioFin);
}
