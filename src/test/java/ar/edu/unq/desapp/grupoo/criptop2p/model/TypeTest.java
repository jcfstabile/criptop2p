package ar.edu.unq.desapp.grupoo.criptop2p.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Type Tests")
@SpringBootTest
public class TypeTest {
    User anUser;
    @BeforeEach
    void setUp(){
        anUser = new User("Jim", "Ken", "jk@here.dom", "1234567890", "Pepito12!", "12345678", "1111111111111111111111");
    }

    @Test
    void testTypeShellReturnTheUsersCVU(){
        assertEquals(anUser.getCvu(), Type.SELL.shippingAddress(anUser));
    }

    @Test
    void testTypeBuyReturnTheUsersCVU(){
        assertEquals(anUser.getWalletAddress(), Type.BUY.shippingAddress(anUser));
    }
}
