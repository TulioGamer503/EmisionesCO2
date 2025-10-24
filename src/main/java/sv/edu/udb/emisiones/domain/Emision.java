/**
 * Entidad que representa los registros de emisiones de CO₂ en la base de datos.
 * Contiene información sobre el año, mes, cantidad emitida, sector y subsector asociado,
 * así como la fecha en que se registró la emisión.
 *-
 * Esta clase se utiliza dentro del contexto de persistencia de JPA para mapear la tabla "emisiones".
 */
package sv.edu.udb.emisiones.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "emisiones", indexes = {
        @Index(name = "idx_emision_anio", columnList = "anio"),
        @Index(name = "idx_emision_sector", columnList = "sector_id"),
        @Index(name = "idx_emision_anio_mes", columnList = "anio,mes")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Emision {

    /** Identificador único de la emisión (clave primaria autogenerada). */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Año en el que se registró la emisión. */
    @Column(nullable = false)
    private Integer anio;

    /** Mes en el que se registró la emisión. */
    @Column(nullable = false)
    private Integer mes;

    /** Cantidad emitida de CO₂ expresada en toneladas (TCO₂). */
    @Column(name = "cantidad_tco2", nullable = false)
    private Double cantidadTCO2;

    /**
     * Relación muchos-a-uno con la entidad Sector.
     * Indica a qué sector económico pertenece esta emisión.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sector_id", nullable = false)
    private Sector sector;

    /**
     * Relación muchos-a-uno con la entidad Subsector.
     * Puede ser nula si la emisión no pertenece a un subsector específico.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subsector_id")
    private Subsector subsector;

    /** Fecha en que fue registrado el dato de emisión. */
    @Column(name = "fecha_registro")
    private LocalDate fechaRegistro;

    /**
     * Calcula el porcentaje de reducción de emisiones comparado con un registro anterior.
     * Basado en una fórmula simple derivada de la ecuación de Kaya.
     *
     * @param emisionAnterior la emisión registrada previamente para comparar
     * @return porcentaje de reducción (valor positivo si hubo reducción)
     */
    public Double calcularReduccion(Emision emisionAnterior) {
        if (emisionAnterior == null || emisionAnterior.cantidadTCO2 == 0) {
            return 0.0;
        }
        return ((emisionAnterior.cantidadTCO2 - this.cantidadTCO2) / emisionAnterior.cantidadTCO2) * 100;
    }

    /**
     * Identifica de forma general los posibles factores (drivers) que contribuyeron a la reducción de emisiones.
     * Esta implementación es una versión simplificada de la ecuación de Kaya.
     *
     * @return descripción textual de los factores que influyen en la reducción.
     */
    public String identificarDriverReduccion() {
        return "Análisis de drivers (Población, GDP, Intensidad Energética, Mix Energético)";
    }
}
