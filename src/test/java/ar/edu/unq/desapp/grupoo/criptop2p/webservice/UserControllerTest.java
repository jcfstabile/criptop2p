package ar.edu.unq.desapp.grupoo.criptop2p.webservice;

import ar.edu.unq.desapp.grupoo.criptop2p.model.CryptoName;
import ar.edu.unq.desapp.grupoo.criptop2p.model.Status;
import ar.edu.unq.desapp.grupoo.criptop2p.service.services.QuotationService;
import ar.edu.unq.desapp.grupoo.criptop2p.service.dto.*;
import ar.edu.unq.desapp.grupoo.criptop2p.service.exceptions.IntentionNotFoundException;
import ar.edu.unq.desapp.grupoo.criptop2p.service.exceptions.NoValidActionErrorException;
import ar.edu.unq.desapp.grupoo.criptop2p.service.exceptions.StatusChangeNotAllowedRestException;
import ar.edu.unq.desapp.grupoo.criptop2p.service.exceptions.UserNotFoundException;
import ar.edu.unq.desapp.grupoo.criptop2p.webservice.controllers.UserController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("UserRestController Tests")
@SpringBootTest
@ExtendWith(MockitoExtension.class)
@Transactional
class UserControllerTest {

    static UserCreationDTO anUser, oneUser;

    @Autowired
    UserController anUserController;

    @Autowired
    QuotationService quotationService;

    @BeforeEach
    public void setUp(){
        anUser = new UserCreationDTO("Jom", "Nen", "jk@here.dom", "Fake Street 1234", "Pepito+1234", "12345678", "1234567890123456789012");
        oneUser = new UserCreationDTO("Mik", "Ken", "jh@here.dom", "Fake Street 1234", "Pepito+1234", "12345679", "1234567890123456789012");
    }

    @DisplayName("An User can be registered")
    @Test
    void userRegister(){
        UserDTO getUser = anUserController.register(anUser).getBody();
        assertNotNull(getUser);
        assertEquals(anUser.getName(), getUser.getName());
        assertEquals(anUser.getEmail(), getUser.getEmail());
    }

    @DisplayName("Find User by Id")
    @Test
    void testAUserIsSearchedById(){
        Exception exception = assertThrows(UserNotFoundException.class, () ->
                anUserController.findUserById(0L));
        String expectedMessage = "Could not find user 0";
        String actualMessage = exception.getMessage();
        assertEquals(actualMessage, expectedMessage);
        UserDTO registeredUser = anUserController.register(anUser).getBody();
        assertNotNull(registeredUser);
        UserDTO getUser = anUserController.findUserById(registeredUser.getId()).getBody();
        assertNotNull(getUser);
        assertEquals(registeredUser.getId(), getUser.getId());
        assertEquals(registeredUser.getName(), getUser.getName());
        assertEquals(registeredUser.getEmail(), getUser.getEmail());
    }

    @DisplayName("When an User make a offer it get an intention with status of OFFERED and the User as offered")
    @Test
    void anUserCanMakeANewOfter() throws InterruptedException {
        UserDTO userDTO = anUserController.register(anUser).getBody();
        assertNotNull(userDTO);
        String cryptoName = "ALICEUSDT";

        IntentionCreationDTO intentionCreationDTO = new IntentionCreationDTO(10, quotationService.priceOf(CryptoName.ALICEUSDT).toString(), "SELL", cryptoName);
        assertNotNull(userDTO.getId());

        IntentionDTO intentionDTO = anUserController.offer(userDTO.getId(), intentionCreationDTO).getBody();

        assertNotNull(intentionCreationDTO);
        assertNotNull(intentionDTO);
        assertEquals(Status.OFFERED, intentionDTO.getStatus() );
        assertEquals(userDTO.getId(), intentionDTO.getOfferedId());
    }

    void eraseAllUsers() {
        List<UserInfoDTO> users = anUserController.allUsers().getBody();
        assertNotNull(users);
        for(UserInfoDTO user : users) { anUserController.unregister(user.getId()); }
    }

