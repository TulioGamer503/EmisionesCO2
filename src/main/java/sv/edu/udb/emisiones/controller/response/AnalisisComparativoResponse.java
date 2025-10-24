/**
 * Propósito:
 * Representa la respuesta generada por el sistema al realizar un análisis 
 * comparativo entre las emisiones de CO₂ de El Salvador y los referentes 
 * globales (otros países o promedios internacionales).
 *-
 * Este DTO se utiliza para devolver al cliente los resultados del método 
 * compararConReferentesGlobales() en el AnalisisService.
 *-
 * Incluye información cuantitativa (variaciones, posición) y cualitativa 
 * (interpretación y fuentes).
 */

package sv.edu.udb.emisiones.controller.response;

import lombok.*;

import java.time.LocalDate;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnalisisComparativoResponse {

    /** Porcentaje de variación de emisiones correspondiente a El Salvador.*/
    private Double variacionElSalvador;

    /**Mapa con los referentes globales y su respectiva variación porcentual.*/
    private Map<String, Double> referentesGlobales;

    /**Indica la posición relativa de El Salvador frente a otros países o regiones.*/
    private String posicionRelativa;

    /**
     * Texto interpretativo que resume los hallazgos del análisis comparativo.
     * Proporciona una explicación cualitativa de los resultados numéricos.
     */
    private String interpretacionComparativa;

    /**
     * Fuente o fuentes de los datos utilizados en el análisis.
     * Puede incluir el nombre de organismos, bases de datos o reportes.
     */
    private String fuentes;

    /**
     * Fecha en la que se realizó o generó el análisis comparativo.
     * Permite registrar cuándo se llevó a cabo la comparación.
     */
    private LocalDate fechaComparacion;
}
