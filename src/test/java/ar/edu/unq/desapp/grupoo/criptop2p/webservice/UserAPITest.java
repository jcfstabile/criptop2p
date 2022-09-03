package ar.edu.unq.desapp.grupoo.criptop2p.webservice;

import ar.edu.unq.desapp.grupoo.criptop2p.model.User;
import ar.edu.unq.desapp.grupoo.criptop2p.persistence.UserDAO;
import ar.edu.unq.desapp.grupoo.criptop2p.persistence.UserDAOImpl;
import ar.edu.unq.desapp.grupoo.criptop2p.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;


@DisplayName("API Tests")
@SpringBootTest
class APITest {
    static User anUser;
    static UserRestController anUserRestController;
    static UserServiceImpl anUserService;
    static UserDAOImpl anUserDAO;

    @BeforeAll
    public static void setUp(){
        anUser = new User("Jim", "Ken", "jk@here.dom", "there 123", "1234", "12345678", "22*1");
        anUserDAO = new UserDAOImpl();
        anUserService = new UserServiceImpl(anUserDAO);
        anUserRestController = new UserRestController(anUserService);
    }

    @DisplayName("A User can be registed")
    @Test
    void userRegister(){
        User getUser = anUserRestController.register(anUser);
        assertNotNull(getUser);
        assertEquals(anUser.id, getUser.id);
        assertEquals(anUser.name, getUser.name);
        assertEquals(anUser.email, getUser.email);
    }
}
