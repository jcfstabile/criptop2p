package ar.edu.unq.desapp.grupoo.criptop2p.service;

import ar.edu.unq.desapp.grupoo.criptop2p.model.Intention;
import ar.edu.unq.desapp.grupoo.criptop2p.model.User;
import ar.edu.unq.desapp.grupoo.criptop2p.model.dto.IntentionDTO;
import org.springframework.stereotype.Service;


@Service
public interface UserServiceInterface {
    User addUser(User user);
    User findByID(Long anId);

    Intention offer(Long anId, IntentionDTO anIntentionDTO);
}
