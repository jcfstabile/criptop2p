package ar.edu.unq.desapp.grupoo.criptop2p.webservice;

import ar.edu.unq.desapp.grupoo.criptop2p.model.CryptoName;
import ar.edu.unq.desapp.grupoo.criptop2p.model.Status;
import ar.edu.unq.desapp.grupoo.criptop2p.service.QuotationService;
import ar.edu.unq.desapp.grupoo.criptop2p.service.dto.*;
import ar.edu.unq.desapp.grupoo.criptop2p.service.exceptions.IntentionNotFoundException;
import ar.edu.unq.desapp.grupoo.criptop2p.service.exceptions.NoValidActionErrorException;
import ar.edu.unq.desapp.grupoo.criptop2p.service.exceptions.StatusChangeNotAllowedRestException;
import ar.edu.unq.desapp.grupoo.criptop2p.service.exceptions.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@DisplayName("UserRestController Tests")
@SpringBootTest
@ExtendWith(MockitoExtension.class)
@Transactional
class UserRestControllerTest {

    static UserCreationDTO anUser, oneUser;

    @Mock QuotationService quotationServiceMock;

    @Autowired
    UserRestController anUserRestController;

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

    @DisplayName("When an User make a offer it get an intention with status of OFFERED and the User as offered")
    @Test
    void anUserCanMakeANewOfter() throws InterruptedException {
        UserDTO userDTO = anUserRestController.register(anUser).getBody();
        assertNotNull(userDTO);
        CryptoName cryptoName = CryptoName.ALICEUSDT;

        IntentionCreationDTO intentionCreationDTO = new IntentionCreationDTO(10, quotationService.priceOf(cryptoName), "SELL", cryptoName);
        assertNotNull(userDTO.getId());

        IntentionDTO intentionDTO = anUserRestController.offer(userDTO.getId(), intentionCreationDTO).getBody();

        assertNotNull(intentionCreationDTO);
        assertNotNull(intentionDTO);
        assertEquals(Status.OFFERED, intentionDTO.getStatus() );
        assertEquals(userDTO.getId(), intentionDTO.getOfferedId());
    }

    void eraseAllUsers() {
        List<UserInfoDTO> users = anUserRestController.allUsers().getBody();
        assertNotNull(users);
        for(UserInfoDTO user : users) { anUserRestController.unregister(user.getId()); }
    }

