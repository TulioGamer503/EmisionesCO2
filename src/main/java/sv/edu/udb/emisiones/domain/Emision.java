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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer anio;

    @Column(nullable = false)
    private Integer mes;

    @Column(name = "cantidad_tco2", nullable = false)
    private Double cantidadTCO2;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sector_id", nullable = false)
    private Sector sector;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subsector_id")
    private Subsector subsector;

    @Column(name = "fecha_registro")
    private LocalDate fechaRegistro;

    // Método para calcular reducción basado en la ecuación de Kaya
    public Double calcularReduccion(Emision emisionAnterior) {
        if (emisionAnterior == null || emisionAnterior.cantidadTCO2 == 0) {
            return 0.0;
        }
        return ((emisionAnterior.cantidadTCO2 - this.cantidadTCO2) / emisionAnterior.cantidadTCO2) * 100;
    }

    // Método para identificar el driver principal de la reducción
    public String identificarDriverReduccion() {
        // Implementación simplificada de la ecuación de Kaya
        return "Análisis de drivers (Población, GDP, Intensidad Energética, Mix Energético)";
    }
}