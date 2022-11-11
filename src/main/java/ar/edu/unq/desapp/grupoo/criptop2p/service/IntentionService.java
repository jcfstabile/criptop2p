package ar.edu.unq.desapp.grupoo.criptop2p.service;

import ar.edu.unq.desapp.grupoo.criptop2p.model.Intention;
import ar.edu.unq.desapp.grupoo.criptop2p.model.Status;
import ar.edu.unq.desapp.grupoo.criptop2p.persistence.IntentionRepository;
import ar.edu.unq.desapp.grupoo.criptop2p.service.dto.IntentionCreationDTO;
import ar.edu.unq.desapp.grupoo.criptop2p.service.dto.IntentionDTO;
import ar.edu.unq.desapp.grupoo.criptop2p.service.dto.UserDTO;
import ar.edu.unq.desapp.grupoo.criptop2p.service.exceptions.*;
import ar.edu.unq.desapp.grupoo.criptop2p.webservice.mappers.IntentionMapper;
import ar.edu.unq.desapp.grupoo.criptop2p.webservice.mappers.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Service
public class IntentionService implements IntentionServiceInterface{

    @Autowired
    private UserService userService;
    @Autowired
    private IntentionMapper intentionMapper;

    @Autowired
    private UserMapper userMapper;

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
        List<IntentionDTO> intentions = new ArrayList<>();
        for (Intention intention : this.intentionRepository.findAll()){
            intentions.add(intentionMapper.toIntentionDto(intention));
        }
        return intentions;
    }

    @Override
    public List<IntentionDTO> intentionsWithState(String aState) {
        Status st;
        try{
            st = Status.valueOf(aState);
        }
        catch(IllegalArgumentException ex){
            throw new IncorrectStatusException(aState);
        }

        List<IntentionDTO> intentions = new ArrayList<>();
        for (Intention intention : this.intentionRepository.findAll()){
            if(intention.hasStatus(st)){
                intentions.add(intentionMapper.toIntentionDto(intention));
            }
        }
        return intentions;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Intention intention = this.intentionRepository.findById(id)
                .orElseThrow(() -> new IntentionNotFoundException(id));
        this.intentionRepository.deleteById(intention.getId());
    }

    @Override
    @Transactional
    public IntentionDTO add(IntentionCreationDTO intentionDTO, UserDTO userDTO) {
        try {
            return this.userService.offer(userDTO.getId(), intentionDTO);
        } catch (DataIntegrityViolationException e) {
            throw new DataIncomingConflictException();
        } catch (IllegalStateException e) {
            throw new ServerCantHandleRequestNowException();
        }
    }
}
