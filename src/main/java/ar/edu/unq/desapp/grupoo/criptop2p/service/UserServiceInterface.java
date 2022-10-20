package ar.edu.unq.desapp.grupoo.criptop2p.service;

import ar.edu.unq.desapp.grupoo.criptop2p.service.dto.*;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface UserServiceInterface {
    Long addUser(UserCreationDTO userCreationDTO);
    UserDTO findByID(Long anId);

    IntentionDTO offer(Long anId, IntentionCreationDTO anIntentionDTO);

    List<UserInfoDTO> findAll();

    void deleteUserById(Long anId);
}
