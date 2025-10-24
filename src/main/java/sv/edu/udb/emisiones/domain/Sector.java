/**
 * Entidad que representa un sector económico dentro del sistema de emisiones de CO₂.
 *.
 * Cada sector agrupa un conjunto de subsectores y registros de emisiones asociados.
 * Ejemplos de sectores pueden ser: Energía, Transporte, Agricultura, Industria, etc.
 */
package sv.edu.udb.emisiones.domain;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sectores")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sector {

    /** Identificador único del sector (clave primaria autogenerada). */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Nombre del sector económico. Debe ser único. */
    @Column(nullable = false, unique = true)
    private String nombre;

    /** Descripción general del sector, limitada a 500 caracteres. */
    @Column(length = 500)
    private String descripcion;

    /**
     * Relación uno-a-muchos con la entidad Subsector.
     *.
     * Cada sector puede tener múltiples subsectores asociados.
     * Se aplica cascada completa para mantener consistencia en las operaciones de persistencia.
     */
    @Builder.Default
    @OneToMany(mappedBy = "sector", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Subsector> subsectores = new ArrayList<>();

    /**
     * Relación uno-a-muchos con la entidad Emision.
     *.
     * Permite acceder a todas las emisiones registradas para este sector.
     * No aplica cascada, ya que las emisiones suelen manejarse por separado.
     */
    @Builder.Default
    @OneToMany(mappedBy = "sector", fetch = FetchType.LAZY)
    private List<Emision> emisiones = new ArrayList<>();
}
