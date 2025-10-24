package sv.edu.udb.emisiones.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import sv.edu.udb.emisiones.controller.response.*;
import sv.edu.udb.emisiones.service.AnalisisService;

import java.util.Map;

@RestController
@RequestMapping("/api/analisis")
@RequiredArgsConstructor
@Tag(
        name = "Análisis de Emisiones",
        description = "Realiza análisis avanzados de emisiones de CO₂: variación, comparativos, proyecciones y consistencia de datos"
)
public class AnalisisController {

    private final AnalisisService analisisService;

    @GetMapping("/variacion-pandemia")
    @Operation(summary = "Analizar variación de emisiones durante la pandemia")
    public AnalisisVariacionResponse analizarVariacionPandemia() {
        return analisisService.analizarVariacionPandemia();
    }

    @GetMapping("/comparativo-global")
    @Operation(summary = "Comparar emisiones con referentes globales")
    public AnalisisComparativoResponse compararConReferentesGlobales() {
        return analisisService.compararConReferentesGlobales();
    }

    @GetMapping("/proyeccion")
    @Operation(summary = "Proyectar emisiones futuras")
    public ProyeccionResponse proyectarEmisiones(
            @RequestParam(defaultValue = "10") Integer aniosFuturo) {
        return analisisService.proyectarEmisiones(aniosFuturo);
    }

    @GetMapping("/indicadores-kaya")
    @Operation(summary = "Calcular indicadores de la identidad de Kaya para un año específico")
    public Map<String, Object> calcularIndicadoresKaya(
            @RequestParam Integer anio) {
        return analisisService.calcularIndicadoresKaya(anio);
    }

    @GetMapping("/consistencia")
    @Operation(summary = "Verificar consistencia y calidad de los datos de emisiones")
    public ReporteConsistenciaResponse verificarConsistenciaDatos() {
        return analisisService.verificarConsistenciaDatos();
    }
}
