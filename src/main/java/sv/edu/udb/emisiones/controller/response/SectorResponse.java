/**
 * Representa la respuesta que el sistema devuelve al cliente cuando se consulta, 
 * crea o actualiza un sector económico dentro del sistema de emisiones de CO₂.
 *.
 * se utiliza en los endpoints del SectorController y contiene
 * información simplificada sobre el sector, incluyendo su nombre, 
 * descripción y la cantidad de subsectores asociados.
 */

package sv.edu.udb.emisiones.controller.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SectorResponse {

    /**
     * Identificador único del sector.
     * Se genera automáticamente al registrarlo en la base de datos.
     */
    private Long id;

    /**Nombre del sector económico.*/
    private String nombre;

    /**Descripción opcional del sector.*/
    private String descripcion;

    /**Cantidad total de subsectores asociados al sector.*/
    private Integer totalSubsectores;
}
