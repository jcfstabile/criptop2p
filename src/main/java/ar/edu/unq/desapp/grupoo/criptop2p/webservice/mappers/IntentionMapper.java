package ar.edu.unq.desapp.grupoo.criptop2p.webservice.mappers;

import ar.edu.unq.desapp.grupoo.criptop2p.model.Intention;
import ar.edu.unq.desapp.grupoo.criptop2p.model.User;
import ar.edu.unq.desapp.grupoo.criptop2p.service.dto.IntentionCreationDTO;
import ar.edu.unq.desapp.grupoo.criptop2p.service.dto.IntentionDTO;
import ar.edu.unq.desapp.grupoo.criptop2p.utils.TypeIntentionDelivery;
import org.springframework.stereotype.Component;

@Component
public class IntentionMapper {
    public IntentionDTO toIntentionDto(Intention intention) {
        return new IntentionDTO(intention.getId(), intention.getCount(), intention.getPrice(), intention.getType(), intention.getCrypto(), intention.getOffered().getId(), intention.getStatus());
    }
    public Intention toIntention(User user, IntentionCreationDTO intentionCreationDTO){
        return new Intention(user, intentionCreationDTO.getCount(), intentionCreationDTO.getPrice(), new TypeIntentionDelivery().get(intentionCreationDTO.getType()), intentionCreationDTO.getCryptoName());
    }

    public Intention toIntention(IntentionDTO intentionDTO) {
        return new Intention(null, intentionDTO.getCount(), intentionDTO.getPrice(), new TypeIntentionDelivery().get(intentionDTO.getType().name()), intentionDTO.getCryptoName());
    }

    public IntentionDTO toIntentionDBDto(Intention intention) {
        return new IntentionDTO(intention.getId(), intention.getCount(), intention.getPrice(), intention.getType(), intention.getCrypto(), null, intention.getStatus());
    }
}
