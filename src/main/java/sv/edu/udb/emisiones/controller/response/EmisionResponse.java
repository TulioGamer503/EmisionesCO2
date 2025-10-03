package sv.edu.udb.emisiones.controller.response;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmisionResponse {
    private Long id;
    private Integer anio;
    private Integer mes;
    private Double cantidadTCO2;
    private String sectorNombre;
    private String subsectorNombre;
    private LocalDate fechaRegistro;
}