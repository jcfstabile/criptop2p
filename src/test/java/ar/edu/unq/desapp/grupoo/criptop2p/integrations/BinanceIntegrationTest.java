package ar.edu.unq.desapp.grupoo.criptop2p.integrations;

import ar.edu.unq.desapp.grupoo.criptop2p.model.CryptoName;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Binance Integration tests")
@SpringBootTest
@Disabled
public class BinanceIntegrationTest {

    BinanceIntegration binanceIntegrator;

    @BeforeEach
    void setUp(){
        binanceIntegrator  = new BinanceIntegration();
    }

    @DisplayName("Check connectivity with the Binance API")
    @Test
    void testCheck(){
        Assertions.assertEquals("", binanceIntegrator.check());
    }

    @DisplayName("The price of BNBUSDT is the expected")
    @Test
    void testPriceOfAProductIsTheExpected(){
        String result = binanceIntegrator.priceOf(CryptoName.BNBUSDT);
        assertNotNull(result);
        assertInstanceOf(String.class, result);
        assertNotSame("", result);
    }


}
