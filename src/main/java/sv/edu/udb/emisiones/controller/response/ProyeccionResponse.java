/**
 * Representa la respuesta generada por el sistema al realizar una proyección 
 * de emisiones de CO₂ para los próximos años, a partir de un año base.
 *-
 * se utiliza en el método proyectarEmisiones() del AnalisisService
 * y devuelve los resultados estimados bajo distintos escenarios (lineal, 
 * optimista y pesimista), junto con las recomendaciones y supuestos empleados.
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
public class ProyeccionResponse {

    /**Año base a partir del cual se realiza la proyección de emisiones.*/
    private Integer anioBase;

    /**Número total de años que abarca la proyección hacia el futuro.*/
    private Integer aniosProyeccion;

    /**Resultados de la proyección bajo un modelo lineal.*/
    private Map<String, Double> proyeccionLineal;

    /**Resultados del escenario optimista (reducción de emisiones).*/
    private Map<String, Double> escenarioOptimista;

    /**Resultados del escenario pesimista (aumento de emisiones).*/
    private Map<String, Double> escenarioPesimista;

    /**Supuestos o condiciones consideradas para generar las proyecciones.*/
    private String supuestos;

    /**Recomendaciones de mitigación derivadas del análisis de escenarios.*/
    private String recomendacionesMitigacion;

    /**Fecha en la que se generó la proyección.*/
    private LocalDate fechaProyeccion;
}
