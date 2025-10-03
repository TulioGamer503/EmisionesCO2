package sv.edu.udb.emisiones.service;

import sv.edu.udb.emisiones.controller.response.*;

import java.util.Map;

public interface AnalisisService {
    AnalisisVariacionResponse analizarVariacionPandemia();
    AnalisisComparativoResponse compararConReferentesGlobales();
    ProyeccionResponse proyectarEmisiones(Integer aniosFuturo);
    Map<String, Object> calcularIndicadoresKaya(Integer anio);
    ReporteConsistenciaResponse verificarConsistenciaDatos();
}