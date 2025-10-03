package sv.edu.udb.emisiones.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import sv.edu.udb.emisiones.controller.response.AnalisisComparativoResponse;
import sv.edu.udb.emisiones.controller.response.AnalisisVariacionResponse;
import sv.edu.udb.emisiones.controller.response.ProyeccionResponse;
import sv.edu.udb.emisiones.controller.response.ReporteConsistenciaResponse;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface AnalisisMapper {

    // Método para crear AnalisisVariacionResponse con fecha de generación
    default AnalisisVariacionResponse toAnalisisVariacionResponse() {
        return AnalisisVariacionResponse.builder()
                .fechaGeneracion(LocalDateTime.now())
                .build();
    }

    // Método para crear AnalisisComparativoResponse con fecha de comparación
    default AnalisisComparativoResponse toAnalisisComparativoResponse() {
        return AnalisisComparativoResponse.builder()
                .fechaComparacion(java.time.LocalDate.now())
                .build();
    }

    // Método para crear ProyeccionResponse con fecha de proyección
    default ProyeccionResponse toProyeccionResponse() {
        return ProyeccionResponse.builder()
                .fechaProyeccion(java.time.LocalDate.now())
                .build();
    }

    // Método para crear ReporteConsistenciaResponse con fecha de verificación
    default ReporteConsistenciaResponse toReporteConsistenciaResponse() {
        return ReporteConsistenciaResponse.builder()
                .fechaVerificacion(LocalDateTime.now())
                .build();
    }
}