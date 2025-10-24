/**
 * Define operaciones CRUD y consultas sobre fuentes de datos.
 */
package sv.edu.udb.emisiones.service;

import sv.edu.udb.emisiones.controller.request.FuenteDatosRequest;
import sv.edu.udb.emisiones.controller.response.FuenteDatosResponse;

import java.util.List;
import java.util.Map;

public interface FuenteDatosService {

    /** Listar todas las fuentes de datos. */
    List<FuenteDatosResponse> findAll();

    /** Buscar fuente de datos por ID. */
    FuenteDatosResponse findById(Long id);

    /** Crear nueva fuente de datos. */
    FuenteDatosResponse save(FuenteDatosRequest request);

    /** Actualizar una fuente de datos existente. */
    FuenteDatosResponse update(Long id, FuenteDatosRequest request);

    /** Eliminar fuente de datos por ID. */
    void delete(Long id);

    /** Buscar fuentes de datos por organismo. */
    List<FuenteDatosResponse> findByOrganismo(String organismo);

    /** Listar solo fuentes marcadas como confiables. */
    List<FuenteDatosResponse> findByEsConfiableTrue();

    /** Contar fuentes agrupadas por organismo. */
    Map<String, Long> countByOrganismo();
}
