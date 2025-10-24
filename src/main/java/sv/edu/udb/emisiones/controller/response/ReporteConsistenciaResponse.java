/**
 * Representa el resultado del proceso de verificación de consistencia de los datos 
 * de emisiones de CO₂ en el sistema.
 *.
 * Se utiliza como respuesta del método verificarConsistenciaDatos()
 * del AnalisisService, y resume los hallazgos del análisis de calidad de los datos, 
 * incluyendo validaciones, problemas encontrados y nivel de confianza de la información.
 */

package sv.edu.udb.emisiones.controller.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReporteConsistenciaResponse {

    /**
     * Fecha y hora exactas en que se realizó la verificación de consistencia.
     * Permite registrar cuándo fue ejecutado el análisis.
     */
    private LocalDateTime fechaVerificacion; // Ya existe

    /**Indica si los datos analizados fueron considerados consistentes o no.*/
    private Boolean datosConsistentes;

    /**Lista de verificaciones o pruebas realizadas durante el proceso.*/
    private List<String> verificacionesRealizadas;

    /**Lista de problemas o anomalías encontradas durante la validación de datos.*/
    private List<String> problemasIdentificados;

    /**Recomendaciones derivadas de los hallazgos.*/
    private String recomendaciones;

    /**Nivel de confianza asignado a los datos luego del análisis.*/
    private String nivelConfianza;
}
