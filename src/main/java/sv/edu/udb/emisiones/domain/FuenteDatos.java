/**
 * Entidad que representa una fuente de datos utilizada para registrar o validar
 * información relacionada con las emisiones de CO₂.
 *,
 * Cada fuente de datos contiene información sobre su nombre, organismo responsable,
 * URL de referencia, nivel de confiabilidad y la metodología empleada para recopilar los datos.
 */
package sv.edu.udb.emisiones.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "fuentes_datos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FuenteDatos {

    /** Identificador único de la fuente de datos (clave primaria autogenerada). */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Nombre oficial de la fuente de datos (por ejemplo: Banco Mundial, IPCC, etc.). */
    @Column(nullable = false)
    private String nombre;

    /** Nombre del organismo o institución que provee los datos. */
    private String organismo;

    /** URL o enlace de referencia donde pueden consultarse los datos. */
    private String url;

    /**
     * Indica si la fuente de datos es considerada confiable.
     * Por defecto, todas las fuentes se marcan como confiables (true).
     */
    @Column(name = "es_confiable")
    @Builder.Default
    private Boolean esConfiable = true;

    /** Breve descripción de la metodología utilizada por la fuente para recolectar los datos. */
    @Column(name = "metodologia")
    private String metodologia;
}
