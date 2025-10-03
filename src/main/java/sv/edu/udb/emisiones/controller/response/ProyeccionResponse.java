package sv.edu.udb.emisiones.controller.response;

import lombok.*;

import java.time.LocalDate;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProyeccionResponse {
    private Integer anioBase;
    private Integer aniosProyeccion;
    private Map<String, Double> proyeccionLineal;
    private Map<String, Double> escenarioOptimista;
    private Map<String, Double> escenarioPesimista;
    private String supuestos;
    private String recomendacionesMitigacion;

    // Nueva propiedad agregada
    private LocalDate fechaProyeccion;
}