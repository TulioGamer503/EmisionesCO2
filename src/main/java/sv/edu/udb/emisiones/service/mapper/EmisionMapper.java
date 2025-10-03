package sv.edu.udb.emisiones.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import sv.edu.udb.emisiones.controller.request.EmisionRequest;
import sv.edu.udb.emisiones.controller.response.EmisionResponse;
import sv.edu.udb.emisiones.domain.Emision;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmisionMapper {

    @Mapping(target = "sector", ignore = true)
    @Mapping(target = "subsector", ignore = true)
    @Mapping(target = "id", ignore = true)
    Emision toEntity(EmisionRequest request);

    @Mapping(source = "sector.nombre", target = "sectorNombre")
    @Mapping(source = "subsector.nombre", target = "subsectorNombre")
    EmisionResponse toResponse(Emision emision);

    List<EmisionResponse> toResponseList(List<Emision> emisiones);
}