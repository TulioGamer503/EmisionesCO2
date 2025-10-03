package sv.edu.udb.emisiones.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import sv.edu.udb.emisiones.controller.request.SectorRequest;
import sv.edu.udb.emisiones.controller.response.SectorResponse;
import sv.edu.udb.emisiones.domain.Sector;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SectorMapper {

    Sector toEntity(SectorRequest request);

    @Mapping(source = "subsectores", target = "totalSubsectores")
    SectorResponse toResponse(Sector sector);

    List<SectorResponse> toResponseList(List<Sector> sectores);

    // Mapper personalizado para total de subsectores
    default Integer mapSubsectoresToTotal(List<?> subsectores) {
        return subsectores != null ? subsectores.size() : 0;
    }
}