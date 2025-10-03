package sv.edu.udb.emisiones.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import sv.edu.udb.emisiones.controller.request.EmisionRequest;
import sv.edu.udb.emisiones.controller.response.AnalisisVariacionResponse;
import sv.edu.udb.emisiones.controller.response.EmisionResponse;
import sv.edu.udb.emisiones.service.EmisionService;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmisionController.class)
class EmisionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EmisionService emisionService;

    @Test
    void whenGetAllEmisiones_thenReturnEmisionList() throws Exception {
        // given
        EmisionResponse response = EmisionResponse.builder()
                .id(1L)
                .anio(2020)
                .mes(1)
                .cantidadTCO2(1000.0)
                .sectorNombre("Energía")
                .build();

        when(emisionService.findAll()).thenReturn(List.of(response));

        // when & then
        mockMvc.perform(get("/api/emisiones")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].anio").value(2020))
                .andExpect(jsonPath("$[0].sectorNombre").value("Energía"));
    }

    @Test
    void whenGetEmisionesByAnio_thenReturnEmisiones() throws Exception {
        // given
        EmisionResponse response = EmisionResponse.builder()
                .id(1L)
                .anio(2020)
                .mes(1)
                .cantidadTCO2(1000.0)
                .build();

        when(emisionService.findByAnio(2020)).thenReturn(List.of(response));

        // when & then
        mockMvc.perform(get("/api/emisiones/anio/2020"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].anio").value(2020));
    }

    @Test
    void whenCreateEmision_thenReturnCreated() throws Exception {
        // given
        EmisionRequest request = EmisionRequest.builder()
                .anio(2020)
                .mes(1)
                .cantidadTCO2(1000.0)
                .sectorId(1L)
                .fechaRegistro(LocalDate.now())
                .build();

        EmisionResponse response = EmisionResponse.builder()
                .id(1L)
                .anio(2020)
                .mes(1)
                .cantidadTCO2(1000.0)
                .build();

        when(emisionService.save(any(EmisionRequest.class))).thenReturn(response);

        // when & then
        mockMvc.perform(post("/api/emisiones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.anio").value(2020));
    }

    @Test
    void whenCreateEmisionWithInvalidData_thenReturnBadRequest() throws Exception {
        // given
        EmisionRequest request = EmisionRequest.builder()
                .anio(null)  // Año nulo - inválido
                .mes(1)
                .cantidadTCO2(1000.0)
                .sectorId(1L)
                .build();

        // when & then
        mockMvc.perform(post("/api/emisiones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenAnalizarVariacion_thenReturnAnalisis() throws Exception {
        // given
        AnalisisVariacionResponse response = AnalisisVariacionResponse.builder()
                .anioBaseInicio(2015)
                .anioBaseFin(2019)
                .anioComparacion(2020)
                .totalEmisionesBase(5000.0)
                .totalEmisionesComparacion(4000.0)
                .variacionAbsoluta(-1000.0)
                .variacionPorcentual(-20.0)
                .tendencia("DECRECIENTE_FUERTE")
                .interpretacion("Reducción significativa del 20.00% en las emisiones")
                .build();

        when(emisionService.analizarVariacion(2015, 2019, 2020)).thenReturn(response);

        // when & then
        mockMvc.perform(get("/api/emisiones/analisis/variacion")
                        .param("anioBaseInicio", "2015")
                        .param("anioBaseFin", "2019")
                        .param("anioComparacion", "2020"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.variacionPorcentual").value(-20.0))
                .andExpect(jsonPath("$.tendencia").value("DECRECIENTE_FUERTE"));
    }

    @Test
    void whenGetTotalEmisionesPorSector_thenReturnMap() throws Exception {
        // given
        Map<String, Double> resultados = Map.of(
                "Energía", 3000.0,
                "Transporte", 2000.0
        );

        when(emisionService.obtenerTotalEmisionesPorSector(2020)).thenReturn(resultados);

        // when & then
        mockMvc.perform(get("/api/emisiones/analisis/por-sector/2020"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.Energía").value(3000.0))
                .andExpect(jsonPath("$.Transporte").value(2000.0));
    }

    @Test
    void whenDeleteEmision_thenReturnNoContent() throws Exception {
        // when & then
        mockMvc.perform(delete("/api/emisiones/1"))
                .andExpect(status().isNoContent());
    }
}