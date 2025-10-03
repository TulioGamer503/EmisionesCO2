package sv.edu.udb.emisiones.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sv.edu.udb.emisiones.controller.request.FuenteDatosRequest;
import sv.edu.udb.emisiones.controller.response.FuenteDatosResponse;
import sv.edu.udb.emisiones.service.FuenteDatosService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/fuentes-datos")
@RequiredArgsConstructor
@Tag(name = "Gestión de Fuentes de Datos", description = "API para gestión de fuentes de datos de emisiones")
public class FuenteDatosController {

    private final FuenteDatosService fuenteDatosService;

    @GetMapping
    @Operation(summary = "Obtener todas las fuentes de datos")
    public List<FuenteDatosResponse> findAll() {
        return fuenteDatosService.findAll();
    }

    @GetMapping("/confiables")
    @Operation(summary = "Obtener fuentes de datos confiables")
    public List<FuenteDatosResponse> findConfiable() {
        return fuenteDatosService.findByEsConfiableTrue();
    }

    @GetMapping("/organismo/{organismo}")
    @Operation(summary = "Obtener fuentes por organismo")
    public List<FuenteDatosResponse> findByOrganismo(@PathVariable String organismo) {
        return fuenteDatosService.findByOrganismo(organismo);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear nueva fuente de datos")
    public FuenteDatosResponse save(@Valid @RequestBody FuenteDatosRequest request) {
        return fuenteDatosService.save(request);
    }

    @GetMapping("/estadisticas/organismo")
    @Operation(summary = "Estadísticas de fuentes por organismo")
    public Map<String, Long> countByOrganismo() {
        return fuenteDatosService.countByOrganismo();
    }
}