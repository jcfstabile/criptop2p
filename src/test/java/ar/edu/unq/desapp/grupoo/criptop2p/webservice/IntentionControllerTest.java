package ar.edu.unq.desapp.grupoo.criptop2p.webservice;

import ar.edu.unq.desapp.grupoo.criptop2p.integrations.BinanceIntegration;
import ar.edu.unq.desapp.grupoo.criptop2p.model.*;
import ar.edu.unq.desapp.grupoo.criptop2p.service.QuotationService;
import ar.edu.unq.desapp.grupoo.criptop2p.service.dto.*;
import lombok.SneakyThrows;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.math.BigDecimal;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("Intention Controller Tests")
@SpringBootTest
@ExtendWith(MockitoExtension.class)
class IntentionControllerTest {
    @Autowired
    IntentionController sut;
    @Autowired
    UserRestController anUserRestController;
    IntentionDTO intention0;
    IntentionDTO intention1;

    UserDTO userDTO;

    BinanceIntegration binance;

    @SneakyThrows
    @BeforeEach
    void setUp(){
        binance = new BinanceIntegration();
        UserCreationDTO anUser = new UserCreationDTO("Jom", "Nen", "jk22@here.dom", "Fake Street 1234", "Pepito+1234", "66345678", "6664567890123456789012");
        userDTO = anUserRestController.register(anUser).getBody();
        BigDecimal priceATOM = new BigDecimal(binance.priceOf(CryptoName.ATOMUSDT).getPrice());
        BigDecimal priceBN = new BigDecimal(binance.priceOf(CryptoName.BNBUSDT).getPrice());

        IntentionCreationDTO intentionDTO0 = new IntentionCreationDTO(1, priceATOM, "BUY", CryptoName.ATOMUSDT);
        IntentionCreationDTO intentionDTO1 = new IntentionCreationDTO(1, priceBN, "SELL", CryptoName.BNBUSDT);

        intention0 = sut.add(intentionDTO0, userDTO).getBody();
        intention1 = sut.add(intentionDTO1, userDTO).getBody();
    }

    @AfterEach
    void eraseAllIntentions() {
        List<IntentionDTO> intentions = sut.intentions().getBody();
        assertNotNull(intentions);
        for (IntentionDTO intention : intentions) {
            sut.delete(intention.getIntentionId());
        }
        anUserRestController.unregister(userDTO.getId());
    }

    @DisplayName("When intentention controller receive the method intentions by ID delegates to service")
    @Test
    void testWhenIntententionControllerReceiveTheMethodIntentionByIdWithARightIDReturnsCode201() {
        ResponseEntity<IntentionDTO> response = sut.intentionById(intention0.getIntentionId());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @DisplayName("When intentention controller receive the method intentions delegates to service")
    @Test
    void testWhenIntententionControllerReceiveTheMethodIntentionsReturnsCode201() {
        ResponseEntity<List<IntentionDTO>> response = sut.intentions();
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @DisplayName("When intentention controller receive the method intentions with status with a rigth status returns code 200 OK")
    @Test
    void testWhenIntententionControllerReceiveTheMethodIntentionsWithStateReturnsCode200OK() {
        ResponseEntity<List<IntentionDTO>> response = sut.intentionsWithState("SOLD");
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    /*

    @DisplayName("When intentention controller receive the method intentions by ID with a wrong ID")
    @Test
    void testWhenIntententionControllerReceiveTheMethodIntentionByIdWithAWrongIDReturnsCode201() {
        ResponseEntity<IntentionDTO> response = sut.intentionById(12345678L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @DisplayName("When intentention controller receive the method intentions with status with a rigth status returns code 200 OK")
    @Test
    void testWhenIntententionControllerReceiveTheMethodIntentionsWithWrongStateReturnsCode200OK() {
        ResponseEntity<List<IntentionDTO>> response = sut.intentionsWithState("PEPE");
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
    */
}

