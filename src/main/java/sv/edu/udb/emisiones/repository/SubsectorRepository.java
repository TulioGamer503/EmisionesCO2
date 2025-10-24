/**
 * Repositorio para gestionar los subsectores económicos.
 */
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

    /** Buscar subsector por nombre. */
    Optional<Subsector> findByNombre(String nombre);

    /** Buscar subsectores por ID de sector. */
    List<Subsector> findBySectorId(Long sectorId);

    /** Buscar subsectores por sector y tipo. */
    @Query("SELECT s FROM Subsector s WHERE s.sector.id = :sectorId AND s.tipo = :tipo")
    List<Subsector> findBySectorIdAndTipo(@Param("sectorId") Long sectorId, @Param("tipo") String tipo);

    /** Listar todos los subsectores con su sector asociado. */
    @Query("SELECT s FROM Subsector s JOIN FETCH s.sector")
    List<Subsector> findAllWithSector();

    /** Contar cuántos subsectores pertenecen a un sector. */
    @Query("SELECT COUNT(s) FROM Subsector s WHERE s.sector.id = :sectorId")
    Long countBySectorId(@Param("sectorId") Long sectorId);

    /** Buscar subsectores por intensidad de emisiones. */
    List<Subsector> findByIntensidad(String intensidad);
}
