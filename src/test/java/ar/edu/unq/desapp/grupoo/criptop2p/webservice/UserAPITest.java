package ar.edu.unq.desapp.grupoo.criptop2p.webservice;

import ar.edu.unq.desapp.grupoo.criptop2p.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

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


    @BeforeAll
    public static void setUp(){
        anUser = new User("Jim", "Ken", "jk@here.dom", "there 123", "1234", "12345678", "22*1");
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
