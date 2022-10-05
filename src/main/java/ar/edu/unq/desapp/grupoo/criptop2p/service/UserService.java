package ar.edu.unq.desapp.grupoo.criptop2p.service;

import ar.edu.unq.desapp.grupoo.criptop2p.model.Intention;
import ar.edu.unq.desapp.grupoo.criptop2p.model.dto.UserInfoDTO;
import ar.edu.unq.desapp.grupoo.criptop2p.model.exceptions.DataIncomingConflictException;
import ar.edu.unq.desapp.grupoo.criptop2p.model.User;
import ar.edu.unq.desapp.grupoo.criptop2p.model.dto.IntentionDTO;
import ar.edu.unq.desapp.grupoo.criptop2p.model.exceptions.UserConstraintViolationException;
import ar.edu.unq.desapp.grupoo.criptop2p.model.exceptions.UserNotFoundException;
import ar.edu.unq.desapp.grupoo.criptop2p.persistence.IntentionRepository;
import ar.edu.unq.desapp.grupoo.criptop2p.persistence.UserRepository;
import ar.edu.unq.desapp.grupoo.criptop2p.webservice.mappers.UserMapper;
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

    @Autowired
    private UserMapper mapper;
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
        try {
            return this.userRepository.save(user);
        } catch (Exception e) {
            throw new DataIncomingConflictException();
        }
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

    @Override
    public List<UserInfoDTO> findAll(){
        List<UserInfoDTO> users = new ArrayList<>();
        for (User user : this.userRepository.findAll()){
            users.add(mapper.toUserInfoDto(user));
        }
        return users;
    }

    @Override
    @Transactional
    public void deleteUserById(Long anId){
        User user = this.userRepository.findById(anId)
                .orElseThrow(() -> new UserNotFoundException(anId));
        this.userRepository.deleteById(user.getId());
    }
}
