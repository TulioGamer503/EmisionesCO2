/**
 * Repositorio para gestionar los sectores económicos.
 */
package sv.edu.udb.emisiones.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sv.edu.udb.emisiones.domain.Sector;

import java.util.List;
import java.util.Optional;

@Repository
public interface SectorRepository extends JpaRepository<Sector, Long> {

    /** Buscar sector por nombre. */
    Optional<Sector> findByNombre(String nombre);

    /** Listar todos los sectores con sus subsectores. */
    @Query("SELECT s FROM Sector s LEFT JOIN FETCH s.subsectores")
    List<Sector> findAllWithSubsectores();

    /** Buscar sectores por una lista de nombres. */
    @Query("SELECT s FROM Sector s WHERE s.nombre IN :nombres")
    List<Sector> findByNombres(List<String> nombres);
}
