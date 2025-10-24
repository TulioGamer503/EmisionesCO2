/**
 * Repositorio de análisis de emisiones de CO₂.
 *.
 * Esta interfaz proporciona consultas personalizadas para realizar análisis estadísticos,
 * tendencias, estacionalidad, correlaciones y proyecciones sobre los datos de emisiones.
 *.
 * Extiende de JpaRepository, lo que permite acceder a operaciones CRUD básicas,
 * además de consultas especializadas definidas mediante anotaciones @Query.
 */
package sv.edu.udb.emisiones.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sv.edu.udb.emisiones.domain.Emision;

import java.util.List;

@Repository
public interface AnalisisRepository extends JpaRepository<Emision, Long> {

    /**
     * Obtiene la tendencia de emisiones por sector a lo largo de un rango de años.
     * Agrupa los resultados por año y sector, sumando las emisiones totales por cada uno.
     */
    @Query("SELECT e.anio, e.sector.nombre, SUM(e.cantidadTCO2) " +
            "FROM Emision e " +
            "WHERE e.anio BETWEEN :inicio AND :fin " +
            "GROUP BY e.anio, e.sector.nombre " +
            "ORDER BY e.anio, e.sector.nombre")
    List<Object[]> findTendenciasPorSector(@Param("inicio") Integer inicio, @Param("fin") Integer fin);

    /**
     * Analiza la estacionalidad mensual promedio de las emisiones.
     * Calcula el promedio de emisiones (TCO₂) para cada mes dentro del rango de años indicado.
     */
    @Query("SELECT e.mes, AVG(e.cantidadTCO2) " +
            "FROM Emision e " +
            "WHERE e.anio BETWEEN :inicio AND :fin " +
            "GROUP BY e.mes " +
            "ORDER BY e.mes")
    List<Object[]> findEstacionalidadMensual(@Param("inicio") Integer inicio, @Param("fin") Integer fin);

    /**
     * Calcula métricas estadísticas (promedio, mínimo, máximo y desviación estándar)
     * de emisiones por sector para un año determinado.
     */
    @Query("SELECT e.sector.nombre, " +
            "AVG(e.cantidadTCO2), " +
            "MIN(e.cantidadTCO2), " +
            "MAX(e.cantidadTCO2), " +
            "STDDEV(e.cantidadTCO2) " +
            "FROM Emision e " +
            "WHERE e.anio = :anio " +
            "GROUP BY e.sector.nombre")
    List<Object[]> findMetricasEstadisticasPorSector(@Param("anio") Integer anio);

    /**
     * Evalúa la correlación entre sectores para identificar relaciones entre sus emisiones.
     * Compara pares de sectores (sin repetición) en los mismos períodos (año y mes),
     * calculando el coeficiente de correlación estadística entre sus emisiones.

     */
    @Query("SELECT e1.sector.nombre, e2.sector.nombre, " +
            "CORR(e1.cantidadTCO2, e2.cantidadTCO2) " +
            "FROM Emision e1, Emision e2 " +
            "WHERE e1.anio = e2.anio AND e1.mes = e2.mes " +
            "AND e1.sector.id < e2.sector.id " +
            "GROUP BY e1.sector.nombre, e2.sector.nombre")
    List<Object[]> findCorrelacionEntreSectores();

    /**
     * Obtiene los datos agregados por año para realizar una proyección lineal de tendencia.
     * Usa una consulta SQL nativa que suma las emisiones por año dentro del rango especificado.
     */
    @Query(value = "SELECT anio, SUM(cantidad_tco2) as total " +
            "FROM emisiones " +
            "WHERE anio BETWEEN :inicio AND :fin " +
            "GROUP BY anio " +
            "ORDER BY anio", nativeQuery = true)
    List<Object[]> findDatosParaProyeccion(@Param("inicio") Integer inicio, @Param("fin") Integer fin);
}
