/**
 * Repositorio para consultar y gestionar registros de emisiones.
 */
package sv.edu.udb.emisiones.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sv.edu.udb.emisiones.domain.Emision;

import java.util.List;

@Repository
public interface EmisionRepository extends JpaRepository<Emision, Long> {

    /** Buscar emisiones por año. */
    List<Emision> findByAnio(Integer anio);

    /** Buscar emisiones por año y sector. */
    List<Emision> findByAnioAndSectorId(Integer anio, Long sectorId);

    /** Buscar emisiones en un rango de años. */
    @Query("SELECT e FROM Emision e WHERE e.anio BETWEEN :anioInicio AND :anioFin")
    List<Emision> findByRangoAnios(@Param("anioInicio") Integer anioInicio,
                                   @Param("anioFin") Integer anioFin);

    /** Buscar emisiones por rango de años y sector. */
    @Query("SELECT e FROM Emision e WHERE e.anio BETWEEN :anioInicio AND :anioFin AND e.sector.id = :sectorId")
    List<Emision> findByRangoAniosAndSector(@Param("anioInicio") Integer anioInicio,
                                            @Param("anioFin") Integer anioFin,
                                            @Param("sectorId") Long sectorId);

    /** Total de emisiones por sector en un año. */
    @Query("SELECT e.sector.nombre, SUM(e.cantidadTCO2) FROM Emision e WHERE e.anio = :anio GROUP BY e.sector.nombre")
    List<Object[]> findTotalEmisionesPorSector(@Param("anio") Integer anio);

    /** Total de emisiones agrupadas por año. */
    @Query("SELECT e.anio, SUM(e.cantidadTCO2) FROM Emision e GROUP BY e.anio ORDER BY e.anio")
    List<Object[]> findTotalEmisionesPorAnio();

    /** Promedio de emisiones en un rango de años. */
    @Query("SELECT AVG(e.cantidadTCO2) FROM Emision e WHERE e.anio BETWEEN :inicio AND :fin")
    Double findPromedioEmisionesPorPeriodo(@Param("inicio") Integer inicio,
                                           @Param("fin") Integer fin);

    /** Emisiones mensuales por año en un rango. */
    @Query("SELECT e.anio, e.mes, SUM(e.cantidadTCO2) FROM Emision e WHERE e.anio BETWEEN :inicio AND :fin GROUP BY e.anio, e.mes ORDER BY e.anio, e.mes")
    List<Object[]> findEmisionesMensualesPorPeriodo(@Param("inicio") Integer inicio,
                                                    @Param("fin") Integer fin);
}
