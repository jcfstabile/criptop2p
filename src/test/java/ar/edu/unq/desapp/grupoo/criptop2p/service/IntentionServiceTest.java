package ar.edu.unq.desapp.grupoo.criptop2p.service;

import ar.edu.unq.desapp.grupoo.criptop2p.integrations.BinanceIntegration;
import ar.edu.unq.desapp.grupoo.criptop2p.model.CryptoName;
import ar.edu.unq.desapp.grupoo.criptop2p.service.dto.IntentionCreationDTO;
import ar.edu.unq.desapp.grupoo.criptop2p.service.dto.IntentionDTO;
import ar.edu.unq.desapp.grupoo.criptop2p.service.dto.UserCreationDTO;
import ar.edu.unq.desapp.grupoo.criptop2p.service.dto.UserDTO;
import ar.edu.unq.desapp.grupoo.criptop2p.webservice.UserRestController;
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

@DisplayName("Intention Service Tests")
@SpringBootTest
@Transactional
@ExtendWith(MockitoExtension.class)
class IntentionServiceTest {
    @Autowired
    IntentionService sut;
    @Autowired
    UserRestController anUserRestController;
    IntentionDTO intention0;
    IntentionDTO intention1;

    UserDTO userDTO;

    BinanceIntegration binance;

    @BeforeEach
    void setUp(){
        binance = new BinanceIntegration();
        UserCreationDTO anUser = new UserCreationDTO("Jom", "Nen", "jk22@here.dom", "Fake Street 1234", "Pepito+1234", "66345678", "6664567890123456789012");
        userDTO = anUserRestController.register(anUser).getBody();
        BigDecimal priceATOM = new BigDecimal(binance.priceOf(CryptoName.ATOMUSDT).getPrice());
        BigDecimal priceBN = new BigDecimal(binance.priceOf(CryptoName.BNBUSDT).getPrice());

        IntentionCreationDTO intentionDTO0 = new IntentionCreationDTO(1, priceATOM, "BUY", CryptoName.ATOMUSDT);
        IntentionCreationDTO intentionDTO1 = new IntentionCreationDTO(1, priceBN, "SELL", CryptoName.BNBUSDT);

        intention0 = sut.add(intentionDTO0, userDTO);
        intention1 = sut.add(intentionDTO1, userDTO);
    }

    @AfterEach
    void eraseAllIntentions() {
        List<IntentionDTO> intentions = sut.intentions();
        for (IntentionDTO intention : intentions) {
            sut.delete(intention.getIntentionId());
        }
        //anUserRestController.unregister(userDTO.getId());
    }

    @DisplayName("When intentention controller receive the method intentions by ID delegates to service")
    @Test
    void testWhenIntententionControllerReceiveTheMethodIntentionByIdWithARightIDReturnsCode201() {
        IntentionDTO response = sut.findById(intention0.getIntentionId());
        assertEquals(response.getIntentionId(), intention0.getIntentionId());
        assertEquals(response.getCount(), intention0.getCount());
        assertEquals(response.getPrice(), intention0.getPrice());
        assertEquals(response.getCryptoName(), intention0.getCryptoName());
        assertEquals(response.getStatus(), intention0.getStatus());
        assertEquals(response.getType(), intention0.getType());
    }

    @DisplayName("When intentention controller receive the method intentions delegates to service")
    @Test
    void testWhenIntententionControllerReceiveTheMethodIntentionsReturnsCode201() {
        List<IntentionDTO> response = sut.intentions();
        assertEquals(2, response.size());
    }

    @DisplayName("When intentention controller receive the method intentions delegates to service")
    @Test
    void test() {
        this.eraseAllIntentions();
        List<IntentionDTO> response = sut.intentions();
        assertEquals(0, response.size());
    }

    @DisplayName("When intentention controller receive the method intentions with status with a rigth status returns code 200 OK")
    @Test
    void testWhenIntententionControllerReceiveTheMethodIntentionsWithStateReturnsCode200OK() {
        List<IntentionDTO> response = sut.intentionsWithState("SOLD");
        assertEquals(0, response.size());
        response = sut.intentionsWithState("OFFERED");
        assertEquals(2, response.size());
    }

}
