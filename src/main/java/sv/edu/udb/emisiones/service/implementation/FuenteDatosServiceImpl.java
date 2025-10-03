package sv.edu.udb.emisiones.service.implementation;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sv.edu.udb.emisiones.controller.request.FuenteDatosRequest;
import sv.edu.udb.emisiones.controller.response.FuenteDatosResponse;
import sv.edu.udb.emisiones.domain.FuenteDatos;
import sv.edu.udb.emisiones.repository.FuenteDatosRepository;
import sv.edu.udb.emisiones.service.FuenteDatosService;
import sv.edu.udb.emisiones.service.mapper.FuenteDatosMapper;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FuenteDatosServiceImpl implements FuenteDatosService {

    private final FuenteDatosRepository fuenteDatosRepository;
    private final FuenteDatosMapper fuenteDatosMapper;

    @Override
    @Transactional(readOnly = true)
    public List<FuenteDatosResponse> findAll() {
        return fuenteDatosMapper.toResponseList(fuenteDatosRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public FuenteDatosResponse findById(Long id) {
        FuenteDatos fuente = fuenteDatosRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Fuente de datos no encontrada con id: " + id));
        return fuenteDatosMapper.toResponse(fuente);
    }

    @Override
    @Transactional
    public FuenteDatosResponse save(FuenteDatosRequest request) {
        FuenteDatos fuente = fuenteDatosMapper.toEntity(request);
        FuenteDatos saved = fuenteDatosRepository.save(fuente);
        return fuenteDatosMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public FuenteDatosResponse update(Long id, FuenteDatosRequest request) {
        FuenteDatos existing = fuenteDatosRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Fuente de datos no encontrada con id: " + id));

        existing.setNombre(request.getNombre());
        existing.setOrganismo(request.getOrganismo());
        existing.setUrl(request.getUrl());
        existing.setMetodologia(request.getMetodologia());
        existing.setEsConfiable(request.getEsConfiable());

        FuenteDatos updated = fuenteDatosRepository.save(existing);
        return fuenteDatosMapper.toResponse(updated);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!fuenteDatosRepository.existsById(id)) {
            throw new EntityNotFoundException("Fuente de datos no encontrada con id: " + id);
        }
        fuenteDatosRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FuenteDatosResponse> findByOrganismo(String organismo) {
        return fuenteDatosMapper.toResponseList(fuenteDatosRepository.findByOrganismo(organismo));
    }

    @Override
    @Transactional(readOnly = true)
    public List<FuenteDatosResponse> findByEsConfiableTrue() {
        return fuenteDatosMapper.toResponseList(fuenteDatosRepository.findByEsConfiableTrue());
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Long> countByOrganismo() {
        List<Object[]> resultados = fuenteDatosRepository.countByOrganismo();
        return resultados.stream()
                .collect(Collectors.toMap(
                        result -> (String) result[0],
                        result -> (Long) result[1]
                ));
    }
}