    @DisplayName("When there is no User registered allUsers get no UserInfo users")
    @Test
    void allUsersEmpty() {
        eraseAllUsers();

        List<UserInfoDTO> userInfoDTOS = anUserController.allUsers().getBody();

        assertNotNull(userInfoDTOS);
        assertEquals(0, userInfoDTOS.size() );
    }


    @DisplayName("Get all UserInfo users")
    @Test
    void allUsers() {
        eraseAllUsers();
        UserDTO user1 = anUserController.register(anUser).getBody();
        UserDTO user2 = anUserController.register(oneUser).getBody();

        List<UserInfoDTO> users = anUserController.allUsers().getBody();

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
        ResponseEntity<Void> deleteResponse;
        UserDTO registeredUser = anUserController.register(anUser).getBody();
        assertNotNull(registeredUser);
        Long userId = registeredUser.getId();
        anUserController.findUserById(userId);


        deleteResponse = anUserController.unregister(userId);

        assertNotNull(deleteResponse);
        assertThrows(UserNotFoundException.class, () -> anUserController.findUserById(userId));
    }

    @DisplayName("When a non existent User is unsubscribed a exception is thrown")
    @Test
    void unregisterANonExistentUser(){
        Exception exception = assertThrows(UserNotFoundException.class, () ->
                anUserController.unregister(0L)
        );

        assertEquals("Could not find user 0", exception.getMessage());
    }

    @DisplayName("Not valid Status change ")
    @Test
    void testAnInvalidStatusChange() throws InterruptedException {
        UserDTO seller = anUserController.register(anUser).getBody();
        UserDTO buyer = anUserController.register(oneUser).getBody();
        assertNotNull(seller);
        assertNotNull(buyer);
        Long  buyerId = buyer.getId();
        String cryptoName = "ATOMUSDT";

        IntentionCreationDTO intentionCreationDTO = new IntentionCreationDTO(10, quotationService.priceOf(CryptoName.ATOMUSDT).toString(), "SELL", cryptoName);
        IntentionDTO intentionDTO = anUserController.offer(seller.getId(), intentionCreationDTO).getBody();
        assertNotNull(intentionDTO);
        Long intentionId = intentionDTO.getIntentionId();
        assertEquals(Status.OFFERED, intentionDTO.getStatus());



        Exception exception = assertThrows(StatusChangeNotAllowedRestException.class, () ->
                anUserController.processIntention(buyerId, intentionId, "payment")
        );

        assertEquals("Cant change the status to: WAITINGFORDELIVERY"  , exception.getMessage());
    }

    @DisplayName("Not valid action found exception thrown")
    @Test
    void testAnInvalidActionIsNotFound() throws InterruptedException {
        UserDTO seller = anUserController.register(anUser).getBody();
        UserDTO buyer = anUserController.register(oneUser).getBody();
        assertNotNull(seller);
        assertNotNull(buyer);
        Long  buyerId = buyer.getId();
        String cryptoName = "ATOMUSDT";
        IntentionCreationDTO intentionCreationDTO = new IntentionCreationDTO(10, quotationService.priceOf(CryptoName.valueOf(cryptoName)).toString(), "SELL", cryptoName);
        IntentionDTO intentionDTO = anUserController.offer(seller.getId(), intentionCreationDTO).getBody();
        assertNotNull(intentionDTO);
        Long intentionId = intentionDTO.getIntentionId();
        assertEquals(Status.OFFERED, intentionDTO.getStatus());
        String notValidAction = "WrongAction";

        Exception exception = assertThrows(NoValidActionErrorException.class, () ->
                anUserController.processIntention(buyerId, intentionId, notValidAction)
        );

        assertEquals("Action not valid: " + notValidAction , exception.getMessage());
    }

    @DisplayName("Intention not found exception thrown")
    @Test
    void testAnIntentionIsNotFound() throws InterruptedException {
        UserDTO seller = anUserController.register(anUser).getBody();
        UserDTO buyer = anUserController.register(oneUser).getBody();
        assertNotNull(seller);
        assertNotNull(buyer);
        Long  buyerId = buyer.getId();
        String cryptoName = "ATOMUSDT";
        IntentionCreationDTO intentionCreationDTO = new IntentionCreationDTO(10, quotationService.priceOf(CryptoName.valueOf(cryptoName)).toString(), "SELL", cryptoName);
        IntentionDTO intentionDTO = anUserController.offer(seller.getId(), intentionCreationDTO).getBody();
        assertNotNull(intentionDTO);
        assertEquals(Status.OFFERED, intentionDTO.getStatus());
        Long notFoundIntentionID = intentionDTO.getIntentionId() + 1234;

        Exception exception = assertThrows(IntentionNotFoundException.class, () ->
                anUserController.processIntention(buyerId, notFoundIntentionID, "accept")
        );

        assertEquals("Could not find intention " + notFoundIntentionID, exception.getMessage());
    }


