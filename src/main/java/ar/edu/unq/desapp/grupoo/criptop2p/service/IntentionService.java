package ar.edu.unq.desapp.grupoo.criptop2p.service;

import ar.edu.unq.desapp.grupoo.criptop2p.model.Intention;
import ar.edu.unq.desapp.grupoo.criptop2p.model.Status;
import ar.edu.unq.desapp.grupoo.criptop2p.persistence.IntentionRepository;
import ar.edu.unq.desapp.grupoo.criptop2p.service.dto.IntentionDTO;
import ar.edu.unq.desapp.grupoo.criptop2p.service.exceptions.*;
import ar.edu.unq.desapp.grupoo.criptop2p.webservice.mappers.IntentionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public class IntentionService implements IntentionServiceInterface{

    @Autowired
    private IntentionMapper intentionMapper;
    @Autowired
    private IntentionRepository intentionRepository;

    @Autowired
    private Validator validator;

    @Override
    public IntentionDTO findById(Long anId) {
        return  intentionMapper.toIntentionDBDto(this.intentionRepository.findById(anId)
                .orElseThrow(() -> new IntentionNotFoundException(anId)));
    }

    @Override
    public List<IntentionDTO> intentions() {
        return this.intentionsStream().map(
                intention -> intentionMapper.toIntentionDto(intention)
        ).toList();
    }

    @Override
    @Transactional
    public List<IntentionDTO> intentionsWithState(String aState) {
        if(this.isCorrectState(aState)){
            throw new IncorrectStatusException(aState);
        }
        return this.intentionsStream().filter(intention -> intention.hasStatus(Status.valueOf(aState))).map(intention -> intentionMapper.toIntentionDto(intention)).toList();
    }

    @Override
    public void delete(Long id) {
        Intention intention = this.intentionRepository.findById(id)
                .orElseThrow(() -> new IntentionNotFoundException(id));
        this.intentionRepository.deleteById(intention.getId());
    }

    @Override
    @Transactional
    public Long add(IntentionDTO intentionDTO) {
        Intention intention = intentionMapper.toIntention(intentionDTO);
        Set<ConstraintViolation<Intention>> intentionConstraintViolations = validator.validate(intention);
        if (!intentionConstraintViolations.isEmpty() ) {
            List<String> errors = new ArrayList<>();
            for (ConstraintViolation<Intention> intentionConstraintViolation : intentionConstraintViolations){
                errors.add(intentionConstraintViolation.getMessage());
            }
            throw new IntentionConstraintViolationException(errors);
        }
        try {
            return this.intentionRepository.save(intention).getId();
        } catch (DataIntegrityViolationException e) {
            throw new DataIncomingConflictException();
        } catch (IllegalStateException e) {
            throw new ServerCantHandleRequestNowException();
        }
    }

    private boolean isCorrectState(String aState) {
        return Arrays.stream(Status.values()).anyMatch(status -> status.equals(Status.valueOf(aState)));
    }

    private Stream<Intention> intentionsStream(){
        return StreamSupport.stream(this.intentionRepository.findAll().spliterator(), false);
    }
}
