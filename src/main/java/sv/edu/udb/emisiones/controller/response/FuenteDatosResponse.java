/**
 * Representa la respuesta enviada por la API al consultar o registrar 
 * una fuente de datos relacionada con las emisiones de CO₂.
 *.
 * se utiliza para devolver información legible sobre las fuentes
 * de información almacenadas en el sistema, evitando exponer directamente 
 * las entidades del dominio.
 */

package sv.edu.udb.emisiones.controller.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FuenteDatosResponse {

    /**
     * Identificador único de la fuente de datos.
     * Se genera automáticamente al guardarla en la base de datos.
     */
    private Long id;

    /**Nombre o título de la fuente de datos.*/
    private String nombre;

    /**Institución, organismo o entidad que publica o respalda la fuente.*/
    private String organismo;

    /** Enlace web o URL donde se puede consultar la fuente de información.*/
    private String url;

    /**
     * Descripción breve de la metodología utilizada para recopilar o procesar los datos.
     * Permite evaluar la calidad o la base científica de la fuente.
     */
    private String metodologia;

    /**
     * Indica si la fuente es considerada confiable.
     * Valor booleano (TRUE/FALSE) usado en los análisis o filtros de reportes.
     */
    private Boolean esConfiable;
}
