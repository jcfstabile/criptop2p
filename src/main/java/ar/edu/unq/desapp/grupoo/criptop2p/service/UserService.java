package ar.edu.unq.desapp.grupoo.criptop2p.service;

import ar.edu.unq.desapp.grupoo.criptop2p.model.Intention;
import ar.edu.unq.desapp.grupoo.criptop2p.model.User;
import ar.edu.unq.desapp.grupoo.criptop2p.model.dto.IntentionDTO;
import ar.edu.unq.desapp.grupoo.criptop2p.model.exceptions.UserConstraintViolationException;
import ar.edu.unq.desapp.grupoo.criptop2p.model.exceptions.UserNotFoundException;
import ar.edu.unq.desapp.grupoo.criptop2p.persistence.IntentionRepository;
import ar.edu.unq.desapp.grupoo.criptop2p.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class UserService implements UserServiceInterface {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IntentionRepository intentionRepository;

    @Autowired
    private Validator validator;


    @Override
    @Transactional
    public User addUser(User user) {
        Set<ConstraintViolation<User>> userConstraintViolations = validator.validate(user);
        if ( ! userConstraintViolations.isEmpty() ) {
            List<String> errors = new ArrayList<>();
            for (ConstraintViolation<User> userConstraintViolation : userConstraintViolations){
                errors.add(userConstraintViolation.getMessage());
            }
            throw new UserConstraintViolationException(errors);
        }
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
        Intention anIntention = new Intention(getUser, anIntentionDTO.getCount(), anIntentionDTO.getPrice(), anIntentionDTO.getType(), anIntentionDTO.getCryptoName());
        return this.intentionRepository.save(anIntention);
    }
}
