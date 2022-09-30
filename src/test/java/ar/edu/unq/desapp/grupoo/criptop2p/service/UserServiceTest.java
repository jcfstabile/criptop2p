package ar.edu.unq.desapp.grupoo.criptop2p.service;

import ar.edu.unq.desapp.grupoo.criptop2p.model.User;
import ar.edu.unq.desapp.grupoo.criptop2p.model.builders.UserBuilder;
import ar.edu.unq.desapp.grupoo.criptop2p.model.exceptions.DataIncomingConflictException;
import ar.edu.unq.desapp.grupoo.criptop2p.model.exceptions.UserConstraintViolationException;
import ar.edu.unq.desapp.grupoo.criptop2p.model.exceptions.UserNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("UserService Tests")
@SpringBootTest
class UserServiceTest {
    static class UserBuilderUnique extends UserBuilder {
        public UserBuilderUnique() {
            super("aaa","bbb","c@d.e","fghijklmno", "Pqrs7$", "12345678","1234567890123456789012");
        }

        @Override
        public UserBuilder withEmail(String email){
            this.withWalletAddress(email.repeat(2).substring(0,8));
            return super.withEmail(email);
        }
    }

    UserBuilder anyUser = new UserBuilderUnique();
    UserBuilder otherUser = new UserBuilderUnique();

    User user;

    UserConstraintViolationException userConstraintViolationException;
    @Autowired
    UserService userService;

    @DisplayName("When an User is correctly formed is added")
    @Test
    void addAnUser(){
        user = anyUser.withEmail("just@add.once").build();

        userService.addUser(user);

        assertNotNull(user.getId());
    }

    @DisplayName("When an User has a bad formed email throws a UserConstraintException")
    @Test
    void addAnUserWithBadFormedEmailThrowException(){
        user = anyUser.withEmail("a$b.c").build();

        userConstraintViolationException = assertThrows(
                UserConstraintViolationException.class, () -> userService.addUser(user));

        assertEquals("Email is not valid", userConstraintViolationException.getErrors().get(0) );
    }

    @DisplayName("When an User has a bad formed password throws a UserConstraintException")
    @Test
    void addAnUserWithBadFormedPasswordThrowException(){
        user = anyUser.withPassword("123456").build();

        userConstraintViolationException = assertThrows(
                UserConstraintViolationException.class, () -> userService.addUser(user));

        assertTrue(userConstraintViolationException.getErrors().get(0).contains("Password must contain:") );
    }

    @DisplayName("When an User has a bad formed Address throws a UserConstraintException")
    @Test
    void addAnUserWithBadFormedAddressThrowException(){
        user = anyUser.withAddress("Here 234").build();

        userConstraintViolationException = assertThrows(
                UserConstraintViolationException.class, () -> userService.addUser(user));

        assertEquals("Address must have between 10 and 30 characters",
                      userConstraintViolationException.getErrors().get(0) );
    }

    @DisplayName("When an User has a bad formed WalletAddress throws a UserConstraintException")
    @Test
    void addAnUserWithBadFormedWalletAddressThrowException(){
        user = anyUser.withWalletAddress("1234567").build();

        userConstraintViolationException = assertThrows(
                UserConstraintViolationException.class, () -> userService.addUser(user));

        assertEquals("Wallet Address must have 8 characters", userConstraintViolationException.getErrors().get(0) );
    }

    @DisplayName("When an User has a bad formed CVU throws a UserConstraintException")
    @Test
    void addAnUserWithBadFormedCVUThrowException(){
        user = anyUser.withCvu("abcdefghijKLMNOPQRST1").build();

        userConstraintViolationException = assertThrows(
                UserConstraintViolationException.class, () -> userService.addUser(user));

        assertEquals("CVU must have 22 characters", userConstraintViolationException.getErrors().get(0) );
    }

    @DisplayName("Finding a existent User give the User")
    @Test
    void findExistentUser(){
        String name = "Pete";
        user = anyUser.withEmail("pete@here.dom").withName(name).build();
        userService.addUser(user);

        assertEquals(name, userService.findByID(user.getId()).getName());
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
        User registeredUser = anyUser.withEmail("me@here.throw").withWalletAddress("aaaaaaaa").build();
        userService.addUser(registeredUser);
        DataIncomingConflictException dataIncomingConflictException =
                assertThrows(DataIncomingConflictException.class , () -> userService.addUser(otherUser.withEmail("me@here.throw").withWalletAddress("xxxxxxxx").build()));
        assertEquals("The operation can not be completed due to data conflict",
                dataIncomingConflictException.getError());
    }
    @DisplayName("Delete an User by id")
    @Test
    void deleteUserById(){
        user = anyUser.withEmail("tobe@delete.soon").withWalletAddress("00000000").withCvu("0000 0000 0000 0000 00").build();
        userService.addUser(user);
        Long id = user.getId();

        userService.deleteUserById(id);

        UserNotFoundException userNotFoundException = assertThrows(UserNotFoundException.class, () -> userService.findByID(id));

        assertEquals("Could not find user " + id.toString(), userNotFoundException.getMessage());
    }
}