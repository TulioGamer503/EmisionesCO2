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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombre;

    @Column(length = 500)
    private String descripcion;

    @Builder.Default
    @OneToMany(mappedBy = "sector", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Subsector> subsectores = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "sector", fetch = FetchType.LAZY)
    private List<Emision> emisiones = new ArrayList<>();
}