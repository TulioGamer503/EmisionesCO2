/**
 * Representa el cuerpo (payload) que el cliente envía al API para 
 * registrar o actualizar una emisión de CO₂.
 *-
 * contiene los datos mínimos requeridos para crear una nueva
 * entrada en la base de datos, como el año, mes, cantidad de emisiones,
 * sector y fecha de registro.
 *-
 * Se utiliza principalmente en los endpoints POST y PUT del 
 * EmisionController, y es validado automáticamente mediante las 
 * anotaciones de Jakarta Validation (@NotNull, @Positive, etc.).
 */

package sv.edu.udb.emisiones.controller.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmisionRequest {

    /**
     * Año al que pertenece el registro de emisiones.
     * Es obligatorio y se usa para agrupar las emisiones por periodos
     */
    @NotNull(message = "El año es obligatorio")
    private Integer anio;

    /**
     * Mes correspondiente al registro de emisiones.
     * Representa el mes del año en formato numérico
     */
    @NotNull(message = "El mes es obligatorio")
    private Integer mes;

    /**
     * Cantidad total de emisiones de CO₂ en toneladas (tCO₂).
     * Debe ser positiva y refleja la magnitud de las emisiones
     */
    @NotNull(message = "La cantidad de tCO2 es obligatoria")
    @Positive(message = "La cantidad debe ser positiva")
    private Double cantidadTCO2;

    /**
     * Identificador del sector económico asociado a la emisión.
     * Es obligatorio, ya que toda emisión debe estar vinculada a un sector
     */
    @NotNull(message = "El sector es obligatorio")
    private Long sectorId;

    /**
     * Identificador del subsector asociado a la emisión (opcional).
     * Solo se utiliza si el sector cuenta con divisiones más específicas,
     * como “Transporte terrestre” dentro del sector “Transporte”.
     */
    private Long subsectorId;

    /**
     * Fecha exacta en la que se registró el dato de emisión.
     * Se formatea automáticamente como "yyyy-MM-dd" al serializar o 
     * deserializar en JSON, gracias a la anotación @JsonFormat.
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaRegistro;
}
