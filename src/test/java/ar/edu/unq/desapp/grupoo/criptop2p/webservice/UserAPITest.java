package ar.edu.unq.desapp.grupoo.criptop2p.webservice;

import ar.edu.unq.desapp.grupoo.criptop2p.model.CryptoName;
import ar.edu.unq.desapp.grupoo.criptop2p.model.dto.IntentionDTO;
import ar.edu.unq.desapp.grupoo.criptop2p.model.dto.UserCreationDTO;
import ar.edu.unq.desapp.grupoo.criptop2p.model.dto.UserDTO;
import ar.edu.unq.desapp.grupoo.criptop2p.model.exceptions.UserNotFoundException;
import ar.edu.unq.desapp.grupoo.criptop2p.model.Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.math.BigDecimal;


@DisplayName("API Tests")
@SpringBootTest
@Transactional
class APITest {
    static UserCreationDTO anUser;
    @Autowired
    private UserRestController anUserRestController;


    @BeforeEach
    public void setUp(){
        anUser = new UserCreationDTO("Jim", "Ken", "jk@here.dom", "Fake Street 1234", "Pepito+1234", "12345678", "1234567890123456789012");
    }

    @DisplayName("A User can be registered")
    @Test
    void userRegister(){
        UserDTO getUser = anUserRestController.register(anUser).getBody();
        assertNotNull(getUser);
        assertEquals(anUser.getName(), getUser.getName());
        assertEquals(anUser.getEmail(), getUser.getEmail());
    }

    @Test
    void testAUserIsSearchedById(){
        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            anUserRestController.findUserById(100000L);
        });
        String expectedMessage = "Could not find user 100000";
        String actualMessage = exception.getMessage();
        assertEquals(actualMessage, expectedMessage);
        UserDTO registeredUser = anUserRestController.register(anUser).getBody();
        UserDTO getUser = anUserRestController.findUserById(registeredUser.getId()).getBody();;
        assertNotNull(getUser);
        assertEquals(registeredUser.getId(), getUser.getId());
        assertEquals(registeredUser.getName(), getUser.getName());
        assertEquals(registeredUser.getEmail(), getUser.getEmail());
    }

    @Test
    void anUserCanMakeANewOfter(){
        UserDTO getUser = anUserRestController.register(anUser).getBody();;
        //assertEquals(0, getUser.getOffers().size());
        IntentionDTO intentionDTO = new IntentionDTO(10,new BigDecimal(10), Type.SELL, CryptoName.ALICEUSDT);
        anUserRestController.offer(getUser.getId(), intentionDTO);
        //assertEquals(1, getUser.getOffers().size());
    }
}
