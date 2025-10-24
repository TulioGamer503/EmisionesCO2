/**
 * Mapper para conversión entre entidades y DTOs de Sector.
 */
package sv.edu.udb.emisiones.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import sv.edu.udb.emisiones.controller.request.SectorRequest;
import sv.edu.udb.emisiones.controller.response.SectorResponse;
import sv.edu.udb.emisiones.domain.Sector;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SectorMapper {

    /** Convierte un request a entidad. */
    Sector toEntity(SectorRequest request);

    /** Convierte una entidad a DTO e incluye el total de subsectores. */
    @Mapping(source = "subsectores", target = "totalSubsectores")
    SectorResponse toResponse(Sector sector);

    /** Convierte una lista de entidades a lista de DTOs. */
    List<SectorResponse> toResponseList(List<Sector> sectores);

    /** Calcula el total de subsectores asociados a un sector. */
    default Integer mapSubsectoresToTotal(List<?> subsectores) {
        return subsectores != null ? subsectores.size() : 0;
    }
}
