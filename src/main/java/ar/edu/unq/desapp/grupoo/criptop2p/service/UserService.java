package ar.edu.unq.desapp.grupoo.criptop2p.service;

import ar.edu.unq.desapp.grupoo.criptop2p.integrations.BinanceIntegration;
import ar.edu.unq.desapp.grupoo.criptop2p.model.Intention;
import ar.edu.unq.desapp.grupoo.criptop2p.model.Status;
import ar.edu.unq.desapp.grupoo.criptop2p.model.StatusChangeErrorException;
import ar.edu.unq.desapp.grupoo.criptop2p.service.dto.*;
import ar.edu.unq.desapp.grupoo.criptop2p.service.exceptions.*;
import ar.edu.unq.desapp.grupoo.criptop2p.model.User;
import ar.edu.unq.desapp.grupoo.criptop2p.persistence.IntentionRepository;
import ar.edu.unq.desapp.grupoo.criptop2p.persistence.UserRepository;
import ar.edu.unq.desapp.grupoo.criptop2p.utils.ConvertDate;
import ar.edu.unq.desapp.grupoo.criptop2p.utils.FormatterDate;
import ar.edu.unq.desapp.grupoo.criptop2p.utils.InspectUser;
import ar.edu.unq.desapp.grupoo.criptop2p.utils.TypeIntentionDelivery;
import ar.edu.unq.desapp.grupoo.criptop2p.service.dto.QuotationDTO;
import ar.edu.unq.desapp.grupoo.criptop2p.webservice.mappers.IntentionMapper;
import ar.edu.unq.desapp.grupoo.criptop2p.webservice.mappers.UserMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
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

    @Autowired
    private InspectUser inspectUser;

    @Autowired
    private QuotationService quotationService;

    protected final Log logger = LogFactory.getLog(getClass());

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
        QuotationDTO quotation = new BinanceIntegration().priceOf(anIntentionDTO.getCryptoName());
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

    @Override
    public Formless intentionsBetween(Long id, String start, String end) {
        User getUser = this.userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        FormatterDate formatter = new FormatterDate();
        LocalDate startLocalDate = formatter.stringToDate(start);
        LocalDate endLocalDate = formatter.stringToDate(end);
        ConvertDate convert = new ConvertDate();
        Date startDate = convert.convertToDate(startLocalDate);
        Date endDate = convert.convertToDate(endLocalDate);
        return inspectUser.offersBetween(getUser, startDate, endDate);
    }

    @Transactional
    public IntentionDTO processIntention(Long userId, Long intentionId, String action) {
        IntentionDTO response = null;
        logger.info("User " + userId + " do " + action + " for intention " + intentionId);
        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        Intention intention = this.intentionRepository.findById(intentionId)
                .orElseThrow(() -> new IntentionNotFoundException(intentionId));

        try {
            switch (action) {
                case "accept" -> {
                    user.accept(intention, quotationService.priceOf(intention.getCrypto()));
                    response = intentionMapper.toIntentionDto(intention);
                }
//            case "delivery": intention.delivery(); break;
//            case "payment": intention.payment(); break;
                case "cancel" -> intention.cancel(user);
                default -> throw new NoValidActionErrorException(action);
            }
        } catch (InterruptedException e) {
            throw new InterruptedErrorException();
        } catch (StatusChangeErrorException e) {
            throw new StatusChangeNotAllowedRestException(e.status);
        }
        return response;
    }
}
