/**
 * Esta clase centraliza el manejo de excepciones para toda la aplicación.
 * Se encarga de capturar errores comunes (entidad no encontrada, validaciones,
 * argumentos inválidos, errores genéricos, etc.) y devolver una respuesta
 * uniforme al cliente con detalles estructurados en formato JSON.
 * .
 * intercepta las excepciones
 * lanzadas desde cualquier controlador REST y construye una respuesta
 * personalizada de tipo ErrorResponse.
 */

package sv.edu.udb.emisiones.configuration;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import sv.edu.udb.emisiones.controller.response.ErrorResponse;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Maneja excepciones del tipo EntityNotFoundException.
     *
     * Este método se activa cuando no se encuentra un recurso en la base de datos
     * (por ejemplo, un ID inexistente). Devuelve una respuesta con estado HTTP 404
     * y un objeto ErrorResponse con información del error.
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFound(EntityNotFoundException ex) {
        ErrorResponse error = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())                   // Fecha y hora del error
                .status(HttpStatus.NOT_FOUND.value())             // Código HTTP 404
                .error("Recurso no encontrado")                   // Tipo de error
                .message(ex.getMessage())                         // Mensaje detallado
                .path("/api/")                                    // Ruta genérica del API
                .build();
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    /**
     * Maneja excepciones del tipo IllegalArgumentException.
     *
     * Se utiliza cuando un método recibe argumentos no válidos o inconsistentes.
     * Devuelve una respuesta con estado HTTP 400 (BAD_REQUEST) y un mensaje claro
     * para el cliente.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex) {
        ErrorResponse error = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())                   // Fecha del error
                .status(HttpStatus.BAD_REQUEST.value())           // Código HTTP 400
                .error("Solicitud inválida")                      // Tipo de error
                .message(ex.getMessage())                         // Detalle del error
                .path("/api/")                                    // Ruta genérica
                .build();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * Maneja excepciones del tipo MethodArgumentNotValidException.
     *
     * Este error ocurre cuando las validaciones de los DTOs con @Valid fallan
     * (por ejemplo, campos nulos, valores fuera de rango, etc.).
     * Se construye un mapa de errores campo -> mensaje, que se incluye en la respuesta.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        // Recorre todos los errores de validación y los guarda en el mapa
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();     // Nombre del campo con error
            String errorMessage = error.getDefaultMessage();        // Mensaje de validación
            errors.put(fieldName, errorMessage);                    // Se agrega al mapa
        });

        // Se crea el objeto de respuesta con detalles de los campos inválidos
        ErrorResponse error = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Error de validación")
                .message("Datos de entrada inválidos")
                .details(errors)                                    // Incluye los errores específicos
                .path("/api/")
                .build();

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * Maneja cualquier otra excepción no controlada de manera específica.
     *
     * Este método sirve como “última línea de defensa” ante errores inesperados.
     * Devuelve un mensaje genérico con estado HTTP 500 (INTERNAL_SERVER_ERROR).
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        ErrorResponse error = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())                       // Momento del error
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())     // Código HTTP 500
                .error("Error interno del servidor")                  // Tipo de error
                .message("Ocurrió un error inesperado")               // Mensaje genérico
                .path("/api/")                                        // Ruta genérica
                .build();
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
