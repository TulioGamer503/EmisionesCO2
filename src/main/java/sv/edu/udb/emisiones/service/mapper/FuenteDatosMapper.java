/**
 * Mapper para conversión entre entidades y DTOs de FuenteDatos.
 */
package sv.edu.udb.emisiones.service.mapper;

import org.mapstruct.Mapper;
import sv.edu.udb.emisiones.controller.request.FuenteDatosRequest;
import sv.edu.udb.emisiones.controller.response.FuenteDatosResponse;
import sv.edu.udb.emisiones.domain.FuenteDatos;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FuenteDatosMapper {

    /** Convierte un request a entidad. */
    FuenteDatos toEntity(FuenteDatosRequest request);

    /** Convierte una entidad a DTO. */
    FuenteDatosResponse toResponse(FuenteDatos fuenteDatos);

    /** Convierte una lista de entidades a lista de DTOs. */
    List<FuenteDatosResponse> toResponseList(List<FuenteDatos> fuentesDatos);
}
