package ar.edu.unq.desapp.grupoo.criptop2p.service;

import ar.edu.unq.desapp.grupoo.criptop2p.integrations.BinanceIntegration;
import ar.edu.unq.desapp.grupoo.criptop2p.model.Intention;
import ar.edu.unq.desapp.grupoo.criptop2p.model.Status;
import ar.edu.unq.desapp.grupoo.criptop2p.service.dto.*;
import ar.edu.unq.desapp.grupoo.criptop2p.service.exceptions.*;
import ar.edu.unq.desapp.grupoo.criptop2p.model.User;
import ar.edu.unq.desapp.grupoo.criptop2p.persistence.IntentionRepository;
import ar.edu.unq.desapp.grupoo.criptop2p.persistence.UserRepository;
import ar.edu.unq.desapp.grupoo.criptop2p.utils.TypeIntentionDelivery;
import ar.edu.unq.desapp.grupoo.criptop2p.webservice.Quotation;
import ar.edu.unq.desapp.grupoo.criptop2p.webservice.mappers.IntentionMapper;
import ar.edu.unq.desapp.grupoo.criptop2p.webservice.mappers.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.math.BigDecimal;
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
    private UserMapper userMapper;

    @Autowired
    private IntentionMapper intentionMapper;
    @Override
    @Transactional
    public Long addUser(UserCreationDTO userCreationDTO) {
        User user = userMapper.toUser(userCreationDTO);
        Set<ConstraintViolation<User>> userConstraintViolations = validator.validate(user);
        if ( ! userConstraintViolations.isEmpty() ) {
            List<String> errors = new ArrayList<>();
            for (ConstraintViolation<User> userConstraintViolation : userConstraintViolations){
                errors.add(userConstraintViolation.getMessage());
            }
            throw new UserConstraintViolationException(errors);
        }
        try {
            return this.userRepository.save(user).getId();
        } catch (DataIntegrityViolationException e) {
            throw new DataIncomingConflictException();
        } catch (IllegalStateException e) {
            throw new ServerCantHandleRequestNowException();
        }
    }

    @Override
    public UserDTO findByID(Long anId) {
        return userMapper.toUserDto(this.userRepository.findById(anId)
                .orElseThrow(() -> new UserNotFoundException(anId)));
    }

    @Override
    @Transactional
    public IntentionDTO offer(Long anId, IntentionCreationDTO anIntentionDTO){
        User user = this.userRepository.findById(anId)
                .orElseThrow(() -> new UserNotFoundException(anId));
        Intention anIntention = this.intentionMapper.toIntention(user, anIntentionDTO);
        IntentionDTO intentionDTO = intentionMapper.toIntentionDto(this.intentionRepository.save(anIntention));
        Quotation quotation = new BinanceIntegration().priceOf(anIntentionDTO.getCryptoName());
        BigDecimal aCurrentPrice = BigDecimal.valueOf(Float.parseFloat(quotation.getPrice()));
        user.offer(anIntentionDTO.getCount(), anIntentionDTO.getPrice(), new TypeIntentionDelivery().get(anIntentionDTO.getType()),anIntentionDTO.getCryptoName(), aCurrentPrice);
        this.userRepository.save(user);
        if(anIntention.hasStatus(Status.CANCELEDBYSYSTEM)){
            throw new DifferenceWithCurrentPriceException(anIntention.getPrice(), aCurrentPrice);
        }
        return intentionDTO;
    }

    @Override
    public List<UserInfoDTO> findAll(){
        List<UserInfoDTO> users = new ArrayList<>();
        for (User user : this.userRepository.findAll()){
            users.add(userMapper.toUserInfoDto(user));
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

    @Override
    @Transactional
    public List<IntentionDTO> activatedIntentionsOf(Long anId) {
        User getUser = this.userRepository.findById(anId)
                .orElseThrow(() -> new UserNotFoundException(anId));
        return getUser.activatedIntentions().map(intention -> intentionMapper.toIntentionDto(intention)).toList();
    }
}
