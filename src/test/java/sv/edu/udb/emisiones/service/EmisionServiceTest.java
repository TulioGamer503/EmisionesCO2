package sv.edu.udb.emisiones.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sv.edu.udb.emisiones.controller.request.EmisionRequest;
import sv.edu.udb.emisiones.controller.response.AnalisisVariacionResponse;
import sv.edu.udb.emisiones.controller.response.EmisionResponse;
import sv.edu.udb.emisiones.domain.Emision;
import sv.edu.udb.emisiones.domain.Sector;
import sv.edu.udb.emisiones.repository.EmisionRepository;
import sv.edu.udb.emisiones.repository.SectorRepository;
import sv.edu.udb.emisiones.repository.SubsectorRepository;
import sv.edu.udb.emisiones.service.implementation.EmisionServiceImpl;
import sv.edu.udb.emisiones.service.mapper.EmisionMapper;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmisionServiceTest {

    @Mock
    private EmisionRepository emisionRepository;

    @Mock
    private SectorRepository sectorRepository;

    @Mock
    private SubsectorRepository subsectorRepository;

    @Mock
    private EmisionMapper emisionMapper;

    @InjectMocks
    private EmisionServiceImpl emisionService;

    @Test
    void whenFindAll_thenReturnEmisionList() {
        // given
        Emision emision = Emision.builder().id(1L).anio(2020).build();
        EmisionResponse response = EmisionResponse.builder().id(1L).anio(2020).build();

        when(emisionRepository.findAll()).thenReturn(List.of(emision));
        when(emisionMapper.toResponseList(any())).thenReturn(List.of(response));

        // when
        List<EmisionResponse> result = emisionService.findAll();

        // then
        assertThat(result).isNotEmpty();
        assertThat(result.get(0).getAnio()).isEqualTo(2020);
    }

    @Test
    void whenFindById_thenReturnEmision() {
        // given
        Emision emision = Emision.builder().id(1L).anio(2020).build();
        EmisionResponse response = EmisionResponse.builder().id(1L).anio(2020).build();

        when(emisionRepository.findById(1L)).thenReturn(Optional.of(emision));
        when(emisionMapper.toResponse(emision)).thenReturn(response);

        // when
        EmisionResponse result = emisionService.findById(1L);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getAnio()).isEqualTo(2020);
    }

    @Test
    void whenSaveValidEmision_thenReturnSavedEmision() {
        // given
        EmisionRequest request = EmisionRequest.builder()
                .anio(2020)
                .mes(1)
                .cantidadTCO2(1000.0)
                .sectorId(1L)
                .fechaRegistro(LocalDate.now())
                .build();

        Sector sector = Sector.builder().id(1L).nombre("Energía").build();
        Emision emision = Emision.builder().id(1L).anio(2020).build();
        EmisionResponse response = EmisionResponse.builder().id(1L).anio(2020).build();

        when(sectorRepository.findById(1L)).thenReturn(Optional.of(sector));
        when(emisionMapper.toEntity(request)).thenReturn(emision);
        when(emisionRepository.save(emision)).thenReturn(emision);
        when(emisionMapper.toResponse(emision)).thenReturn(response);

        // when
        EmisionResponse result = emisionService.save(request);

        // then
        assertThat(result).isNotNull();
        verify(emisionRepository, times(1)).save(emision);
    }

    @Test
    void whenSaveEmisionWithInvalidSector_thenThrowException() {
        // given
        EmisionRequest request = EmisionRequest.builder()
                .sectorId(999L)
                .build();

        when(sectorRepository.findById(999L)).thenReturn(Optional.empty());

        // when & then
        assertThrows(EntityNotFoundException.class, () -> {
            emisionService.save(request);
        });
    }

    @Test
    void whenAnalizarVariacion_thenReturnAnalisis() {
        // given
        Emision emisionBase = Emision.builder().anio(2019).cantidadTCO2(1000.0).build();
        Emision emisionComparacion = Emision.builder().anio(2020).cantidadTCO2(800.0).build();

        when(emisionRepository.findByRangoAnios(2019, 2019)).thenReturn(List.of(emisionBase));
        when(emisionRepository.findByAnio(2020)).thenReturn(List.of(emisionComparacion));

        // when
        AnalisisVariacionResponse result = emisionService.analizarVariacion(2019, 2019, 2020);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getVariacionPorcentual()).isEqualTo(-20.0);
        assertThat(result.getTendencia()).isEqualTo("DECRECIENTE_FUERTE");
    }

    @Test
    void whenFindByAnio_thenReturnEmisionesDelAnio() {
        // given
        Emision emision = Emision.builder().id(1L).anio(2020).build();
        EmisionResponse response = EmisionResponse.builder().id(1L).anio(2020).build();

        when(emisionRepository.findByAnio(2020)).thenReturn(List.of(emision));
        when(emisionMapper.toResponseList(any())).thenReturn(List.of(response));

        // when
        List<EmisionResponse> result = emisionService.findByAnio(2020);

        // then
        assertThat(result).isNotEmpty();
        assertThat(result.get(0).getAnio()).isEqualTo(2020);
    }

    @Test
    void whenDeleteExistingEmision_thenSuccess() {
        // given
        when(emisionRepository.existsById(1L)).thenReturn(true);

        // when
        emisionService.delete(1L);

        // then
        verify(emisionRepository, times(1)).deleteById(1L);
    }

    @Test
    void whenDeleteNonExistingEmision_thenThrowException() {
        // given
        when(emisionRepository.existsById(1L)).thenReturn(false);

        // when & then
        assertThrows(EntityNotFoundException.class, () -> {
            emisionService.delete(1L);
        });
    }
}