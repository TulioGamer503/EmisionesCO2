/**
 * Representa la respuesta generada por el sistema al realizar un análisis 
 * de variación de emisiones de CO₂ entre un periodo base (por ejemplo, 2015–2019)
 * y un año de comparación (por ejemplo, 2020 o 2021).
 *e utiliza en los métodos analizarVariacion() y
 * analizarVariacionPorSector() del EmisionService, devolviendo tanto resultados 
 * numéricos como interpretativos.
 * ,
 * Contiene datos de emisiones totales, variaciones porcentuales, contribución
 * por sector y detalles de tendencia e interpretación.
 */

package sv.edu.udb.emisiones.controller.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnalisisVariacionResponse {

    /**Año inicial del periodo base utilizado para el análisis.*/
    private Integer anioBaseInicio;

    /**Año final del periodo base utilizado para el análisis.*/
    private Integer anioBaseFin;

    /**Año que se usa para comparar contra el periodo base.*/
    private Integer anioComparacion;

    /**Total de emisiones acumuladas en el periodo base (tCO₂).*/
    private Double totalEmisionesBase;

    /**Total de emisiones correspondientes al año de comparación (tCO₂).*/
    private Double totalEmisionesComparacion;

    /**
     * Diferencia absoluta entre las emisiones del año comparado 
     * y las del periodo base (en toneladas de CO₂).
     */
    private Double variacionAbsoluta;

    /** Variación porcentual entre el periodo base y el año de comparación.*/
    private Double variacionPorcentual;

    /**Mapa que indica el cambio porcentual por sector económico.*/
    private Map<String, Double> variacionPorSector;

    /**Mapa con la contribución porcentual de cada sector al total de emisiones.*/
    private Map<String, Double> contribucionPorSector;

    /**
     * Clasificación de la tendencia general detectada en el análisis.
     * Posibles valores: "CRECIENTE_FUERTE", "CRECIENTE_SUAVE", 
     * "DECRECIENTE_SUAVE", "DECRECIENTE_FUERTE", "ESTABLE".
     */
    private String tendencia;

    /**
     * Texto interpretativo que explica los resultados de la variación.
     * Resume si el cambio observado es positivo o negativo, y su magnitud.
     */
    private String interpretacion;

    /**
     * Metodología utilizada para el cálculo de las emisiones o variaciones.
     * Por defecto se usa "IPCC 2023", según los lineamientos internacionales.
     */
    @Builder.Default
    private String metodologia = "IPCC 2023";

    /**
     * Recomendaciones derivadas del análisis (opcional).
     * Puede incluir sugerencias para reducir emisiones o mejorar políticas.
     */
    private String recomendaciones;

    /**
     * Fecha y hora en la que se generó este análisis de variación.
     * Útil para auditoría o trazabilidad de los resultados.
     */
    private LocalDateTime fechaGeneracion;
}
