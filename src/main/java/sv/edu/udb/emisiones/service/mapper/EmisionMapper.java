/**
 * Mapper para conversión entre entidades y DTOs de Emisión.
 */
package sv.edu.udb.emisiones.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import sv.edu.udb.emisiones.controller.request.EmisionRequest;
import sv.edu.udb.emisiones.controller.response.EmisionResponse;
import sv.edu.udb.emisiones.domain.Emision;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmisionMapper {

    /** Convierte un request a entidad (sector y subsector se asignan luego). */
    @Mapping(target = "sector", ignore = true)
    @Mapping(target = "subsector", ignore = true)
    @Mapping(target = "id", ignore = true)
    Emision toEntity(EmisionRequest request);

    /** Convierte una entidad a DTO con nombres de sector y subsector. */
    @Mapping(source = "sector.nombre", target = "sectorNombre")
    @Mapping(source = "subsector.nombre", target = "subsectorNombre")
    EmisionResponse toResponse(Emision emision);

    /** Convierte una lista de entidades a lista de respuestas. */
    List<EmisionResponse> toResponseList(List<Emision> emisiones);
}
