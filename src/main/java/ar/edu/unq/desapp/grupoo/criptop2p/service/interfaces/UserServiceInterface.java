package ar.edu.unq.desapp.grupoo.criptop2p.service.interfaces;

import ar.edu.unq.desapp.grupoo.criptop2p.service.dto.*;
import ar.edu.unq.desapp.grupoo.criptop2p.service.exceptions.StatusChangeErrorException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface UserServiceInterface {
    Long addUser(UserCreationDTO userCreationDTO);
    UserDTO findByID(Long anId);

    IntentionDTO offer(Long anId, IntentionCreationDTO anIntentionDTO) throws StatusChangeErrorException;

    List<UserInfoDTO> findAll();

    void deleteUserById(Long anId);

    List<IntentionDTO> activatedIntentionsOf(Long id);

    FormDTO intentionsBetween(Long id, String start, String end);
}
