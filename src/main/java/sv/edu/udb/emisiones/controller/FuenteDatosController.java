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

/**
 * Controlador REST responsable de gestionar las fuentes de datos utilizadas
 * en los análisis de emisiones de CO₂.
 *.
 * Proporciona endpoints para listar, crear y analizar las fuentes,
 * incluyendo filtrados por confiabilidad o por organismo responsable.
 *.
 * Forma parte de la capa de presentación (API pública) y delega
 * la lógica de negocio a la capa de servicio (FuenteDatosService).
 */
@RestController
@RequestMapping("/api/fuentes-datos")
@RequiredArgsConstructor
@Tag(
        name = "Gestión de Fuentes de Datos",
        description = "API para gestión de fuentes de datos de emisiones"
)
public class FuenteDatosController {

    /** Servicio que contiene la lógica de negocio asociada a las fuentes de datos.*/
    private final FuenteDatosService fuenteDatosService;

    /** Obtiene la lista completa de fuentes de datos registradas en el sistema. */
    @GetMapping
    @Operation(summary = "Obtener todas las fuentes de datos")
    public List<FuenteDatosResponse> findAll() {
        return fuenteDatosService.findAll();
    }

    /** Devuelve únicamente las fuentes marcadas como confiables. */
    @GetMapping("/confiables")
    @Operation(summary = "Obtener fuentes de datos confiables")
    public List<FuenteDatosResponse> findConfiable() {
        return fuenteDatosService.findByEsConfiableTrue();
    }

    /**Busca y devuelve las fuentes de datos asociadas a un organismo específico.*/
    @GetMapping("/organismo/{organismo}")
    @Operation(summary = "Obtener fuentes por organismo")
    public List<FuenteDatosResponse> findByOrganismo(@PathVariable String organismo) {
        return fuenteDatosService.findByOrganismo(organismo);
    }

    /**Crea un nuevo registro de fuente de datos.
     * Valida que los campos obligatorios estén presentes antes de guardar.*/
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear nueva fuente de datos")
    public FuenteDatosResponse save(@Valid @RequestBody FuenteDatosRequest request) {
        return fuenteDatosService.save(request);
    }

    /**Genera estadísticas del número de fuentes registradas agrupadas por organismo.*/
    @GetMapping("/estadisticas/organismo")
    @Operation(summary = "Estadísticas de fuentes por organismo")
    public Map<String, Long> countByOrganismo() {
        return fuenteDatosService.countByOrganismo();
    }
}
