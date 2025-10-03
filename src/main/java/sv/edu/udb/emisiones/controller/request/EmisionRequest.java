package sv.edu.udb.emisiones.controller.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmisionRequest {
    @NotNull(message = "El año es obligatorio")
    private Integer anio;

    @NotNull(message = "El mes es obligatorio")
    private Integer mes;

    @NotNull(message = "La cantidad de tCO2 es obligatoria")
    @Positive(message = "La cantidad debe ser positiva")
    private Double cantidadTCO2;

    @NotNull(message = "El sector es obligatorio")
    private Long sectorId;

    private Long subsectorId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaRegistro;
}