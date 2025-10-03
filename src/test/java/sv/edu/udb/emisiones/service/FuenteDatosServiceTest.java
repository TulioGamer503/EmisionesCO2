package sv.edu.udb.emisiones.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sv.edu.udb.emisiones.controller.request.FuenteDatosRequest;
import sv.edu.udb.emisiones.controller.response.FuenteDatosResponse;
import sv.edu.udb.emisiones.domain.FuenteDatos;
import sv.edu.udb.emisiones.repository.FuenteDatosRepository;
import sv.edu.udb.emisiones.service.implementation.FuenteDatosServiceImpl;
import sv.edu.udb.emisiones.service.mapper.FuenteDatosMapper;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FuenteDatosServiceTest {

    @Mock
    private FuenteDatosRepository fuenteDatosRepository;

    @Mock
    private FuenteDatosMapper fuenteDatosMapper;

    @InjectMocks
    private FuenteDatosServiceImpl fuenteDatosService;

    @Test
    void whenFindAll_thenReturnFuenteDatosList() {
        // given
        FuenteDatos fuente = FuenteDatos.builder().id(1L).nombre("IEA").build();
        FuenteDatosResponse response = FuenteDatosResponse.builder().id(1L).nombre("IEA").build();

        when(fuenteDatosRepository.findAll()).thenReturn(List.of(fuente));
        when(fuenteDatosMapper.toResponseList(any())).thenReturn(List.of(response));

        // when
        List<FuenteDatosResponse> result = fuenteDatosService.findAll();

        // then
        assertThat(result).isNotEmpty();
        assertThat(result.get(0).getNombre()).isEqualTo("IEA");
    }

    @Test
    void whenFindByEsConfiableTrue_thenReturnConfiableFuentes() {
        // given
        FuenteDatos fuente = FuenteDatos.builder().id(1L).nombre("IEA").esConfiable(true).build();
        FuenteDatosResponse response = FuenteDatosResponse.builder().id(1L).nombre("IEA").esConfiable(true).build();

        when(fuenteDatosRepository.findByEsConfiableTrue()).thenReturn(List.of(fuente));
        when(fuenteDatosMapper.toResponseList(any())).thenReturn(List.of(response));

        // when
        List<FuenteDatosResponse> result = fuenteDatosService.findByEsConfiableTrue();

        // then
        assertThat(result).isNotEmpty();
        assertThat(result.get(0).getEsConfiable()).isTrue();
    }

    @Test
    void whenSaveValidFuente_thenReturnSavedFuente() {
        // given
        FuenteDatosRequest request = FuenteDatosRequest.builder()
                .nombre("Nueva Fuente")
                .organismo("Organismo Test")
                .esConfiable(true)
                .build();

        FuenteDatos fuente = FuenteDatos.builder().id(1L).nombre("Nueva Fuente").build();
        FuenteDatosResponse response = FuenteDatosResponse.builder().id(1L).nombre("Nueva Fuente").build();

        when(fuenteDatosMapper.toEntity(request)).thenReturn(fuente);
        when(fuenteDatosRepository.save(fuente)).thenReturn(fuente);
        when(fuenteDatosMapper.toResponse(fuente)).thenReturn(response);

        // when
        FuenteDatosResponse result = fuenteDatosService.save(request);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getNombre()).isEqualTo("Nueva Fuente");
        verify(fuenteDatosRepository, times(1)).save(fuente);
    }

    @Test
    void whenDeleteExistingFuente_thenSuccess() {
        // given
        when(fuenteDatosRepository.existsById(1L)).thenReturn(true);

        // when
        fuenteDatosService.delete(1L);

        // then
        verify(fuenteDatosRepository, times(1)).deleteById(1L);
    }

    @Test
    void whenDeleteNonExistingFuente_thenThrowException() {
        // given
        when(fuenteDatosRepository.existsById(1L)).thenReturn(false);

        // when & then
        assertThrows(EntityNotFoundException.class, () -> {
            fuenteDatosService.delete(1L);
        });
    }
}