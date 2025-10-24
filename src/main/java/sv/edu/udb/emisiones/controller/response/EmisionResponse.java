/**
 * Representa la respuesta que el sistema envía al cliente al consultar,
 * crear o actualizar un registro de emisión de CO₂.
 *-
 * se utiliza para devolver información legible y simplificada
 * en los endpoints del EmisionController, evitando exponer directamente 
 * las entidades del dominio (Emision, Sector, Subsector).
 */

package sv.edu.udb.emisiones.controller.response;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmisionResponse {

    /**
     * Identificador único de la emisión registrada.
     * Se genera automáticamente al guardar el registro en la base de datos.
     */
    private Long id;

    /**Año correspondiente al registro de la emisión.Permite agrupar y comparar datos por periodos anuales.*/
    private Integer anio;

    /**Mes de la emisión expresado en número (1–12).*/
    private Integer mes;

    /**
     * Cantidad total de emisiones de CO₂ en toneladas (tCO₂).
     * Representa la magnitud de emisiones registradas en ese mes y año.
     */
    private Double cantidadTCO2;

    /**Nombre del sector económico asociado a la emisión.*/
    private String sectorNombre;

    /**Nombre del subsector al que pertenece la emisión (si aplica).*/
    private String subsectorNombre;

    /**
     * Fecha en la que fue registrado el dato de emisión.
     * Puede representar la fecha de ingreso o la fecha real del registro.
     */
    private LocalDate fechaRegistro;
}
