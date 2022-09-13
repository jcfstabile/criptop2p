package ar.edu.unq.desapp.grupoo.criptop2p.service;

import ar.edu.unq.desapp.grupoo.criptop2p.model.Intention;
import ar.edu.unq.desapp.grupoo.criptop2p.model.User;
import ar.edu.unq.desapp.grupoo.criptop2p.model.dto.IntentionDTO;
import ar.edu.unq.desapp.grupoo.criptop2p.model.exceptions.UserNotFoundException;
import ar.edu.unq.desapp.grupoo.criptop2p.persistence.IntentionRepository;
import ar.edu.unq.desapp.grupoo.criptop2p.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService implements UserServiceInterface {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IntentionRepository intentionRepository;

    @Override
    @Transactional
    public User addUser(User user) {
        return this.userRepository.save(user);
    }


    @Override
    public User findByID(Long anId) {
        return this.userRepository.findById(anId)
                .orElseThrow(() -> new UserNotFoundException(anId));
    }

    @Override
    @Transactional
    public Intention offer(Long anId, IntentionDTO anIntentionDTO){
        User getUser = this.userRepository.findById(anId)
                .orElseThrow(() -> new UserNotFoundException(anId));
        Intention anIntention = new Intention(getUser, anIntentionDTO.count, anIntentionDTO.price, anIntentionDTO.crypto);
        return this.intentionRepository.save(anIntention);
    }
}
