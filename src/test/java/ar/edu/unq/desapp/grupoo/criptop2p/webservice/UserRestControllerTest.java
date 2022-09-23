package ar.edu.unq.desapp.grupoo.criptop2p.webservice;

import ar.edu.unq.desapp.grupoo.criptop2p.model.CryptoName;
import ar.edu.unq.desapp.grupoo.criptop2p.model.Type;
import ar.edu.unq.desapp.grupoo.criptop2p.model.dto.IntentionDTO;
import ar.edu.unq.desapp.grupoo.criptop2p.model.dto.UserCreationDTO;
import ar.edu.unq.desapp.grupoo.criptop2p.model.dto.UserDTO;
import ar.edu.unq.desapp.grupoo.criptop2p.model.dto.UserInfoDTO;
import ar.edu.unq.desapp.grupoo.criptop2p.model.exceptions.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("UserRestController Tests")
@SpringBootTest
@Transactional
class UserRestControllerTest {

    static UserCreationDTO anUser, oneUser;
    @Autowired
    private UserRestController anUserRestController;


    @BeforeEach
    public void setUp(){
        anUser = new UserCreationDTO("Jom", "Nen", "jk@here.dom", "Fake Street 1234", "Pepito+1234", "12345678", "1234567890123456789012");
        oneUser = new UserCreationDTO("Mik", "Ken", "jh@here.dom", "Fake Street 1234", "Pepito+1234", "12345679", "1234567890123456789012");
    }

    @DisplayName("An User can be registered")
    @Test
    void userRegister(){
        UserDTO getUser = anUserRestController.register(anUser).getBody();
        assertNotNull(getUser);
        assertEquals(anUser.getName(), getUser.getName());
        assertEquals(anUser.getEmail(), getUser.getEmail());
    }

    @DisplayName("Find User by Id")
    @Test
    void testAUserIsSearchedById(){
        Exception exception = assertThrows(UserNotFoundException.class, () ->
                anUserRestController.findUserById(0L));
        String expectedMessage = "Could not find user 0";
        String actualMessage = exception.getMessage();
        assertEquals(actualMessage, expectedMessage);
        UserDTO registeredUser = anUserRestController.register(anUser).getBody();
        assertNotNull(registeredUser);
        UserDTO getUser = anUserRestController.findUserById(registeredUser.getId()).getBody();
        assertNotNull(getUser);
        assertEquals(registeredUser.getId(), getUser.getId());
        assertEquals(registeredUser.getName(), getUser.getName());
        assertEquals(registeredUser.getEmail(), getUser.getEmail());
    }

    @DisplayName("An User can make a offer")
    @Test
    void anUserCanMakeANewOfter(){
        UserDTO getUser = anUserRestController.register(anUser).getBody();
        assertNotNull(getUser);
        //assertEquals(0, getUser.getOffers().size());
        IntentionDTO intentionDTO = new IntentionDTO(10,new BigDecimal(10), Type.SELL, CryptoName.ALICEUSDT);

        anUserRestController.offer(getUser.getId(), intentionDTO);
        //assertEquals(1, getUser.getOffers().size());
    }

    void eraseAllUsers() {
        List<UserInfoDTO> users = anUserRestController.allUsers().getBody();
        assertNotNull(users);
        for(UserInfoDTO user : users) { anUserRestController.unregister(user.getId()); }
    }

    @DisplayName("Get no UserInfo users")
    @Test
    void allUsersEmpty() {
        eraseAllUsers();

        List<UserInfoDTO> users = anUserRestController.allUsers().getBody();

        assertNotNull(users);
        assertEquals(users.size(), 0);
    }


    @DisplayName("Get all UserInfo users")
    @Test
    void allUsers() {
        eraseAllUsers();
        UserDTO user1 = anUserRestController.register(anUser).getBody();
        UserDTO user2 = anUserRestController.register(oneUser).getBody();

        List<UserInfoDTO> users = anUserRestController.allUsers().getBody();

        assertNotNull(users);
        assertNotNull(user1);
        assertNotNull(user2);
        assertEquals(2, users.size());
        assertEquals(user1.getName(), users.get(0).getName() );
        assertEquals(user2.getName(), users.get(1).getName() );
    }

    @DisplayName("An User can be unregistered")
    @Test
    void unregister() {
        UserDTO registeredUser = anUserRestController.register(anUser).getBody();
        assertNotNull(registeredUser);
        Long userId = registeredUser.getId();
        anUserRestController.findUserById(userId);

        anUserRestController.unregister(userId);

        assertThrows(UserNotFoundException.class, () -> anUserRestController.findUserById(userId));
    }

    @DisplayName("When a non existent User is unsubscribed a exception is thrown")
    @Test
    void unregisterANonExistentUser(){
        Exception exception = assertThrows(UserNotFoundException.class, () ->
                anUserRestController.unregister(0L)
        );

        assertEquals("Could not find user 0", exception.getMessage());
    }
}

