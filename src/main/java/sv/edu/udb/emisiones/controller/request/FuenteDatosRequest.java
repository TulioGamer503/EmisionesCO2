/**
 * Representa la información que el cliente envía al API para registrar 
 * o actualizar una fuente de datos relacionada con las emisiones de CO₂.
 *,
 * Este DTO se utiliza principalmente en los endpoints POST y PUT del 
 * controlador FuenteDatosController, y permite gestionar metadatos sobre 
 * las fuentes de información utilizadas en los análisis (por ejemplo, 
 * nombre, organismo, URL, metodología y confiabilidad).
 */

package sv.edu.udb.emisiones.controller.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FuenteDatosRequest {

    /**
     * Nombre de la fuente de datos.
     * Es un campo obligatorio y debe contener texto no vacío.
     * Representa el título o denominación principal de la fuente,
     * como un informe, base de datos o sistema de monitoreo.
     */
    @NotBlank(message = "El nombre de la fuente es obligatorio")
    private String nombre;

    /**
     * Nombre del organismo o institución que publica la fuente de datos.
     * Campo opcional, útil para identificar la procedencia de la información.
     * Ejemplo: "Ministerio de Medio Ambiente", "Banco Mundial", etc.
     */
    private String organismo;

    /**
     * URL o enlace web donde se puede consultar la fuente.
     * Campo opcional que permite referenciar la ubicación pública 
     * del documento o base de datos original.
     */
    private String url;

    /**
     * Descripción breve de la metodología utilizada para recopilar o calcular 
     * los datos de emisiones dentro de esta fuente.
     * Ayuda a evaluar la validez científica o técnica de la información.
     */
    private String metodologia;

    /**
     * Indica si la fuente es considerada confiable.
     * Por defecto su valor es TRUE. Se puede usar este campo para filtrar 
     * fuentes en los análisis o reportes del sistema.
     */
    @Builder.Default
    private Boolean esConfiable = true;
}
