package sv.edu.udb.emisiones.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import sv.edu.udb.emisiones.controller.request.SectorRequest;
import sv.edu.udb.emisiones.controller.response.SectorResponse;
import sv.edu.udb.emisiones.service.SectorService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SectorController.class)
class SectorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SectorService sectorService;

    @Test
    void whenGetAllSectores_thenReturnSectorList() throws Exception {
        // given
        SectorResponse response = SectorResponse.builder()
                .id(1L)
                .nombre("Energía")
                .descripcion("Sector energético")
                .totalSubsectores(2)
                .build();

        when(sectorService.findAll()).thenReturn(List.of(response));

        // when & then
        mockMvc.perform(get("/api/sectores")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Energía"))
                .andExpect(jsonPath("$[0].totalSubsectores").value(2));
    }

    @Test
    void whenGetSectorById_thenReturnSector() throws Exception {
        // given
        SectorResponse response = SectorResponse.builder()
                .id(1L)
                .nombre("Transporte")
                .build();

        when(sectorService.findById(1L)).thenReturn(response);

        // when & then
        mockMvc.perform(get("/api/sectores/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Transporte"));
    }

    @Test
    void whenCreateSector_thenReturnCreated() throws Exception {
        // given
        SectorRequest request = SectorRequest.builder()
                .nombre("Nuevo Sector")
                .descripcion("Descripción del sector")
                .build();

        SectorResponse response = SectorResponse.builder()
                .id(1L)
                .nombre("Nuevo Sector")
                .descripcion("Descripción del sector")
                .build();

        when(sectorService.save(any(SectorRequest.class))).thenReturn(response);

        // when & then
        mockMvc.perform(post("/api/sectores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Nuevo Sector"));
    }

    @Test
    void whenCreateSectorWithInvalidData_thenReturnBadRequest() throws Exception {
        // given
        SectorRequest request = SectorRequest.builder()
                .nombre("")  // Nombre vacío - inválido
                .build();

        // when & then
        mockMvc.perform(post("/api/sectores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenUpdateSector_thenReturnUpdated() throws Exception {
        // given
        SectorRequest request = SectorRequest.builder()
                .nombre("Sector Actualizado")
                .descripcion("Descripción actualizada")
                .build();

        SectorResponse response = SectorResponse.builder()
                .id(1L)
                .nombre("Sector Actualizado")
                .descripcion("Descripción actualizada")
                .build();

        when(sectorService.update(anyLong(), any(SectorRequest.class))).thenReturn(response);

        // when & then
        mockMvc.perform(put("/api/sectores/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Sector Actualizado"));
    }

    @Test
    void whenDeleteSector_thenReturnNoContent() throws Exception {
        // when & then
        mockMvc.perform(delete("/api/sectores/1"))
                .andExpect(status().isNoContent());
    }
}