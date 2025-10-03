package sv.edu.udb.emisiones.service;

import sv.edu.udb.emisiones.controller.request.SectorRequest;
import sv.edu.udb.emisiones.controller.response.SectorResponse;

import java.util.List;
import java.util.Map;

public interface SectorService {
    List<SectorResponse> findAll();
    SectorResponse findById(Long id);
    SectorResponse findByNombre(String nombre);
    SectorResponse save(SectorRequest request);
    SectorResponse update(Long id, SectorRequest request);
    void delete(Long id);
    List<SectorResponse> findAllWithSubsectores();
    Map<String, Long> contarSubsectoresPorSector();
    SectorResponse agregarSubsectorASector(Long sectorId, String nombreSubsector, String tipo, String intensidad);
}