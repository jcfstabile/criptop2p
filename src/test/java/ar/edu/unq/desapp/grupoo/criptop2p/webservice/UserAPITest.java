package ar.edu.unq.desapp.grupoo.criptop2p.webservice;

import ar.edu.unq.desapp.grupoo.criptop2p.model.Crypto;
import ar.edu.unq.desapp.grupoo.criptop2p.model.User;
import ar.edu.unq.desapp.grupoo.criptop2p.model.dto.IntentionDTO;
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


@DisplayName("API Tests")
@SpringBootTest
@Transactional
class APITest {
    static User anUser;
    @Autowired
    private UserRestController anUserRestController;


    @BeforeEach
    public void setUp(){
        anUser = new User("Jim", "Ken", "jk@here.dom", "Fake Street 1234", "Pepito+1234", "12345678", "1234567890123456789012");
    }

    @DisplayName("A User can be registed")
    @Test
    void userRegister(){
        assertEquals(null, anUser.getId());
        User getUser = anUserRestController.register(anUser);
        assertNotNull(getUser);
        assertEquals(anUser.getId(), getUser.getId());
        assertEquals(anUser.getName(), getUser.getName());
        assertEquals(anUser.getEmail(), getUser.getEmail());
    }

    @Test
    void testAUserIsSearchedById(){
        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            anUserRestController.findUserById(1L);
        });
        String expectedMessage = "Could not find user 1";
        String actualMessage = exception.getMessage();
        assertEquals(actualMessage, expectedMessage);
        anUserRestController.register(anUser);
        User getUser = anUserRestController.findUserById(anUser.getId());
        assertNotNull(getUser);
        assertEquals(anUser.getId(), getUser.getId());
        assertEquals(anUser.getName(), getUser.getName());
        assertEquals(anUser.getEmail(), getUser.getEmail());
    }

    @Test
    void anUserCanMakeANewOfter(){
        User getUser = anUserRestController.register(anUser);
        //assertEquals(0, getUser.getOffers().size());
        IntentionDTO intentionDTO = new IntentionDTO(10,100L, Type.SELL, Crypto.ALICEUSDT);
        anUserRestController.ofter(getUser.getId(), intentionDTO);
        //assertEquals(1, getUser.getOffers().size());
    }
}
