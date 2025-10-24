/**
 * Representa los parámetros de entrada para realizar diferentes tipos
 * de análisis de emisiones de CO₂.
 *-
 * se utiliza para enviar desde el cliente los filtros o
 * condiciones del análisis, tales como los años de estudio, país,
 * sector o el número de años proyectados hacia el futuro.
 */

package sv.edu.udb.emisiones.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data                       // Genera getters, setters, toString, equals y hashCode
@Builder                    // Permite construir objetos con patrón Builder
@NoArgsConstructor           // Constructor vacío requerido por frameworks como Jackson
@AllArgsConstructor          // Constructor con todos los argumentos
@Schema(description = "Parámetros para realizar análisis y proyecciones de emisiones de CO₂")
public class AnalisisRequest {

    @NotNull(message = "El año base inicial no puede ser nulo")
    @Min(value = 1900, message = "El año base inicial debe ser mayor o igual a 1900")
    @Schema(description = "Año inicial del periodo base", example = "2015")
    private Integer anioBaseInicio;

    @NotNull(message = "El año base final no puede ser nulo")
    @Min(value = 1900, message = "El año base final debe ser mayor o igual a 1900")
    @Schema(description = "Año final del periodo base", example = "2019")
    private Integer anioBaseFin;

    @NotNull(message = "El año de comparación no puede ser nulo")
    @Min(value = 1900, message = "El año de comparación debe ser mayor o igual a 1900")
    @Schema(description = "Año de comparación con respecto al periodo base", example = "2020")
    private Integer anioComparacion;

    @Schema(description = "Número de años a proyectar hacia el futuro", example = "10")
    @Min(value = 1, message = "Debe proyectarse al menos un año")
    private Integer aniosFuturo;

    @Schema(description = "Identificador del sector a analizar (opcional)", example = "3")
    private Long sectorId;

    @Schema(description = "País o región a filtrar (opcional)", example = "El Salvador")
    private String pais;
}