    @DisplayName("An user accept a intention" )
    @Test
    void testAnUserAcceptAIntentions() throws InterruptedException {

        UserDTO seller = anUserController.register(anUser).getBody();
        UserDTO buyer = anUserController.register(oneUser).getBody();
        assertNotNull(seller);
        assertNotNull(buyer);
        String cryptoName = "ATOMUSDT";
        BigDecimal sellPrice = quotationService.priceOf(CryptoName.valueOf(cryptoName)).multiply(BigDecimal.valueOf(0.99));

        IntentionCreationDTO intentionCreationDTO = new IntentionCreationDTO(10, sellPrice.toString(), "SELL", cryptoName);
        IntentionDTO intentionDTO = anUserController.offer(seller.getId(), intentionCreationDTO).getBody();

        assertNotNull(intentionDTO);
        assertEquals(Status.OFFERED, intentionDTO.getStatus());

        IntentionDTO acceptedIntentionDTO = anUserController.processIntention(buyer.getId(), intentionDTO.getIntentionId(), "accept").getBody();


        assertNotNull(acceptedIntentionDTO);
        assertEquals(Status.SOLD, acceptedIntentionDTO.getStatus());
    }

    @DisplayName("An user recently created hasn't activated intentions")
    @Test
    void testAnUserRecentlyHasNotActivatedIntentions(){
        UserDTO registeredUser = anUserController.register(anUser).getBody();
        assertNotNull(registeredUser);

        List<IntentionDTO> activatedIntentions = anUserController.activatedIntentionsOf(registeredUser.getId()).getBody();

        assertNotNull(activatedIntentions);
        assertEquals(0, activatedIntentions.size());
    }


    @DisplayName("An user recently has only one activated intention when only offers one time")
    @Test
    void testAnUserRecentlyHasOnlyActivatedIntentions() throws InterruptedException {
        UserDTO registeredUser = anUserController.register(anUser).getBody();
        assertNotNull(registeredUser);
        CryptoName cryptoName = CryptoName.ALICEUSDT;
        IntentionCreationDTO intentionCreationDTO = new IntentionCreationDTO(10, quotationService.priceOf(cryptoName).toString(), "SELL", "ALICEUSDT");
        anUserController.offer(registeredUser.getId(), intentionCreationDTO);

        List<IntentionDTO> activatedIntentions = anUserController.activatedIntentionsOf(registeredUser.getId()).getBody();

        assertNotNull(activatedIntentions);
        assertEquals(1, activatedIntentions.size());
    }

    @DisplayName("An user has two activated intention when offers twice")
    @Test
    void testAnUserHasTwoActivatedIntentions() throws InterruptedException {
        UserDTO registeredUser = anUserController.register(anUser).getBody();
        assertNotNull(registeredUser);
        CryptoName cryptoName = CryptoName.ALICEUSDT;
        IntentionCreationDTO intentionCreationDTO0 = new IntentionCreationDTO(10, quotationService.priceOf(cryptoName).toString(), "SELL", "ALICEUSDT");
        IntentionCreationDTO intentionCreationDTO1 = new IntentionCreationDTO(10, quotationService.priceOf(cryptoName).toString(), "BUY", "ALICEUSDT");
        anUserController.offer(registeredUser.getId(), intentionCreationDTO0);
        anUserController.offer(registeredUser.getId(), intentionCreationDTO1);

        List<IntentionDTO> activatedIntentions = anUserController.activatedIntentionsOf(registeredUser.getId()).getBody();

        assertNotNull(activatedIntentions);
        assertEquals(2, activatedIntentions.size());
    }
}
