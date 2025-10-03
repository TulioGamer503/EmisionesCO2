package sv.edu.udb.emisiones.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sv.edu.udb.emisiones.controller.request.SectorRequest;
import sv.edu.udb.emisiones.controller.response.SectorResponse;
import sv.edu.udb.emisiones.domain.Sector;
import sv.edu.udb.emisiones.repository.SectorRepository;
import sv.edu.udb.emisiones.service.implementation.SectorServiceImpl;
import sv.edu.udb.emisiones.service.mapper.SectorMapper;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SectorServiceTest {

    @Mock
    private SectorRepository sectorRepository;

    @Mock
    private SectorMapper sectorMapper;

    @InjectMocks
    private SectorServiceImpl sectorService;

    @Test
    void whenFindAll_thenReturnSectorList() {
        // given
        Sector sector = Sector.builder().id(1L).nombre("Test").build();
        SectorResponse response = SectorResponse.builder().id(1L).nombre("Test").build();

        when(sectorRepository.findAll()).thenReturn(List.of(sector));
        when(sectorMapper.toResponseList(any())).thenReturn(List.of(response));

        // when
        List<SectorResponse> result = sectorService.findAll();

        // then
        assertThat(result).isNotEmpty();
        assertThat(result.get(0).getNombre()).isEqualTo("Test");
        verify(sectorRepository, times(1)).findAll();
    }

    @Test
    void whenFindById_thenReturnSector() {
        // given
        Sector sector = Sector.builder().id(1L).nombre("Test").build();
        SectorResponse response = SectorResponse.builder().id(1L).nombre("Test").build();

        when(sectorRepository.findById(1L)).thenReturn(Optional.of(sector));
        when(sectorMapper.toResponse(sector)).thenReturn(response);

        // when
        SectorResponse result = sectorService.findById(1L);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getNombre()).isEqualTo("Test");
    }

    @Test
    void whenFindByIdNotFound_thenThrowException() {
        // given
        when(sectorRepository.findById(1L)).thenReturn(Optional.empty());

        // when & then
        assertThrows(EntityNotFoundException.class, () -> {
            sectorService.findById(1L);
        });
    }

    @Test
    void whenSaveValidSector_thenReturnSavedSector() {
        // given
        SectorRequest request = SectorRequest.builder()
                .nombre("Nuevo Sector")
                .descripcion("Descripción")
                .build();

        Sector sector = Sector.builder().id(1L).nombre("Nuevo Sector").build();
        SectorResponse response = SectorResponse.builder().id(1L).nombre("Nuevo Sector").build();

        when(sectorRepository.findByNombre("Nuevo Sector")).thenReturn(Optional.empty());
        when(sectorMapper.toEntity(request)).thenReturn(sector);
        when(sectorRepository.save(sector)).thenReturn(sector);
        when(sectorMapper.toResponse(sector)).thenReturn(response);

        // when
        SectorResponse result = sectorService.save(request);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getNombre()).isEqualTo("Nuevo Sector");
        verify(sectorRepository, times(1)).save(sector);
    }

    @Test
    void whenSaveDuplicateSector_thenThrowException() {
        // given
        SectorRequest request = SectorRequest.builder().nombre("Existente").build();
        Sector existingSector = Sector.builder().id(1L).nombre("Existente").build();

        when(sectorRepository.findByNombre("Existente")).thenReturn(Optional.of(existingSector));

        // when & then
        assertThrows(IllegalArgumentException.class, () -> {
            sectorService.save(request);
        });
    }

    @Test
    void whenDeleteExistingSector_thenSuccess() {
        // given
        when(sectorRepository.existsById(1L)).thenReturn(true);
        when(sectorRepository.findById(1L)).thenReturn(Optional.of(Sector.builder().build()));

        // when
        sectorService.delete(1L);

        // then
        verify(sectorRepository, times(1)).deleteById(1L);
    }

    @Test
    void whenDeleteNonExistingSector_thenThrowException() {
        // given
        when(sectorRepository.existsById(1L)).thenReturn(false);

        // when & then
        assertThrows(EntityNotFoundException.class, () -> {
            sectorService.delete(1L);
        });
    }
}