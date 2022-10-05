package ar.edu.unq.desapp.grupoo.criptop2p.webservice.mappers;

import ar.edu.unq.desapp.grupoo.criptop2p.model.Intention;
import ar.edu.unq.desapp.grupoo.criptop2p.model.dto.IntentionDTO;
import org.springframework.stereotype.Component;

@Component
//TO DO: Finish toIntention and make an interface or abstract class
public class IntentionMapper {
    public IntentionDTO toDto(Intention anIntention) {
        return new IntentionDTO( anIntention.getCount(), anIntention.getPrice(), anIntention.getType(), anIntention.getCrypto());
    }

    public Intention toIntention(){
        return new Intention();
    }
}
