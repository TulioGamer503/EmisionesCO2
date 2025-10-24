/**
 * Define la estructura estándar de las respuestas de error enviadas por la API.
 *-
 * Esta clase es utilizada por el GlobalExceptionHandler para devolver 
 * información consistente y detallada cuando ocurre una excepción
 *-
 * Permite que los clientes de la API comprendan el tipo de error, el momento
 * en que ocurrió y los detalles adicionales asociados.
 */

package sv.edu.udb.emisiones.controller.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL) // Excluye los campos nulos en la respuesta JSON
public class ErrorResponse {

    /** Fecha y hora exactas en las que ocurrió el error.*/
    private LocalDateTime timestamp;

    /** Código de estado HTTP correspondiente al error (por ejemplo: 400, 404, 500).*/
    private Integer status;

    /**Descripción breve del tipo de error.*/
    private String error;

    /**Mensaje explicativo del error, normalmente más detallado o contextual.*/
    private String message;

    /**
     * Ruta o endpoint donde se produjo el error.
     * Ayuda a identificar qué parte del API generó la excepción.
     */
    private String path;

    /**
     * Mapa opcional con detalles adicionales del error.
     * Se usa especialmente en errores de validación para listar
     */
    private Map<String, String> details;
}
