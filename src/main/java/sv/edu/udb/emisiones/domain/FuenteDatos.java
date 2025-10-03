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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    private String organismo;

    private String url;

    @Column(name = "es_confiable")
    @Builder.Default
    private Boolean esConfiable = true;

    @Column(name = "metodologia")
    private String metodologia;
}