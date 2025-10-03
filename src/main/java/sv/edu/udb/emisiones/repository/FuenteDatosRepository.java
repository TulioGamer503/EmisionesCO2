package sv.edu.udb.emisiones.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sv.edu.udb.emisiones.domain.FuenteDatos;

import java.util.List;
import java.util.Optional;

@Repository
public interface FuenteDatosRepository extends JpaRepository<FuenteDatos, Long> {

    Optional<FuenteDatos> findByNombre(String nombre);

    List<FuenteDatos> findByOrganismo(String organismo);

    List<FuenteDatos> findByEsConfiableTrue();

    @Query("SELECT f FROM FuenteDatos f WHERE f.metodologia LIKE %:metodologia%")
    List<FuenteDatos> findByMetodologiaContaining(String metodologia);

    @Query("SELECT f.organismo, COUNT(f) FROM FuenteDatos f GROUP BY f.organismo")
    List<Object[]> countByOrganismo();
}