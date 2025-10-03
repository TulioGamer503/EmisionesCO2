package sv.edu.udb.emisiones.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sv.edu.udb.emisiones.domain.Subsector;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubsectorRepository extends JpaRepository<Subsector, Long> {

    Optional<Subsector> findByNombre(String nombre);

    List<Subsector> findBySectorId(Long sectorId);

    @Query("SELECT s FROM Subsector s WHERE s.sector.id = :sectorId AND s.tipo = :tipo")
    List<Subsector> findBySectorIdAndTipo(@Param("sectorId") Long sectorId, @Param("tipo") String tipo);

    @Query("SELECT s FROM Subsector s JOIN FETCH s.sector")
    List<Subsector> findAllWithSector();

    @Query("SELECT COUNT(s) FROM Subsector s WHERE s.sector.id = :sectorId")
    Long countBySectorId(@Param("sectorId") Long sectorId);

    List<Subsector> findByIntensidad(String intensidad);
}