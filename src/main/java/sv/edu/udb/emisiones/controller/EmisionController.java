package sv.edu.udb.emisiones.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sv.edu.udb.emisiones.controller.request.EmisionRequest;
import sv.edu.udb.emisiones.controller.response.AnalisisVariacionResponse;
import sv.edu.udb.emisiones.controller.response.EmisionResponse;
import sv.edu.udb.emisiones.service.EmisionService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/emisiones")
@RequiredArgsConstructor
@Tag(name = "Gestión de Emisiones", description = "CRUD y análisis de datos de emisiones de CO₂")
public class EmisionController {

    private final EmisionService emisionService;

    // ----- CRUD -----

    @GetMapping
    @Operation(summary = "Listar todas las emisiones")
    public List<EmisionResponse> findAll() {
        return emisionService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener emisión por ID")
    public EmisionResponse findById(@PathVariable Long id) {
        return emisionService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear nueva emisión")
    public EmisionResponse save(@Valid @RequestBody EmisionRequest request) {
        return emisionService.save(request);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar emisión por ID")
    public EmisionResponse update(@PathVariable Long id, @Valid @RequestBody EmisionRequest request) {
        return emisionService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Eliminar emisión por ID")
    public void delete(@PathVariable Long id) {
        emisionService.delete(id);
    }

    // ----- Consultas por tiempo -----

    @GetMapping("/anio/{anio}")
    @Operation(summary = "Listar emisiones por año")
    public List<EmisionResponse> findByAnio(@PathVariable Integer anio) {
        return emisionService.findByAnio(anio);
    }

    @GetMapping("/rango")
    @Operation(summary = "Listar emisiones por rango de años")
    public List<EmisionResponse> findByRangoAnios(
            @RequestParam Integer inicio,
            @RequestParam Integer fin
    ) {
        return emisionService.findByRangoAnios(inicio, fin);
    }

    // ----- Análisis de variación -----

    @GetMapping("/variacion")
    @Operation(summary = "Analizar variación entre periodos (base vs comparación)")
    public AnalisisVariacionResponse analizarVariacion(
            @RequestParam Integer anioBaseInicio,
            @RequestParam Integer anioBaseFin,
            @RequestParam Integer anioComparacion
    ) {
        return emisionService.analizarVariacion(anioBaseInicio, anioBaseFin, anioComparacion);
    }

    @GetMapping("/variacion/sector/{sectorId}")
    @Operation(summary = "Analizar variación por sector (base vs comparación)")
    public AnalisisVariacionResponse analizarVariacionPorSector(
            @PathVariable Long sectorId,
            @RequestParam Integer anioBaseInicio,
            @RequestParam Integer anioBaseFin,
            @RequestParam Integer anioComparacion
    ) {
        return emisionService.analizarVariacionPorSector(sectorId, anioBaseInicio, anioBaseFin, anioComparacion);
    }

    // ----- Estadísticas / agregaciones -----

    @GetMapping("/estadisticas/por-sector")
    @Operation(summary = "Total de emisiones por sector para un año")
    public Map<String, Double> obtenerTotalEmisionesPorSector(@RequestParam Integer anio) {
        return emisionService.obtenerTotalEmisionesPorSector(anio);
    }

    @GetMapping("/estadisticas/por-anio")
    @Operation(summary = "Total de emisiones por año (serie completa)")
    public Map<Integer, Double> obtenerTotalEmisionesPorAnio() {
        return emisionService.obtenerTotalEmisionesPorAnio();
    }

    @GetMapping("/estadisticas/mensual-promedio")
    @Operation(summary = "Emisiones mensuales promedio entre dos años")
    public Map<String, Double> obtenerEmisionesMensualesPromedio(
            @RequestParam Integer anioInicio,
            @RequestParam Integer anioFin
    ) {
        return emisionService.obtenerEmisionesMensualesPromedio(anioInicio, anioFin);
    }
}
