package sv.edu.udb.emisiones.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import sv.edu.udb.emisiones.controller.response.AnalisisComparativoResponse;
import sv.edu.udb.emisiones.controller.response.AnalisisVariacionResponse;
import sv.edu.udb.emisiones.controller.response.ProyeccionResponse;
import sv.edu.udb.emisiones.controller.response.ReporteConsistenciaResponse;
import sv.edu.udb.emisiones.service.AnalisisService;

import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AnalisisController.class)
class AnalisisControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AnalisisService analisisService;

    @Test
    void whenAnalizarVariacionPandemia_thenReturnAnalisis() throws Exception {
        // given
        AnalisisVariacionResponse response = AnalisisVariacionResponse.builder()
                .anioBaseInicio(2015)
                .anioBaseFin(2019)
                .anioComparacion(2020)
                .variacionPorcentual(-15.5)
                .tendencia("DECRECIENTE_MODERADA")
                .interpretacion("Reducción durante confinamientos por COVID-19")
                .fechaGeneracion(java.time.LocalDateTime.now()) // Nueva línea
                .build();

        when(analisisService.analizarVariacionPandemia()).thenReturn(response);

        // when & then
        mockMvc.perform(get("/api/analisis/variacion-pandemia"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.variacionPorcentual").value(-15.5))
                .andExpect(jsonPath("$.tendencia").value("DECRECIENTE_MODERADA"))
                .andExpect(jsonPath("$.fechaGeneracion").exists()); // Nueva verificación
    }

    @Test
    void whenProyectarEmisiones_thenReturnProyeccion() throws Exception {
        // given
        ProyeccionResponse response = ProyeccionResponse.builder()
                .anioBase(2023)
                .aniosProyeccion(5)
                .proyeccionLineal(Map.of("2024", 1050.0, "2025", 1100.0))
                .escenarioOptimista(Map.of("2024", 840.0, "2025", 880.0))
                .escenarioPesimista(Map.of("2024", 1260.0, "2025", 1320.0))
                .supuestos("Tendencia histórica 2015-2023")
                .fechaProyeccion(java.time.LocalDate.now()) // Nueva línea
                .build();

        when(analisisService.proyectarEmisiones(5)).thenReturn(response);

        // when & then
        mockMvc.perform(get("/api/analisis/proyecciones")
                        .param("aniosFuturo", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.aniosProyeccion").value(5))
                .andExpect(jsonPath("$.proyeccionLineal['2024']").value(1050.0))
                .andExpect(jsonPath("$.fechaProyeccion").exists()); // Nueva verificación
    }
}