package sv.edu.udb.emisiones.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sv.edu.udb.emisiones.domain.Emision;

import java.util.List;

@Repository
public interface AnalisisRepository extends JpaRepository<Emision, Long> {

    // Análisis de tendencias por sector
    @Query("SELECT e.anio, e.sector.nombre, SUM(e.cantidadTCO2) " +
            "FROM Emision e " +
            "WHERE e.anio BETWEEN :inicio AND :fin " +
            "GROUP BY e.anio, e.sector.nombre " +
            "ORDER BY e.anio, e.sector.nombre")
    List<Object[]> findTendenciasPorSector(@Param("inicio") Integer inicio, @Param("fin") Integer fin);

    // Análisis de estacionalidad mensual
    @Query("SELECT e.mes, AVG(e.cantidadTCO2) " +
            "FROM Emision e " +
            "WHERE e.anio BETWEEN :inicio AND :fin " +
            "GROUP BY e.mes " +
            "ORDER BY e.mes")
    List<Object[]> findEstacionalidadMensual(@Param("inicio") Integer inicio, @Param("fin") Integer fin);

    // Cálculo de métricas estadísticas por sector
    @Query("SELECT e.sector.nombre, " +
            "AVG(e.cantidadTCO2), " +
            "MIN(e.cantidadTCO2), " +
            "MAX(e.cantidadTCO2), " +
            "STDDEV(e.cantidadTCO2) " +
            "FROM Emision e " +
            "WHERE e.anio = :anio " +
            "GROUP BY e.sector.nombre")
    List<Object[]> findMetricasEstadisticasPorSector(@Param("anio") Integer anio);

    // Análisis de correlación entre sectores
    @Query("SELECT e1.sector.nombre, e2.sector.nombre, " +
            "CORR(e1.cantidadTCO2, e2.cantidadTCO2) " +
            "FROM Emision e1, Emision e2 " +
            "WHERE e1.anio = e2.anio AND e1.mes = e2.mes " +
            "AND e1.sector.id < e2.sector.id " +
            "GROUP BY e1.sector.nombre, e2.sector.nombre")
    List<Object[]> findCorrelacionEntreSectores();

    // Proyección basada en tendencia lineal
    @Query(value = "SELECT anio, SUM(cantidad_tco2) as total " +
            "FROM emisiones " +
            "WHERE anio BETWEEN :inicio AND :fin " +
            "GROUP BY anio " +
            "ORDER BY anio", nativeQuery = true)
    List<Object[]> findDatosParaProyeccion(@Param("inicio") Integer inicio, @Param("fin") Integer fin);
}