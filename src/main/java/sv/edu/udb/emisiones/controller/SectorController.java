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

@RestController
@RequestMapping("/api/sectores")
@RequiredArgsConstructor
@Tag(name = "Gestión de Sectores", description = "API para gestión de sectores económicos")
public class SectorController {

    private final SectorService sectorService;

    @GetMapping
    @Operation(summary = "Obtener todos los sectores")
    public List<SectorResponse> findAll() {
        return sectorService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener sector por ID")
    public SectorResponse findById(@PathVariable Long id) {
        return sectorService.findById(id);
    }

    @GetMapping("/nombre/{nombre}")
    @Operation(summary = "Obtener sector por nombre")
    public SectorResponse findByNombre(@PathVariable String nombre) {
        return sectorService.findByNombre(nombre);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear nuevo sector")
    public SectorResponse save(@Valid @RequestBody SectorRequest request) {
        return sectorService.save(request);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar sector existente")
    public SectorResponse update(@PathVariable Long id, @Valid @RequestBody SectorRequest request) {
        return sectorService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Eliminar sector")
    public void delete(@PathVariable Long id) {
        sectorService.delete(id);
    }

    @GetMapping("/con-subsectores")
    @Operation(summary = "Obtener sectores con sus subsectores")
    public List<SectorResponse> findAllWithSubsectores() {
        return sectorService.findAllWithSubsectores();
    }

    @GetMapping("/estadisticas/subsectores")
    @Operation(summary = "Obtener estadísticas de subsectores por sector")
    public Map<String, Long> contarSubsectoresPorSector() {
        return sectorService.contarSubsectoresPorSector();
    }

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