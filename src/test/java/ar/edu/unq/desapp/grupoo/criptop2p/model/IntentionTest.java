package ar.edu.unq.desapp.grupoo.criptop2p.model;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Intention Tests")
@SpringBootTest
public class IntentionTest {

    @Test
    void testAnIntentionExist(){
        User anUser = new User("Jim", "Ken", "jk@here.dom", "Fake Street 1234", "Pepito+1234", "12345678", "1234567890123456789012");
        Intention intention = new Intention(anUser, 1, 2L,Type.SELL, Crypto.ATOMUSDT);
        assertEquals(anUser, intention.getUser());
        assertEquals(1, intention.getCount());
        assertEquals(2L, intention.getPrice());
        assertEquals(Type.SELL, intention.getType());
        assertEquals(Crypto.ATOMUSDT, intention.getCrypto());
    }
}
