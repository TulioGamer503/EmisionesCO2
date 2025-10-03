package sv.edu.udb.emisiones.controller.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnalisisVariacionResponse {
    private Integer anioBaseInicio;
    private Integer anioBaseFin;
    private Integer anioComparacion;
    private Double totalEmisionesBase;
    private Double totalEmisionesComparacion;
    private Double variacionAbsoluta;
    private Double variacionPorcentual;
    private Map<String, Double> variacionPorSector;
    private Map<String, Double> contribucionPorSector;
    private String tendencia;
    private String interpretacion;
    @Builder.Default
    private String metodologia = "IPCC 2023";
    private String recomendaciones;

    // Nueva propiedad agregada
    private LocalDateTime fechaGeneracion;
}