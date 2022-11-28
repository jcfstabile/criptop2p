package ar.edu.unq.desapp.grupoo.criptop2p.service;

import ar.edu.unq.desapp.grupoo.criptop2p.integrations.BinanceIntegration;
import ar.edu.unq.desapp.grupoo.criptop2p.model.CryptoName;
import ar.edu.unq.desapp.grupoo.criptop2p.model.builders.UserCreationDTOBuilder;
import ar.edu.unq.desapp.grupoo.criptop2p.service.dto.IntentionCreationDTO;
import ar.edu.unq.desapp.grupoo.criptop2p.service.dto.UserCreationDTO;
import ar.edu.unq.desapp.grupoo.criptop2p.service.dto.UserInfoDTO;
import ar.edu.unq.desapp.grupoo.criptop2p.service.exceptions.DataIncomingConflictException;
import ar.edu.unq.desapp.grupoo.criptop2p.service.exceptions.DifferenceWithCurrentPriceException;
import ar.edu.unq.desapp.grupoo.criptop2p.service.exceptions.UserConstraintViolationException;
import ar.edu.unq.desapp.grupoo.criptop2p.service.exceptions.UserNotFoundException;
import ar.edu.unq.desapp.grupoo.criptop2p.service.services.UserService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.math.BigDecimal;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("UserService Tests")
@SpringBootTest
class UserServiceTest {
    static class UserCreationDTOBuilderUnique extends UserCreationDTOBuilder {
        public UserCreationDTOBuilderUnique() {
            super("aaa","bbb","c@d.e","fghijklmno", "Pqrs7$", "12345678","1234567890123456789012");
        }

        @Override
        public UserCreationDTOBuilder withEmail(String email){
            this.withWalletAddress(email.repeat(2).substring(0,8));
            return super.withEmail(email);
        }
    }

    UserCreationDTOBuilder anyUser = new UserCreationDTOBuilderUnique();

    UserCreationDTO userCreationDTO;

    UserConstraintViolationException userConstraintViolationException;
    @Autowired
    UserService userService;

    @AfterEach
    void eraise(){
        List<UserInfoDTO> users = userService.findAll();
        users.forEach(user -> userService.deleteUserById(user.getId()));
    }

    @DisplayName("When an User is correctly formed is added")
    @Test
    void addAnUser(){
        userCreationDTO = anyUser.withEmail("just@add.once").build();

        Long userId = userService.addUser(userCreationDTO);

        assertNotNull(userId);
    }

    @DisplayName("When an User has a bad formed email throws a UserConstraintException")
    @Test
    void addAnUserWithBadFormedEmailThrowException(){

        userCreationDTO = anyUser.withEmail("a$b.c").build();

        userConstraintViolationException = assertThrows(
                UserConstraintViolationException.class, () -> userService.addUser(userCreationDTO));

        assertEquals("Email is not valid", userConstraintViolationException.getErrors().get(0) );
    }

    @DisplayName("When an User has a bad formed password throws a UserConstraintException")
    @Test
    void addAnUserWithBadFormedPasswordThrowException(){
        userCreationDTO = anyUser.withPassword("123456").build();

        userConstraintViolationException = assertThrows(
                UserConstraintViolationException.class, () -> userService.addUser(userCreationDTO));

        assertTrue(userConstraintViolationException.getErrors().get(0).contains("Password must contain:") );
    }

    @DisplayName("When an User has a bad formed Address throws a UserConstraintException")
    @Test
    void addAnUserWithBadFormedAddressThrowException(){
        userCreationDTO = anyUser.withAddress("Here 234").build();

        userConstraintViolationException = assertThrows(
                UserConstraintViolationException.class, () -> userService.addUser(userCreationDTO));

        assertEquals("Address must have between 10 and 30 characters",
                      userConstraintViolationException.getErrors().get(0) );
    }

    @DisplayName("When an User has a bad formed WalletAddress throws a UserConstraintException")
    @Test
    void addAnUserWithBadFormedWalletAddressThrowException(){
        userCreationDTO = anyUser.withWalletAddress("1234567").build();

        userConstraintViolationException = assertThrows(
                UserConstraintViolationException.class, () -> userService.addUser(userCreationDTO));

        assertEquals("Wallet Address must have 8 characters", userConstraintViolationException.getErrors().get(0) );
    }

    @DisplayName("When an User has a bad formed CVU throws a UserConstraintException")
    @Test
    void addAnUserWithBadFormedCVUThrowException(){
        userCreationDTO = anyUser.withCvu("abcdefghijKLMNOPQRST1").build();

        userConstraintViolationException = assertThrows(
                UserConstraintViolationException.class, () -> userService.addUser(userCreationDTO));

        assertEquals("CVU must have 22 characters", userConstraintViolationException.getErrors().get(0) );
    }

