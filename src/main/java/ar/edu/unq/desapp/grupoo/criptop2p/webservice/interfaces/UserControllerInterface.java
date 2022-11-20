package ar.edu.unq.desapp.grupoo.criptop2p.webservice.interfaces;

import ar.edu.unq.desapp.grupoo.criptop2p.service.dto.*;
import org.springframework.http.ResponseEntity;
import java.util.List;

public interface UserControllerInterface {
    ResponseEntity<List<UserInfoDTO>> allUsers();
    ResponseEntity<UserDTO> register(UserCreationDTO userCreationDTO);
    ResponseEntity<UserDTO> findUserById(Long id);
    ResponseEntity<IntentionDTO> offer(Long id, IntentionCreationDTO anIntentionDTO);
    ResponseEntity<Void> unregister(Long id);
    ResponseEntity<List<IntentionDTO>> activatedIntentionsOf(Long id);
    ResponseEntity<IntentionDTO> processIntention(Long userId, Long intentionId, String action);
    ResponseEntity<Form> intentionsBetween(Long id, String start, String end);
}
