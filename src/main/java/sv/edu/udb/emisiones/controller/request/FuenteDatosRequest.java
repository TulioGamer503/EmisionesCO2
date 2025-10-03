package sv.edu.udb.emisiones.controller.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FuenteDatosRequest {
    @NotBlank(message = "El nombre de la fuente es obligatorio")
    private String nombre;

    private String organismo;
    private String url;
    private String metodologia;

    @Builder.Default
    private Boolean esConfiable = true;
}