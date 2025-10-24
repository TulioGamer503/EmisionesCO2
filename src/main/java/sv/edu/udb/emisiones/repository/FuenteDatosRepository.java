/**
 * Repositorio para gestionar las fuentes de datos.
 */
package sv.edu.udb.emisiones.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sv.edu.udb.emisiones.domain.FuenteDatos;

import java.util.List;
import java.util.Optional;

@Repository
public interface FuenteDatosRepository extends JpaRepository<FuenteDatos, Long> {

    /** Buscar una fuente por nombre. */
    Optional<FuenteDatos> findByNombre(String nombre);

    /** Buscar fuentes por organismo. */
    List<FuenteDatos> findByOrganismo(String organismo);

    /** Listar solo las fuentes marcadas como confiables. */
    List<FuenteDatos> findByEsConfiableTrue();

    /** Buscar fuentes por parte del texto de la metodología. */
    @Query("SELECT f FROM FuenteDatos f WHERE f.metodologia LIKE %:metodologia%")
    List<FuenteDatos> findByMetodologiaContaining(String metodologia);

    /** Contar cuántas fuentes hay por organismo. */
    @Query("SELECT f.organismo, COUNT(f) FROM FuenteDatos f GROUP BY f.organismo")
    List<Object[]> countByOrganismo();
}
