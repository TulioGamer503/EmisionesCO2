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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String tipo;

    @Column(name = "intensidad_emisiones")
    private String intensidad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sector_id")
    private Sector sector;
}