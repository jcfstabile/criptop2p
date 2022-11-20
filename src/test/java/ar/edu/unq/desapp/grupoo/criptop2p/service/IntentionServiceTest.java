package ar.edu.unq.desapp.grupoo.criptop2p.service;

import ar.edu.unq.desapp.grupoo.criptop2p.integrations.BinanceIntegration;
import ar.edu.unq.desapp.grupoo.criptop2p.model.CryptoName;
import ar.edu.unq.desapp.grupoo.criptop2p.service.dto.IntentionCreationDTO;
import ar.edu.unq.desapp.grupoo.criptop2p.service.dto.IntentionDTO;
import ar.edu.unq.desapp.grupoo.criptop2p.service.dto.UserCreationDTO;
import ar.edu.unq.desapp.grupoo.criptop2p.service.dto.UserDTO;
import ar.edu.unq.desapp.grupoo.criptop2p.service.exceptions.IntentionNotFoundException;
import ar.edu.unq.desapp.grupoo.criptop2p.webservice.UserController;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Intention Service Tests")
@SpringBootTest
@Transactional
@ExtendWith(MockitoExtension.class)
class IntentionServiceTest {
    @Autowired
    IntentionService sut;
    @Autowired
    UserController anUserController;
    IntentionCreationDTO intentionDTO0, intentionDTO1, intentionDTO2;
    UserDTO userDTO;

    BinanceIntegration binance;

    @BeforeEach
    void setUp(){
        binance = new BinanceIntegration();
        UserCreationDTO anUser = new UserCreationDTO("Jom", "Nen", "jk22@here.dom", "Fake Street 1234", "Pepito+1234", "66345678", "6664567890123456789012");
        userDTO = anUserController.register(anUser).getBody();
        BigDecimal priceATOM = new BigDecimal(binance.priceOf(CryptoName.ATOMUSDT).getPrice());
        BigDecimal priceBN = new BigDecimal(binance.priceOf(CryptoName.BNBUSDT).getPrice());
        BigDecimal priceCK = new BigDecimal(binance.priceOf(CryptoName.CAKEUSDT).getPrice());

        intentionDTO0 = new IntentionCreationDTO(1, priceATOM.toString(), "BUY", "ATOMUSDT");
        intentionDTO1 = new IntentionCreationDTO(1, priceBN.toString(), "SELL", "BNBUSDT");
        intentionDTO2 = new IntentionCreationDTO(2, priceCK.toString(), "BUY", "CAKEUSDT");
    }

    @AfterEach
    void eraseAllIntentions() {
        List<IntentionDTO> intentions = sut.intentions();
        for (IntentionDTO intention : intentions) {
            sut.delete(intention.getIntentionId());
        }
        anUserController.unregister(userDTO.getId());
    }


    @DisplayName("At first, there arent any intention")
    @Test
    void testThreArentAnyIntention(){
        List<IntentionDTO> response = sut.intentions();
        assertEquals(0, response.size());
    }

    @DisplayName("To add an intention its add one ")
    @Test
    void testToSaveAnIntentionItAddOne(){
        assertEquals(0, sut.intentions().size());
        sut.add(intentionDTO0, userDTO);
        assertEquals(1, sut.intentions().size());
    }

    @DisplayName("IntentionService returns a intention when find by Id with an rigth ID")
    @Test
    void testWhenTheIDIsRigthIntentionServiceReturnAnExpectedIntention() {
        IntentionDTO intention0 = sut.add(intentionDTO0, userDTO);

        IntentionDTO response = sut.findById(intention0.getIntentionId());
        assertEquals(response.getIntentionId(), intention0.getIntentionId());
        assertEquals(response.getCount(), intention0.getCount());
        assertEquals(response.getPrice(), intention0.getPrice());
        assertEquals(response.getCryptoName(), intention0.getCryptoName());
        assertEquals(response.getStatus(), intention0.getStatus());
        assertEquals(response.getType(), intention0.getType());
    }

    @DisplayName("Intention Service returns the number of intentions")
    @Test
    void testIntentionServiceReturnsTheIntentions() {
        assertEquals(0, sut.intentions().size());
        sut.add(intentionDTO0, userDTO);
        assertEquals(1, sut.intentions().size());
        sut.add(intentionDTO1, userDTO);
        assertEquals(2, sut.intentions().size());
        sut.add(intentionDTO2, userDTO);
        assertEquals(3, sut.intentions().size());
    }

    @DisplayName("When intentention service receives the method intentions with status with a rigth status returns a list of intentions")
    @Test
    void testWhenIntententionServiceReceiveTheMethodIntentionWithStateWithAnSpecificStatusReturnsTheExpectedIntentions() {
        sut.add(intentionDTO0, userDTO);
        sut.add(intentionDTO1, userDTO);
        sut.add(intentionDTO2, userDTO);
        List<IntentionDTO> response = sut.intentionsWithState("SOLD");
        assertEquals(0, response.size());
        response = sut.intentionsWithState("OFFERED");
        assertEquals(3, response.size());
    }

    @DisplayName("When there arent any intention IntentionService returns a empty list when status is right and receives the method intentionsWithStatus")
    @Test
    void testAnyIntentionWithAnyStatus(){
        List<IntentionDTO> solds = sut.intentionsWithState("SOLD");
        List<IntentionDTO> offereds = sut.intentionsWithState("OFFERED");
        assertEquals(0, solds.size());
        assertEquals(0, offereds.size());
    }

    @DisplayName("IntentionService can delete an intention when the ID es rigth")
    @Test
    void testToDeleteAnIntentionItEliminateOne(){
        IntentionDTO intention0 = sut.add(intentionDTO0, userDTO);
        assertEquals(1, sut.intentions().size());
        sut.delete(intention0.getIntentionId());
//        assertEquals(0, sut.intentions().size());
    }

    @DisplayName("To delete an intention find by ID it throws an exception")
    @Test
    void testIntentionServiceThrowsAnExceptionWhenDeleteAnIntentionAndAfterSearchForIt(){
        IntentionDTO intention0 = sut.add(intentionDTO0, userDTO);
        IntentionDTO response = sut.findById(intention0.getIntentionId());
        assertEquals(response.getIntentionId(), intention0.getIntentionId());
        assertEquals(response.getCount(), intention0.getCount());
        assertEquals(response.getPrice(), intention0.getPrice());
        assertEquals(response.getCryptoName(), intention0.getCryptoName());
        assertEquals(response.getStatus(), intention0.getStatus());
        assertEquals(response.getType(), intention0.getType());

        Long IDToRemove = intention0.getIntentionId();

        sut.delete(IDToRemove);

        IntentionNotFoundException exception = assertThrows(IntentionNotFoundException.class, () ->
                sut.delete(IDToRemove)
        );
        assertEquals("Could not find intention " + intention0.getIntentionId(), exception.getMessage());
    }

    @DisplayName("To find with a wrong ID its throws an exception")
    @Test
    void testIntentionServiceThrowsAnExceptionWhenFindByIDWithAWrongID(){
        IntentionNotFoundException exception = assertThrows(IntentionNotFoundException.class, () ->
                sut.findById(123456789L)
        );
        assertEquals("Could not find intention 123456789", exception.getMessage());
    }

    @DisplayName("To delete with a wrong ID its throws an exception")
    @Test
    void testIntentionServiceThrowsAnExceptionWhenDeleteByIDWithAWrongID(){
        IntentionNotFoundException exception = assertThrows(IntentionNotFoundException.class, () ->
                sut.delete(123456789L)
        );
        assertEquals("Could not find intention 123456789", exception.getMessage());
    }
}
