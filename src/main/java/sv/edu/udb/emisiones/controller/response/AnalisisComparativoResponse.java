package sv.edu.udb.emisiones.controller.response;

import lombok.*;

import java.time.LocalDate;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnalisisComparativoResponse {
    private Double variacionElSalvador;
    private Map<String, Double> referentesGlobales;
    private String posicionRelativa;
    private String interpretacionComparativa;
    private String fuentes;

    // Nueva propiedad agregada
    private LocalDate fechaComparacion;
}