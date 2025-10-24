/**
 * Define operaciones de análisis de emisiones y proyecciones.
 */
package sv.edu.udb.emisiones.service;

import sv.edu.udb.emisiones.controller.response.*;

import java.util.Map;

public interface AnalisisService {

    /** Analiza la variación de emisiones durante la pandemia. */
    AnalisisVariacionResponse analizarVariacionPandemia();

    /** Compara variaciones locales con referentes globales y regionales. */
    AnalisisComparativoResponse compararConReferentesGlobales();

    /** Proyecta emisiones futuras según tendencia lineal. */
    ProyeccionResponse proyectarEmisiones(Integer aniosFuturo);

    /** Calcula indicadores según la identidad de Kaya. */
    Map<String, Object> calcularIndicadoresKaya(Integer anio);

    /** Verifica la consistencia y calidad de los datos. */
    ReporteConsistenciaResponse verificarConsistenciaDatos();
}
