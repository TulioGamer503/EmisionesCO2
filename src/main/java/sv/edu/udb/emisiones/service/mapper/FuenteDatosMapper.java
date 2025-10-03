package sv.edu.udb.emisiones.service.mapper;

import org.mapstruct.Mapper;
import sv.edu.udb.emisiones.controller.request.FuenteDatosRequest;
import sv.edu.udb.emisiones.controller.response.FuenteDatosResponse;
import sv.edu.udb.emisiones.domain.FuenteDatos;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FuenteDatosMapper {

    FuenteDatos toEntity(FuenteDatosRequest request);
    FuenteDatosResponse toResponse(FuenteDatos fuenteDatos);
    List<FuenteDatosResponse> toResponseList(List<FuenteDatos> fuentesDatos);
}