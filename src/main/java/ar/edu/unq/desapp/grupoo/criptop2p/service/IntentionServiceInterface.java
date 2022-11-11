package ar.edu.unq.desapp.grupoo.criptop2p.service;

import ar.edu.unq.desapp.grupoo.criptop2p.service.dto.IntentionCreationDTO;
import ar.edu.unq.desapp.grupoo.criptop2p.service.dto.IntentionDTO;
import ar.edu.unq.desapp.grupoo.criptop2p.service.dto.UserCreationDTO;
import ar.edu.unq.desapp.grupoo.criptop2p.service.dto.UserDTO;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface IntentionServiceInterface {
    IntentionDTO findById(Long id);

    List<IntentionDTO> intentions();

    List<IntentionDTO> intentionsWithState(String aState);

    void delete(Long id);

    IntentionDTO add(IntentionCreationDTO intentionDTO, UserDTO userDTO);
}
