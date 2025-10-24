/**
 * Define operaciones CRUD y gestión de sectores y subsectores.
 */
package sv.edu.udb.emisiones.service;

import sv.edu.udb.emisiones.controller.request.SectorRequest;
import sv.edu.udb.emisiones.controller.response.SectorResponse;

import java.util.List;
import java.util.Map;

public interface SectorService {

    /** Listar todos los sectores. */
    List<SectorResponse> findAll();

    /** Buscar sector por ID. */
    SectorResponse findById(Long id);

    /** Buscar sector por nombre. */
    SectorResponse findByNombre(String nombre);

    /** Crear nuevo sector. */
    SectorResponse save(SectorRequest request);

    /** Actualizar un sector existente. */
    SectorResponse update(Long id, SectorRequest request);

    /** Eliminar sector por ID. */
    void delete(Long id);

    /** Listar sectores con sus subsectores asociados. */
    List<SectorResponse> findAllWithSubsectores();

    /** Contar subsectores por cada sector. */
    Map<String, Long> contarSubsectoresPorSector();

    /** Agregar un subsector a un sector existente. */
    SectorResponse agregarSubsectorASector(Long sectorId, String nombreSubsector, String tipo, String intensidad);
}
