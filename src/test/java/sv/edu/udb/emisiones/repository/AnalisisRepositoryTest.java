package sv.edu.udb.emisiones.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import sv.edu.udb.emisiones.domain.Sector;
import sv.edu.udb.emisiones.domain.Subsector;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class SubsectorRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private SubsectorRepository subsectorRepository;

    @Test
    void whenFindBySectorId_thenReturnSubsectores() {
        // given
        Sector sector = Sector.builder().nombre("Industria").build();
        entityManager.persist(sector);

        Subsector subsector = Subsector.builder()
                .nombre("Industria pesada")
                .tipo("Manufactura")
                .intensidad("ALTA")
                .sector(sector)
                .build();
        entityManager.persist(subsector);
        entityManager.flush();

        // when
        List<Subsector> subsectores = subsectorRepository.findBySectorId(sector.getId());

        // then
        assertThat(subsectores).isNotEmpty();
        assertThat(subsectores.get(0).getSector().getId()).isEqualTo(sector.getId());
    }

    @Test
    void whenCountBySectorId_thenReturnCount() {
        // given
        Sector sector = Sector.builder().nombre("Test").build();
        entityManager.persist(sector);

        Subsector subsector1 = Subsector.builder()
                .nombre("Subsector 1")
                .tipo("Tipo 1")
                .sector(sector)
                .build();

        Subsector subsector2 = Subsector.builder()
                .nombre("Subsector 2")
                .tipo("Tipo 2")
                .sector(sector)
                .build();

        entityManager.persist(subsector1);
        entityManager.persist(subsector2);
        entityManager.flush();

        // when
        Long count = subsectorRepository.countBySectorId(sector.getId());

        // then
        assertThat(count).isEqualTo(2);
    }
}