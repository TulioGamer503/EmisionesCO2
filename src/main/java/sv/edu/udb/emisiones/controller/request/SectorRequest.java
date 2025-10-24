/**
 * Representa los datos que el cliente envía al API para crear o actualizar 
 * un sector económico en el sistema de emisiones de CO₂.
 *-
 * es utilizado principalmente por los endpoints POST y PUT del
 * SectorController, y permite validar que los campos enviados cumplan 
 * con las reglas de formato y longitud definidas.
 */

package sv.edu.udb.emisiones.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SectorRequest {

    /**
     * Nombre del sector económico.
     * Es un campo obligatorio y debe tener entre 2 y 100 caracteres.
     */
    @NotBlank(message = "El nombre del sector es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String nombre;

    /**
     * Descripción opcional del sector.
     * Puede incluir detalles sobre su alcance o características generales.
     * Se limita a un máximo de 500 caracteres para mantener la consistencia.
     */
    @Size(max = 500, message = "La descripción no puede exceder 500 caracteres")
    private String descripcion;
}
