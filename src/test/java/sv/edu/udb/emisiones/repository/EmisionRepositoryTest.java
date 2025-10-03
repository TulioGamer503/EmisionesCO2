package sv.edu.udb.emisiones.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import sv.edu.udb.emisiones.domain.Emision;
import sv.edu.udb.emisiones.domain.Sector;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class EmisionRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private EmisionRepository emisionRepository;

    @Test
    void whenFindByAnio_thenReturnEmisiones() {
        // given
        Sector sector = Sector.builder().nombre("Test Sector").build();
        entityManager.persist(sector);

        Emision emision = Emision.builder()
                .anio(2020)
                .mes(1)
                .cantidadTCO2(1000.0)
                .sector(sector)
                .fechaRegistro(LocalDate.now())
                .build();
        entityManager.persist(emision);
        entityManager.flush();

        // when
        List<Emision> emisiones = emisionRepository.findByAnio(2020);

        // then
        assertThat(emisiones).isNotEmpty();
        assertThat(emisiones.get(0).getAnio()).isEqualTo(2020);
    }

    @Test
    void whenFindByRangoAnios_thenReturnEmisiones() {
        // given
        Sector sector = Sector.builder().nombre("Test Sector").build();
        entityManager.persist(sector);

        Emision emision1 = Emision.builder()
                .anio(2019)
                .mes(1)
                .cantidadTCO2(1000.0)
                .sector(sector)
                .build();

        Emision emision2 = Emision.builder()
                .anio(2020)
                .mes(1)
                .cantidadTCO2(800.0)
                .sector(sector)
                .build();

        entityManager.persist(emision1);
        entityManager.persist(emision2);
        entityManager.flush();

        // when
        List<Emision> emisiones = emisionRepository.findByRangoAnios(2019, 2020);

        // then
        assertThat(emisiones).hasSize(2);
    }

    @Test
    void whenFindTotalEmisionesPorSector_thenReturnResults() {
        // given
        Sector sector = Sector.builder().nombre("Energía").build();
        entityManager.persist(sector);

        Emision emision = Emision.builder()
                .anio(2020)
                .mes(1)
                .cantidadTCO2(1500.0)
                .sector(sector)
                .build();
        entityManager.persist(emision);
        entityManager.flush();

        // when
        List<Object[]> resultados = emisionRepository.findTotalEmisionesPorSector(2020);

        // then
        assertThat(resultados).isNotEmpty();
    }

    @Test
    void whenFindPromedioEmisionesPorPeriodo_thenReturnAverage() {
        // given
        Sector sector = Sector.builder().nombre("Test").build();
        entityManager.persist(sector);

        Emision emision1 = Emision.builder()
                .anio(2019)
                .mes(1)
                .cantidadTCO2(1000.0)
                .sector(sector)
                .build();

        Emision emision2 = Emision.builder()
                .anio(2020)
                .mes(1)
                .cantidadTCO2(2000.0)
                .sector(sector)
                .build();

        entityManager.persist(emision1);
        entityManager.persist(emision2);
        entityManager.flush();

        // when
        Double promedio = emisionRepository.findPromedioEmisionesPorPeriodo(2019, 2020);

        // then
        assertThat(promedio).isEqualTo(1500.0);
    }
}