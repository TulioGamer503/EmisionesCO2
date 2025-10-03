package sv.edu.udb.emisiones.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sv.edu.udb.emisiones.domain.Emision;

import java.util.List;

@Repository
public interface EmisionRepository extends JpaRepository<Emision, Long> {

    List<Emision> findByAnio(Integer anio);

    List<Emision> findByAnioAndSectorId(Integer anio, Long sectorId);

    @Query("SELECT e FROM Emision e WHERE e.anio BETWEEN :anioInicio AND :anioFin")
    List<Emision> findByRangoAnios(@Param("anioInicio") Integer anioInicio,
                                   @Param("anioFin") Integer anioFin);

    @Query("SELECT e FROM Emision e WHERE e.anio BETWEEN :anioInicio AND :anioFin AND e.sector.id = :sectorId")
    List<Emision> findByRangoAniosAndSector(@Param("anioInicio") Integer anioInicio,
                                            @Param("anioFin") Integer anioFin,
                                            @Param("sectorId") Long sectorId);

    @Query("SELECT e.sector.nombre, SUM(e.cantidadTCO2) FROM Emision e WHERE e.anio = :anio GROUP BY e.sector.nombre")
    List<Object[]> findTotalEmisionesPorSector(@Param("anio") Integer anio);

    @Query("SELECT e.anio, SUM(e.cantidadTCO2) FROM Emision e GROUP BY e.anio ORDER BY e.anio")
    List<Object[]> findTotalEmisionesPorAnio();

    @Query("SELECT AVG(e.cantidadTCO2) FROM Emision e WHERE e.anio BETWEEN :inicio AND :fin")
    Double findPromedioEmisionesPorPeriodo(@Param("inicio") Integer inicio,
                                           @Param("fin") Integer fin);

    @Query("SELECT e.anio, e.mes, SUM(e.cantidadTCO2) FROM Emision e WHERE e.anio BETWEEN :inicio AND :fin GROUP BY e.anio, e.mes ORDER BY e.anio, e.mes")
    List<Object[]> findEmisionesMensualesPorPeriodo(@Param("inicio") Integer inicio,
                                                    @Param("fin") Integer fin);
}