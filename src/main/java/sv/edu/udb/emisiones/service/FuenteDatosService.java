package sv.edu.udb.emisiones.service;

import sv.edu.udb.emisiones.controller.request.FuenteDatosRequest;
import sv.edu.udb.emisiones.controller.response.FuenteDatosResponse;

import java.util.List;
import java.util.Map;

public interface FuenteDatosService {
    List<FuenteDatosResponse> findAll();
    FuenteDatosResponse findById(Long id);
    FuenteDatosResponse save(FuenteDatosRequest request);
    FuenteDatosResponse update(Long id, FuenteDatosRequest request);
    void delete(Long id);
    List<FuenteDatosResponse> findByOrganismo(String organismo);
    List<FuenteDatosResponse> findByEsConfiableTrue();
    Map<String, Long> countByOrganismo();
}