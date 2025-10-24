package sv.edu.udb.emisiones.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sv.edu.udb.emisiones.controller.request.SectorRequest;
import sv.edu.udb.emisiones.controller.response.SectorResponse;
import sv.edu.udb.emisiones.service.SectorService;

import java.util.List;
import java.util.Map;

/**
 * Controlador REST para la gestión de sectores económicos.
 * Expone endpoints para CRUD, consultas con subsectores y estadísticas.
 * Delegamos toda la lógica de negocio en SectorService.
 */
@RestController
@RequestMapping("/api/sectores")
@RequiredArgsConstructor
@Tag(name = "Gestión de Sectores", description = "API para gestión de sectores económicos")
public class SectorController {

    /**Servicio que contiene la lógica de negocio para sectores*/
    private final SectorService sectorService;

    /** Obtiene el listado completo de sectores. */
    @GetMapping
    @Operation(summary = "Obtener todos los sectores")
    public List<SectorResponse> findAll() {
        return sectorService.findAll();
    }

    /**Obtiene un sector por su identificador.*/
    @GetMapping("/{id}")
    @Operation(summary = "Obtener sector por ID")
    public SectorResponse findById(@PathVariable Long id) {
        return sectorService.findById(id);
    }

    /**Busca un sector por su nombre (coincidencia exacta según la implementación del service).*/
    @GetMapping("/nombre/{nombre}")
    @Operation(summary = "Obtener sector por nombre")
    public SectorResponse findByNombre(@PathVariable String nombre) {
        return sectorService.findByNombre(nombre);
    }

    /**
     * Crea un nuevo sector.
     * Valida el payload con @Valid antes de delegar al service.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear nuevo sector")
    public SectorResponse save(@Valid @RequestBody SectorRequest request) {
        return sectorService.save(request);
    }

    /**
     * Actualiza un sector existente.
     * @param id id del sector a actualizar
     * @param request datos a actualizar
     */
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar sector existente")
    public SectorResponse update(@PathVariable Long id, @Valid @RequestBody SectorRequest request) {
        return sectorService.update(id, request);
    }

    /**
     * Elimina un sector por su ID.
     * Responde 204 No Content en caso de éxito.
     * @param id id del sector a eliminar
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Eliminar sector")
    public void delete(@PathVariable Long id) {
        sectorService.delete(id);
    }

    /**
     * Devuelve todos los sectores incluyendo sus subsectores asociados.
     * Útil para vistas maestras o pantallas de detalle.
     */
    @GetMapping("/con-subsectores")
    @Operation(summary = "Obtener sectores con sus subsectores")
    public List<SectorResponse> findAllWithSubsectores() {
        return sectorService.findAllWithSubsectores();
    }

    /**Devuelve un mapa con el conteo de subsectores por cada sector.*/
    @GetMapping("/estadisticas/subsectores")
    @Operation(summary = "Obtener estadísticas de subsectores por sector")
    public Map<String, Long> contarSubsectoresPorSector() {
        return sectorService.contarSubsectoresPorSector();
    }

    /**
     * Agrega un subsector a un sector específico.
     * Recibe los datos mínimos del subsector por query params.
     * @param sectorId id del sector al que se agregará el subsector
     * @param nombre nombre del subsector
     * @param tipo tipo o categoría del subsector
     * @param intensidad intensidad
     */
    @PostMapping("/{sectorId}/subsectores")
    @Operation(summary = "Agregar subsector a un sector")
    public SectorResponse agregarSubsector(
            @PathVariable Long sectorId,
            @RequestParam String nombre,
            @RequestParam String tipo,
            @RequestParam String intensidad) {
        return sectorService.agregarSubsectorASector(sectorId, nombre, tipo, intensidad);
    }
}
