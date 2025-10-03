package sv.edu.udb.emisiones.controller.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SectorResponse {
    private Long id;
    private String nombre;
    private String descripcion;
    private Integer totalSubsectores;
}