    @DisplayName("When there is no User registered allUsers get no UserInfo users")
    @Test
    void allUsersEmpty() {
        eraseAllUsers();

        List<UserInfoDTO> userInfoDTOS = anUserRestController.allUsers().getBody();

        assertNotNull(userInfoDTOS);
        assertEquals(0, userInfoDTOS.size() );
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
        ResponseEntity<Void> deleteResponse;
        UserDTO registeredUser = anUserRestController.register(anUser).getBody();
        assertNotNull(registeredUser);
        Long userId = registeredUser.getId();
        anUserRestController.findUserById(userId);


        deleteResponse = anUserRestController.unregister(userId);

        assertNotNull(deleteResponse);
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

    @DisplayName("Not valid Status change ")
    @Test
    void testAnInvalidStatusChange() throws InterruptedException {
        UserDTO seller = anUserRestController.register(anUser).getBody();
        UserDTO buyer = anUserRestController.register(oneUser).getBody();
        assertNotNull(seller);
        assertNotNull(buyer);
        Long  buyerId = buyer.getId();
        CryptoName cryptoName = CryptoName.ATOMUSDT;
        when(quotationServiceMock.priceOf(cryptoName)).thenReturn(BigDecimal.valueOf(1.0));

        IntentionCreationDTO intentionCreationDTO = new IntentionCreationDTO(10, quotationServiceMock.priceOf(cryptoName), "SELL", cryptoName);
        IntentionDTO intentionDTO = anUserRestController.offer(seller.getId(), intentionCreationDTO).getBody();
        assertNotNull(intentionDTO);
        Long intentionId = intentionDTO.getIntentionId();
        assertEquals(Status.OFFERED, intentionDTO.getStatus());



        Exception exception = assertThrows(StatusChangeNotAllowedRestException.class, () ->
                anUserRestController.processIntention(buyerId, intentionId, "payment")
        );

        assertEquals("Cant change the status to: WAITINGFORDELIVERY"  , exception.getMessage());
    }

    @DisplayName("Not valid action found exception thrown")
    @Test
    void testAnInvalidActionIsNotFound() throws InterruptedException {
        UserDTO seller = anUserRestController.register(anUser).getBody();
        UserDTO buyer = anUserRestController.register(oneUser).getBody();
        assertNotNull(seller);
        assertNotNull(buyer);
        Long  buyerId = buyer.getId();
        CryptoName cryptoName = CryptoName.ATOMUSDT;
        IntentionCreationDTO intentionCreationDTO = new IntentionCreationDTO(10, quotationService.priceOf(cryptoName), "SELL", cryptoName);
        IntentionDTO intentionDTO = anUserRestController.offer(seller.getId(), intentionCreationDTO).getBody();
        assertNotNull(intentionDTO);
        Long intentionId = intentionDTO.getIntentionId();
        assertEquals(Status.OFFERED, intentionDTO.getStatus());
        String notValidAction = "WrongAction";

        Exception exception = assertThrows(NoValidActionErrorException.class, () ->
                anUserRestController.processIntention(buyerId, intentionId, notValidAction)
        );

        assertEquals("Action not valid: " + notValidAction , exception.getMessage());
    }

    @DisplayName("Intention not found exception thrown")
    @Test
    void testAnIntentionIsNotFound() throws InterruptedException {
        UserDTO seller = anUserRestController.register(anUser).getBody();
        UserDTO buyer = anUserRestController.register(oneUser).getBody();
        assertNotNull(seller);
        assertNotNull(buyer);
        Long  buyerId = buyer.getId();
        CryptoName cryptoName = CryptoName.ATOMUSDT;
        IntentionCreationDTO intentionCreationDTO = new IntentionCreationDTO(10, quotationService.priceOf(cryptoName), "SELL", cryptoName);
        IntentionDTO intentionDTO = anUserRestController.offer(seller.getId(), intentionCreationDTO).getBody();
        assertNotNull(intentionDTO);
        assertEquals(Status.OFFERED, intentionDTO.getStatus());
        Long notFoundIntentionID = intentionDTO.getIntentionId() + 1234;

        Exception exception = assertThrows(IntentionNotFoundException.class, () ->
                anUserRestController.processIntention(buyerId, notFoundIntentionID, "accept")
        );

        assertEquals("Could not find intention " + notFoundIntentionID, exception.getMessage());
    }


    @DisplayName("An user accept a intention" )
    @Test
    void testAnUserAcceptAIntentions() throws InterruptedException {

        UserDTO seller = anUserRestController.register(anUser).getBody();
        UserDTO buyer = anUserRestController.register(oneUser).getBody();
        assertNotNull(seller);
        assertNotNull(buyer);
        CryptoName cryptoName = CryptoName.ATOMUSDT;
        BigDecimal sellPrice = quotationService.priceOf(cryptoName).multiply(BigDecimal.valueOf(0.99));

        IntentionCreationDTO intentionCreationDTO = new IntentionCreationDTO(10, sellPrice, "SELL", cryptoName);
        IntentionDTO intentionDTO = anUserRestController.offer(seller.getId(), intentionCreationDTO).getBody();

        assertNotNull(intentionDTO);
        assertEquals(Status.OFFERED, intentionDTO.getStatus());

        IntentionDTO acceptedIntentionDTO = anUserRestController.processIntention(buyer.getId(), intentionDTO.getIntentionId(), "accept").getBody();


        assertNotNull(acceptedIntentionDTO);
        assertEquals(Status.SOLD, acceptedIntentionDTO.getStatus());
    }

    @DisplayName("An user recently created hasn't activated intentions")
    @Test
    void testAnUserRecentlyHasNotActivatedIntentions(){
        UserDTO registeredUser = anUserRestController.register(anUser).getBody();
        assertNotNull(registeredUser);

        List<IntentionDTO> activatedIntentions = anUserRestController.activatedIntentionsOf(registeredUser.getId()).getBody();

        assertNotNull(activatedIntentions);
        assertEquals(0, activatedIntentions.size());
    }


    @DisplayName("An user recently has only one activated intention when only offers one time")
    @Test
    void testAnUserRecentlyHasOnlyActivatedIntentions() throws InterruptedException {
        UserDTO registeredUser = anUserRestController.register(anUser).getBody();
        assertNotNull(registeredUser);
        CryptoName cryptoName = CryptoName.ALICEUSDT;
        IntentionCreationDTO intentionCreationDTO = new IntentionCreationDTO(10, quotationService.priceOf(cryptoName), "SELL", cryptoName);
        anUserRestController.offer(registeredUser.getId(), intentionCreationDTO);

        List<IntentionDTO> activatedIntentions = anUserRestController.activatedIntentionsOf(registeredUser.getId()).getBody();

        assertNotNull(activatedIntentions);
        assertEquals(1, activatedIntentions.size());
    }

    @DisplayName("An user has two activated intention when offers twice")
    @Test
    void testAnUserHasTwoActivatedIntentions() throws InterruptedException {
        UserDTO registeredUser = anUserRestController.register(anUser).getBody();
        assertNotNull(registeredUser);
        CryptoName cryptoName = CryptoName.ALICEUSDT;
        IntentionCreationDTO intentionCreationDTO0 = new IntentionCreationDTO(10, quotationService.priceOf(cryptoName), "SELL", cryptoName);
        IntentionCreationDTO intentionCreationDTO1 = new IntentionCreationDTO(10, quotationService.priceOf(cryptoName), "BUY", cryptoName);
        anUserRestController.offer(registeredUser.getId(), intentionCreationDTO0);
        anUserRestController.offer(registeredUser.getId(), intentionCreationDTO1);

        List<IntentionDTO> activatedIntentions = anUserRestController.activatedIntentionsOf(registeredUser.getId()).getBody();

        assertNotNull(activatedIntentions);
        assertEquals(2, activatedIntentions.size());
    }
}
