package sv.edu.udb.emisiones.controller.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FuenteDatosResponse {
    private Long id;
    private String nombre;
    private String organismo;
    private String url;
    private String metodologia;
    private Boolean esConfiable;
}