package sv.edu.udb.emisiones.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sv.edu.udb.emisiones.controller.response.AnalisisComparativoResponse;
import sv.edu.udb.emisiones.controller.response.AnalisisVariacionResponse;
import sv.edu.udb.emisiones.controller.response.ProyeccionResponse;
import sv.edu.udb.emisiones.controller.response.ReporteConsistenciaResponse;
import sv.edu.udb.emisiones.domain.Emision;
import sv.edu.udb.emisiones.repository.AnalisisRepository;
import sv.edu.udb.emisiones.repository.EmisionRepository;
import sv.edu.udb.emisiones.service.implementation.AnalisisServiceImpl;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AnalisisServiceTest {

    @Mock
    private AnalisisRepository analisisRepository;

    @Mock
    private EmisionRepository emisionRepository;

    @InjectMocks
    private AnalisisServiceImpl analisisService;

    @Test
    void whenAnalizarVariacionPandemia_thenReturnAnalisis() {
        // given
        Emision emision2019 = Emision.builder().anio(2019).cantidadTCO2(1000.0).build();
        Emision emision2020 = Emision.builder().anio(2020).cantidadTCO2(800.0).build();

        when(emisionRepository.findPromedioEmisionesPorPeriodo(2015, 2019)).thenReturn(1000.0);
        when(emisionRepository.findByAnio(2020)).thenReturn(List.of(emision2020));

        // when
        AnalisisVariacionResponse result = analisisService.analizarVariacionPandemia();

        // then
        assertThat(result).isNotNull();
        assertThat(result.getAnioComparacion()).isEqualTo(2020);
        assertThat(result.getVariacionPorcentual()).isEqualTo(-20.0);
    }

    @Test
    void whenCompararConReferentesGlobales_thenReturnComparacion() {
        // given
        when(emisionRepository.findPromedioEmisionesPorPeriodo(2015, 2019)).thenReturn(1000.0);
        when(emisionRepository.findByAnio(2020)).thenReturn(List.of(
                Emision.builder().anio(2020).cantidadTCO2(900.0).build()
        ));

        // when
        AnalisisComparativoResponse result = analisisService.compararConReferentesGlobales();

        // then
        assertThat(result).isNotNull();
        assertThat(result.getVariacionElSalvador()).isEqualTo(-10.0);
        assertThat(result.getReferentesGlobales()).isNotEmpty();
    }

    @Test
    void whenProyectarEmisiones_thenReturnProyeccion() {
        // given
        when(analisisRepository.findDatosParaProyeccion(anyInt(), anyInt()))
                .thenReturn(List.of(new Object[]{2019, 1000.0}, new Object[]{2020, 900.0}));

        // when
        ProyeccionResponse result = analisisService.proyectarEmisiones(5);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getProyeccionLineal()).isNotEmpty();
        assertThat(result.getEscenarioOptimista()).isNotEmpty();
        assertThat(result.getEscenarioPesimista()).isNotEmpty();
    }

    @Test
    void whenCalcularIndicadoresKaya_thenReturnIndicadores() {
        // given
        when(emisionRepository.findByAnio(2020)).thenReturn(List.of(
                Emision.builder().anio(2020).cantidadTCO2(1000.0).build()
        ));

        // when
        Map<String, Object> result = analisisService.calcularIndicadoresKaya(2020);

        // then
        assertThat(result).isNotNull();
        assertThat(result).containsKeys("poblacion", "pibPerCapita", "intensidadEnergetica",
                "intensidadCarbono", "emisionesCalculadasKaya", "emisionesReales");
    }

    @Test
    void whenVerificarConsistenciaDatos_thenReturnReporte() {
        // given
        when(emisionRepository.count()).thenReturn(10L);
        when(emisionRepository.findTotalEmisionesPorAnio()).thenReturn(List.of(
                new Object[]{2019, 1000.0}, new Object[]{2020, 900.0}
        ));

        // when
        ReporteConsistenciaResponse result = analisisService.verificarConsistenciaDatos();

        // then
        assertThat(result).isNotNull();
        assertThat(result.getDatosConsistentes()).isTrue();
        assertThat(result.getNivelConfianza()).isEqualTo("ALTO");
    }
}