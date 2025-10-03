package sv.edu.udb.emisiones.controller.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReporteConsistenciaResponse {
    private LocalDateTime fechaVerificacion; // Ya existe
    private Boolean datosConsistentes;
    private List<String> verificacionesRealizadas;
    private List<String> problemasIdentificados;
    private String recomendaciones;
    private String nivelConfianza;
}