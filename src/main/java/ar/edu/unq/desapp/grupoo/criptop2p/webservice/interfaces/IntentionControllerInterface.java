package ar.edu.unq.desapp.grupoo.criptop2p.webservice.interfaces;

import ar.edu.unq.desapp.grupoo.criptop2p.service.dto.IntentionCreationDTO;
import ar.edu.unq.desapp.grupoo.criptop2p.service.dto.IntentionDTO;
import ar.edu.unq.desapp.grupoo.criptop2p.service.dto.UserDTO;
import org.springframework.http.ResponseEntity;
import java.util.List;

public interface IntentionControllerInterface {
    ResponseEntity<IntentionDTO> intentionById(Long id);
    ResponseEntity<List<IntentionDTO>> intentions();
    ResponseEntity<List<IntentionDTO>> intentionsWithState(String aState);
    void delete(Long id);
    ResponseEntity<IntentionDTO> add(IntentionCreationDTO intentionDTO, UserDTO userDTO);
}
