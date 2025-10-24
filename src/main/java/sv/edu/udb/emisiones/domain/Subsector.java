/**
 * Entidad que representa un subsector económico dentro del sistema de emisiones de CO₂.
 *.
 * Cada subsector pertenece a un sector principal y contiene información sobre su tipo
 * e intensidad de emisiones. Ejemplos de subsectores pueden ser: Transporte Terrestre,
 * Energía Renovable, Agricultura Ganadera, etc.
 */
package sv.edu.udb.emisiones.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "subsectores")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Subsector {

    /** Identificador único del subsector (clave primaria autogenerada). */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Nombre del subsector (por ejemplo: Transporte, Energía Eléctrica, Agricultura, etc.). */
    @Column(nullable = false)
    private String nombre;

    /** Tipo o clasificación del subsector (por ejemplo: Industrial, Residencial, Comercial, etc.). */
    @Column(nullable = false)
    private String tipo;

    /** Nivel o categoría de intensidad de emisiones asociada al subsector. */
    @Column(name = "intensidad_emisiones")
    private String intensidad;

    /**
     * Relación muchos-a-uno con la entidad Sector.
     *.
     * Indica a qué sector principal pertenece este subsector.
     * La carga es perezosa (lazy) para optimizar el rendimiento en consultas.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sector_id")
    private Sector sector;
}
