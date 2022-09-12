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
        anUser = new User("Jim", "Ken", "jk@here.dom", "Fake Street 1234", "Pepito1234", "12345678", "1234567890123456789012");
    }

    @DisplayName("A User can be registed")
    @Test
    void userRegister(){
        User getUser = anUserRestController.register(anUser);
        assertNotNull(getUser);
        assertEquals(anUser.getId(), getUser.getId());
        assertEquals(anUser.getName(), getUser.getName());
        assertEquals(anUser.getEmail(), getUser.getEmail());
    }
}
