package ar.edu.unq.desapp.grupoo.criptop2p.service;

import ar.edu.unq.desapp.grupoo.criptop2p.service.dto.IntentionDTO;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface IntentionServiceInterface {
    IntentionDTO findById(Long id);

    List<IntentionDTO> intentions();

    List<IntentionDTO> intentionsWithState(String aState);

    void delete(Long id);

    Long add(IntentionDTO intentionDTO);
}
