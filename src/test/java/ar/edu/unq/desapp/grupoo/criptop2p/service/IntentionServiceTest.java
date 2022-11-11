package ar.edu.unq.desapp.grupoo.criptop2p.service;

import ar.edu.unq.desapp.grupoo.criptop2p.service.dto.IntentionDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@DisplayName("Intention Service Tests")
@SpringBootTest
@Transactional
@ExtendWith(MockitoExtension.class)
class IntentionServiceTest {
    @Autowired
    IntentionService sut;

    @Test
    void testIntentionServiceReturnsAnIntention(){
        //IntentionDTO response = sut.findById(1L);

    }

}
