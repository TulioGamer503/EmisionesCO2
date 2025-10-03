package sv.edu.udb.emisiones.service;

import sv.edu.udb.emisiones.controller.request.EmisionRequest;
import sv.edu.udb.emisiones.controller.response.AnalisisVariacionResponse;
import sv.edu.udb.emisiones.controller.response.EmisionResponse;

import java.util.List;
import java.util.Map;

public interface EmisionService {
    List<EmisionResponse> findAll();
    EmisionResponse findById(Long id);
    EmisionResponse save(EmisionRequest request);
    EmisionResponse update(Long id, EmisionRequest request);
    void delete(Long id);
    List<EmisionResponse> findByAnio(Integer anio);
    List<EmisionResponse> findByRangoAnios(Integer inicio, Integer fin);
    AnalisisVariacionResponse analizarVariacion(Integer anioBaseInicio, Integer anioBaseFin, Integer anioComparacion);
    AnalisisVariacionResponse analizarVariacionPorSector(Long sectorId, Integer anioBaseInicio, Integer anioBaseFin, Integer anioComparacion);
    Map<String, Double> obtenerTotalEmisionesPorSector(Integer anio);
    Map<Integer, Double> obtenerTotalEmisionesPorAnio();
    Map<String, Double> obtenerEmisionesMensualesPromedio(Integer anioInicio, Integer anioFin);
}