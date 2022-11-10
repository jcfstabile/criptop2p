package ar.edu.unq.desapp.grupoo.criptop2p.webservice;

import ar.edu.unq.desapp.grupoo.criptop2p.model.*;
import ar.edu.unq.desapp.grupoo.criptop2p.service.IntentionService;
import ar.edu.unq.desapp.grupoo.criptop2p.service.dto.IntentionDTO;
import ar.edu.unq.desapp.grupoo.criptop2p.service.dto.UserInfoDTO;
import ar.edu.unq.desapp.grupoo.criptop2p.service.exceptions.IncorrectStatusException;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@DisplayName("Intention Controller Tests")
@SpringBootTest
@Transactional
@ExtendWith(MockitoExtension.class)
public class IntentionControllerTest {
    @Autowired
    IntentionController sut;
    IntentionDTO intention0;
    IntentionDTO intention1;

    @AfterEach
    void eraseAllUsers() {
        List<IntentionDTO> intentions = sut.intentions().getBody();
        assertNotNull(intentions);
        for (IntentionDTO intention : intentions) {
            sut.delete(intention.getIntentionId());
        }
    }

    @Before
    void setUp() {
        intention0 = new IntentionDTO(1L, 1, new BigDecimal(2), new Buy(), CryptoName.ATOMUSDT, 2L, Status.OFFERED);
        intention1 = new IntentionDTO(1L, 1, new BigDecimal(2), new Buy(), CryptoName.ATOMUSDT, 2L, Status.SOLD);
        sut.add(intention0);
        sut.add(intention1);
    }


    @DisplayName("When intentention controller receive the method intentions by ID delegates to service")
    @Test
    void testWhenIntententionControllerReceiveTheMethodIntentionByIdWithARightIDReturnsCode201() {
        ResponseEntity<IntentionDTO> response = sut.intentionById(intention0.getIntentionId());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @DisplayName("When intentention controller receive the method intentions by ID delegates to service")
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


    @DisplayName("When intentention controller receive the method add with rigth data returns code 201 CREATED")
    @Test
    void testWhenIntententionControllerReceiveTheMethodAddReturnsCode201Created() {
        IntentionDTO intention = new IntentionDTO(1L, 1, new BigDecimal(2), new Buy(), CryptoName.ATOMUSDT, 2L, Status.SOLD);
        ResponseEntity response = sut.add(intention);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

}
/*

    @DisplayName("When intentention controller receive an unregistred intention ID its throwa error")
    @Test
    void testWhenIntententionControllerReceiveTheMethodIntentionsById() {
    //    ResponseEntity<IntentionDTO> response = sut.intentionById(intention0.getIntentionId());
    //    assertEquals(200, response.getStatusCode());
    }


}
    @DisplayName("When intentention controller receive the method intentions delegates to service")
    @Test
    void testWhenIntententionControllerReceiveTheMethodIntentionselegatesToService(){
        IntentionService serviceMock = mock(IntentionService.class);
        IntentionController controller = new IntentionController(serviceMock);
        controller.intentions();
        verify(serviceMock, times(1)).intentions();
    }


    @DisplayName("When intentention controller receive the method intentions of state delegates to service")
    @Test
    void testWhenIntententionControllerReceiveTheMethodIntentionOfStateselegatesToService(){
        IntentionService serviceMock = mock(IntentionService.class);
        IntentionController controller = new IntentionController(serviceMock);
        controller.intentionsWithState("SOLD");
        verify(serviceMock, times(1)).intentionsWithState("SOLD");
    }

    @DisplayName("Intentention Controller returns whatever the service returns when receives the method intentionById")
    @Test
    void testIntententionControllerReturnWhateverTheServiceReturnsWhenReceivesTheMethodIntentionById(@Mock IntentionService intentionService){
        IntentionController controller = new IntentionController(intentionService);
        Mockito.lenient().when(intentionService.findById(1L)).thenReturn(new IntentionDTO(1L, 1, new BigDecimal(2), new Buy(), CryptoName.ATOMUSDT, 2L, Status.OFFERED));
        IntentionDTO result = controller.intentionById(1L).getBody();
        assertEquals(1L, result.getIntentionId());
        assertEquals(1, result.getCount());
        assertEquals(new BigDecimal(2), result.getPrice());
        assertEquals(Status.OFFERED, result.getStatus());
        assertEquals(TypeName.BUY, result.getType());
        assertEquals(2L, result.getOfferedId());
        assertEquals(2L, result.getOfferedId());
        assertEquals(CryptoName.ATOMUSDT, result.getCryptoName());
    }

    @DisplayName("Intentention Controller returns whatever the service returns when receives the method intentions")
    @Test
    void testIntententionControllerReturnWhateverTheServiceReturnsWhenReceivesTheMethodIntentions(@Mock IntentionService intentionService){
        IntentionController controller = new IntentionController(intentionService);
        IntentionDTO expected = new IntentionDTO(1L, 1, new BigDecimal(2), new Buy(), CryptoName.ATOMUSDT, 2L, Status.OFFERED);
        List<IntentionDTO> expectedXS = List.of(expected);
        Mockito.lenient().when(intentionService.intentions()).thenReturn(expectedXS);
        List<IntentionDTO> intentions = controller.intentions().getBody();
        assertEquals(1, intentions.size());
        IntentionDTO unique = intentions.get(0);
        assertEquals(expected, unique);
    }


    @DisplayName("Intentention Controller returns whatever the service returns when receives the method intentionById")
    @Test
    void testIntententionControllerReturnWhateverTheServiceReturnsWhenReceivesTheMethodIntentionState(@Mock IntentionService intentionService){
        IntentionController controller = new IntentionController(intentionService);
        IntentionDTO expected = new IntentionDTO(1L, 1, new BigDecimal(2), new Buy(), CryptoName.ATOMUSDT, 2L, Status.OFFERED);
        List<IntentionDTO> expectedXS = List.of(expected);
        Mockito.lenient().when(intentionService.intentionsWithState("OFFERED")).thenReturn(expectedXS);
        List<IntentionDTO> intentions = controller.intentionsWithState("OFFERED").getBody();
        assertEquals(1, intentions.size());
        IntentionDTO unique = intentions.get(0);
        assertEquals(expected, unique);
    }

    @DisplayName("Intentention Controller throw a exception when the service throw an exception to receive the messsage intentionWithState and a wrong status")
    @Test
    void testIntententionControllerThrowAnExceptionWhenTheServiceThrowAnException(@Mock IntentionService intentionService){
        IntentionController controller = new IntentionController(intentionService);
        Mockito.lenient().when(intentionService.intentionsWithState("ASD")).thenThrow(new IncorrectStatusException("ASD"));

    }
}
*/