    @DisplayName("Finding a existent User give the User")
    @Test
    void findExistentUser(){
        String name = "Pete";
        String surname = "Townshend";
        userCreationDTO = anyUser.withEmail("pete@here.dom").withName(name).withSurname(surname).build();

        Long userId = userService.addUser(userCreationDTO);

        assertEquals(name, userService.findByID(userId).getName());
        assertEquals(surname, userService.findByID(userId).getSurname());
    }

    @DisplayName("Finding a  not existent User throws a UserNotFoundException")
    @Test
    void findNotExistentUser(){
        UserNotFoundException userNotFoundException = assertThrows(UserNotFoundException.class, () -> userService.findByID((long)0));

        assertEquals("Could not find user 0", userNotFoundException.getMessage());
    }

    @DisplayName("Adding an User with a email already registered throw a DataIncomingConflictException")
    @Test
    void addingAnUserWithAUsedEmailThrowException(){
        UserCreationDTO registeredUser = anyUser.withEmail("me@here.throw").withWalletAddress("aaaaaaaa").build();
        userService.addUser(registeredUser);
        UserCreationDTO otherUser = anyUser.withEmail("me@here.throw").withWalletAddress("xxxxxxxx").build();
        DataIncomingConflictException dataIncomingConflictException =
                assertThrows(DataIncomingConflictException.class , () -> userService.addUser(otherUser));
        assertEquals("The operation can not be completed due to data conflict",
                dataIncomingConflictException.getError());
    }
    @DisplayName("Delete an User by id")
    @Test
    void deleteUserById(){
        userCreationDTO = anyUser.withEmail("tobe@delete.soon").withWalletAddress("00000000").withCvu("0000 0000 0000 0000 00").build();
        Long id = userService.addUser(userCreationDTO);

        userService.deleteUserById(id);

        UserNotFoundException userNotFoundException = assertThrows(UserNotFoundException.class, () -> userService.findByID(id));

        assertEquals("Could not find user " + id.toString(), userNotFoundException.getMessage());
    }

    @SneakyThrows
    @DisplayName("When the offered price is higher current price throws an exception")
    @Test
    void testWhenTheOfferedPriceIshigherCurrentPriceThrowsAnException() {
        UserCreationDTO anUserCreationDTO = anyUser.withEmail("onionsoup@here.dom").withName("Pepito").withSurname("Valenzuelo").withWalletAddress("asdthjuk").withCvu("7635413249870645732175").build();
        Long userId = userService.addUser(anUserCreationDTO);
        BigDecimal quotation = new BigDecimal(new BinanceIntegration().priceOf(CryptoName.ATOMUSDT).getPrice());
        BigDecimal twentyOnePorcent = quotation.multiply(new BigDecimal("21")).divide(new BigDecimal("100"));
        BigDecimal offeredPrice = quotation.add(twentyOnePorcent);
        String offeredPriceSt = offeredPrice.toString();
        IntentionCreationDTO intentionCreation = new IntentionCreationDTO(1, offeredPriceSt, "BUY", "ATOMUSDT");
        Exception exception = assertThrows(DifferenceWithCurrentPriceException.class, () ->
                userService.offer(userId, intentionCreation));
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains("has a difference of 20% or more with"));
    }

    @SneakyThrows
    @DisplayName("When the offered price is lower current price throws an exception")
    @Test
    void testWhenTheOfferedPriceIsLowerCurrentPriceThrowsAnException() {
        UserCreationDTO anUserCreationDTO = anyUser.withEmail("onionsoup@here.dom").withName("Pepito").withSurname("Valenzuelo").withWalletAddress("asdthjuk").withCvu("7635413249870645732175").build();
        Long userId = userService.addUser(anUserCreationDTO);
        BigDecimal quotation = new BigDecimal(new BinanceIntegration().priceOf(CryptoName.ATOMUSDT).getPrice());
        BigDecimal twentyOnePorcent = quotation.multiply(new BigDecimal("21")).divide(new BigDecimal("100"));
        BigDecimal offeredPrice = quotation.subtract(twentyOnePorcent);
        String offeredPriceSt = offeredPrice.toString();
        IntentionCreationDTO intentionCreation = new IntentionCreationDTO(1, offeredPriceSt, "BUY", "ATOMUSDT");
        Exception exception = assertThrows(DifferenceWithCurrentPriceException.class, () ->
                userService.offer(userId, intentionCreation));
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains("has a difference of 20% or more with"));
    }
}