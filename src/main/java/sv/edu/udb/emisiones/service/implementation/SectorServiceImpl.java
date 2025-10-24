/**
 * Servicio CRUD para sectores y gestión de subsectores.
 */
package sv.edu.udb.emisiones.service.implementation;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sv.edu.udb.emisiones.controller.request.SectorRequest;
import sv.edu.udb.emisiones.controller.response.SectorResponse;
import sv.edu.udb.emisiones.domain.Sector;
import sv.edu.udb.emisiones.domain.Subsector;
import sv.edu.udb.emisiones.repository.SectorRepository;
import sv.edu.udb.emisiones.repository.SubsectorRepository;
import sv.edu.udb.emisiones.service.SectorService;
import sv.edu.udb.emisiones.service.mapper.SectorMapper;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SectorServiceImpl implements SectorService {

    private final SectorRepository sectorRepository;
    private final SubsectorRepository subsectorRepository;
    private final SectorMapper sectorMapper;

    /** Listar todos los sectores. */
    @Override
    @Transactional(readOnly = true)
    public List<SectorResponse> findAll() {
        return sectorMapper.toResponseList(sectorRepository.findAll());
    }

    /** Buscar sector por ID. */
    @Override
    @Transactional(readOnly = true)
    public SectorResponse findById(Long id) {
        Sector sector = sectorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Sector no encontrado con id: " + id));
        return sectorMapper.toResponse(sector);
    }

    /** Buscar sector por nombre. */
    @Override
    @Transactional(readOnly = true)
    public SectorResponse findByNombre(String nombre) {
        Sector sector = sectorRepository.findByNombre(nombre)
                .orElseThrow(() -> new EntityNotFoundException("Sector no encontrado: " + nombre));
        return sectorMapper.toResponse(sector);
    }

    /** Crear nuevo sector (valida duplicados). */
    @Override
    @Transactional
    public SectorResponse save(SectorRequest request) {
        if (sectorRepository.findByNombre(request.getNombre()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un sector con el nombre: " + request.getNombre());
        }

        Sector sector = sectorMapper.toEntity(request);
        Sector saved = sectorRepository.save(sector);
        return sectorMapper.toResponse(saved);
    }

    /** Actualizar sector existente (valida duplicados). */
    @Override
    @Transactional
    public SectorResponse update(Long id, SectorRequest request) {
        Sector existing = sectorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Sector no encontrado con id: " + id));

        if (!existing.getNombre().equals(request.getNombre()) &&
                sectorRepository.findByNombre(request.getNombre()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un sector con el nombre: " + request.getNombre());
        }

        existing.setNombre(request.getNombre());
        existing.setDescripcion(request.getDescripcion());

        Sector updated = sectorRepository.save(existing);
        return sectorMapper.toResponse(updated);
    }

    /** Eliminar sector (valida dependencias de emisiones). */
    @Override
    @Transactional
    public void delete(Long id) {
        if (!sectorRepository.existsById(id)) {
            throw new EntityNotFoundException("Sector no encontrado con id: " + id);
        }

        Sector sector = sectorRepository.findById(id).get();
        if (!sector.getEmisiones().isEmpty()) {
            throw new IllegalStateException("No se puede eliminar el sector porque tiene emisiones asociadas");
        }

        sectorRepository.deleteById(id);
    }

    /** Listar sectores con sus subsectores. */
    @Override
    @Transactional(readOnly = true)
    public List<SectorResponse> findAllWithSubsectores() {
        List<Sector> sectores = sectorRepository.findAllWithSubsectores();
        return sectorMapper.toResponseList(sectores);
    }

    /** Contar subsectores asociados a cada sector. */
    @Override
    @Transactional(readOnly = true)
    public Map<String, Long> contarSubsectoresPorSector() {
        List<Sector> sectores = sectorRepository.findAllWithSubsectores();
        return sectores.stream()
                .collect(Collectors.toMap(
                        Sector::getNombre,
                        sector -> (long) sector.getSubsectores().size()
                ));
    }

    /** Agregar un nuevo subsector a un sector existente. */
    @Override
    @Transactional
    public SectorResponse agregarSubsectorASector(Long sectorId, String nombreSubsector, String tipo, String intensidad) {
        Sector sector = sectorRepository.findById(sectorId)
                .orElseThrow(() -> new EntityNotFoundException("Sector no encontrado con id: " + sectorId));

        boolean existeSubsector = sector.getSubsectores().stream()
                .anyMatch(subs -> subs.getNombre().equalsIgnoreCase(nombreSubsector));

        if (existeSubsector) {
            throw new IllegalArgumentException("Ya existe un subsector con el nombre: " + nombreSubsector + " en este sector");
        }

        Subsector subsector = Subsector.builder()
                .nombre(nombreSubsector)
                .tipo(tipo)
                .intensidad(intensidad)
                .sector(sector)
                .build();

        sector.getSubsectores().add(subsector);
        Sector updated = sectorRepository.save(sector);

        return sectorMapper.toResponse(updated);
    }
}
