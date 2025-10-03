package sv.edu.udb.emisiones.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import sv.edu.udb.emisiones.domain.Sector;
import sv.edu.udb.emisiones.domain.Subsector;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class SectorRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private SectorRepository sectorRepository;

    @Test
    void whenFindByNombre_thenReturnSector() {
        // given
        Sector sector = Sector.builder()
                .nombre("Energía")
                .descripcion("Sector energético")
                .build();
        entityManager.persist(sector);
        entityManager.flush();

        // when
        Optional<Sector> found = sectorRepository.findByNombre("Energía");

        // then
        assertThat(found).isPresent();
        assertThat(found.get().getNombre()).isEqualTo("Energía");
    }

    @Test
    void whenFindAllWithSubsectores_thenReturnSectoresWithSubsectores() {
        // given
        Sector sector = Sector.builder()
                .nombre("Transporte")
                .descripcion("Sector transporte")
                .build();

        Subsector subsector = Subsector.builder()
                .nombre("Transporte público")
                .tipo("Urbano")
                .intensidad("MEDIA")
                .sector(sector)
                .build();

        sector.getSubsectores().add(subsector);

        entityManager.persist(sector);
        entityManager.flush();

        // when
        List<Sector> sectores = sectorRepository.findAllWithSubsectores();

        // then
        assertThat(sectores).isNotEmpty();
        assertThat(sectores.get(0).getSubsectores()).isNotEmpty();
    }

    @Test
    void whenFindByNombres_thenReturnSectores() {
        // given
        Sector sector1 = Sector.builder().nombre("Industria").build();
        Sector sector2 = Sector.builder().nombre("Residencial").build();
        entityManager.persist(sector1);
        entityManager.persist(sector2);
        entityManager.flush();

        // when
        List<Sector> sectores = sectorRepository.findByNombres(List.of("Industria", "Residencial"));

        // then
        assertThat(sectores).hasSize(2);
    }
}