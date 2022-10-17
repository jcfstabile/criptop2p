package ar.edu.unq.desapp.grupoo.criptop2p.service;

import ar.edu.unq.desapp.grupoo.criptop2p.model.Intention;
import ar.edu.unq.desapp.grupoo.criptop2p.model.dto.IntentionDTO;
import ar.edu.unq.desapp.grupoo.criptop2p.model.dto.UserCreationDTO;
import ar.edu.unq.desapp.grupoo.criptop2p.model.dto.UserDTO;
import ar.edu.unq.desapp.grupoo.criptop2p.model.dto.UserInfoDTO;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface UserServiceInterface {
    Long addUser(UserCreationDTO userCreationDTO);
    UserDTO findByID(Long anId);

    Intention offer(Long anId, IntentionDTO anIntentionDTO);

    List<UserInfoDTO> findAll();

    void deleteUserById(Long anId);
}
