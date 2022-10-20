package ar.edu.unq.desapp.grupoo.criptop2p.integrations;

import ar.edu.unq.desapp.grupoo.criptop2p.model.CryptoName;
import ar.edu.unq.desapp.grupoo.criptop2p.service.exceptions.BinanceQueryErrorException;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Binance Integration tests")
@SpringBootTest
class BinanceIntegrationTest {

    BinanceIntegration binanceIntegrator;

    @BeforeEach
    void setUp(){
        binanceIntegrator  = new BinanceIntegration();
    }

    @DisplayName("Check connectivity with the Binance API")
    @Test
    void testCheck(){
         assertEquals("", binanceIntegrator.check());
    }

    @DisplayName("The price of BNBUSDT is the expected")
    @Test
    void testPriceOfAProductIsTheExpected(){
        String result = binanceIntegrator.priceOf(CryptoName.BNBUSDT);
        assertNotNull(result);
        assertInstanceOf(String.class, result);
        assertNotSame("", result);
    }

    @DisplayName("When a binance query fail a Custom exception can be raised")
    @Test
    void existABinanceQueryErrorException(){
        BinanceQueryErrorException binanceQueryErrorException = new BinanceQueryErrorException(new IOException());
        assertNotNull(binanceQueryErrorException);
        assertNull(binanceQueryErrorException.getError());
    }

    @DisplayName("Price of ALICEUSDT")
    @Test
    void testPriceOfALICEUSDT(){
        String result = binanceIntegrator.priceOf(CryptoName.ALICEUSDT);
        assertNotNull(result);
        assertInstanceOf(String.class, result);
        assertNotSame("", result);
    }

    @DisplayName("Price of MATICUSDT")
    @Test
    void testPriceOfMATICUSDT(){
        String result = binanceIntegrator.priceOf(CryptoName.MATICUSDT);
        assertNotNull(result);
        assertInstanceOf(String.class, result);
        assertNotSame("", result);
    }

    @DisplayName("Price of AXSUSDT")
    @Test
    void testPriceOfAXSUSDT(){
        String result = binanceIntegrator.priceOf(CryptoName.AXSUSDT);
        assertNotNull(result);
        assertInstanceOf(String.class, result);
        assertNotSame("", result);
    }

    @DisplayName("Price of AAVEUSDT")
    @Test
    void testPriceOfAAVEUSDT(){
        String result = binanceIntegrator.priceOf(CryptoName.AAVEUSDT);
        assertNotNull(result);
        assertInstanceOf(String.class, result);
        assertNotSame("", result);
    }

    @DisplayName("Price of ATOMUSDT")
    @Test
    void testPriceOfATOMUSDT(){
        String result = binanceIntegrator.priceOf(CryptoName.ATOMUSDT);
        assertNotNull(result);
        assertInstanceOf(String.class, result);
        assertNotSame("", result);
    }

    @DisplayName("Price of NEOUSDT")
    @Test
    void testPriceOfNEOUSDT(){
        String result = binanceIntegrator.priceOf(CryptoName.NEOUSDT);
        assertNotNull(result);
        assertInstanceOf(String.class, result);
        assertNotSame("", result);
    }

    @DisplayName("Price of OTUSDT")
    @Test
    void testPriceOfOTUSDT(){
        String result = binanceIntegrator.priceOf(CryptoName.DOTUSDT);
        assertNotNull(result);
        assertInstanceOf(String.class, result);
        assertNotSame("", result);
    }

    @DisplayName("Price of ETHUSDT")
    @Test
    void testPriceOfETHUSDT(){
        String result = binanceIntegrator.priceOf(CryptoName.ETHUSDT);
        assertNotNull(result);
        assertInstanceOf(String.class, result);
        assertNotSame("", result);
    }

    @DisplayName("Price of CAKEUSDT")
    @Test
    void testPriceOfCAKEUSDT(){
        String result = binanceIntegrator.priceOf(CryptoName.CAKEUSDT);
        assertNotNull(result);
        assertInstanceOf(String.class, result);
        assertNotSame("", result);
    }

    @DisplayName("Price of BNBUSDT")
    @Test
    void testPriceOfBNBUSDT(){
        String result = binanceIntegrator.priceOf(CryptoName.BNBUSDT);
        assertNotNull(result);
        assertInstanceOf(String.class, result);
        assertNotSame("", result);
    }

    @DisplayName("Price of BTCUSDT")
    @Test
    void testPriceOfBTCUSDT(){
        String result = binanceIntegrator.priceOf(CryptoName.BTCUSDT);
        assertNotNull(result);
        assertInstanceOf(String.class, result);
        assertNotSame("", result);
    }

    @DisplayName("Price of ADAUSDT")
    @Test
    void testPriceOfADAUSDT(){
        String result = binanceIntegrator.priceOf(CryptoName.ADAUSDT);
        assertNotNull(result);
        assertInstanceOf(String.class, result);
        assertNotSame("", result);
    }

    @DisplayName("Price of TRXUSDT")
    @Test
    void testPriceOfTRXUSDT(){
        String result = binanceIntegrator.priceOf(CryptoName.TRXUSDT);
        assertNotNull(result);
        assertInstanceOf(String.class, result);
        assertNotSame("", result);
    }

    @DisplayName("Price of AUDIOUSDT")
    @Test
    void testPriceOfAUDIOUSDT(){
        String result = binanceIntegrator.priceOf(CryptoName.AUDIOUSDT);
        assertNotNull(result);
        assertInstanceOf(String.class, result);
        assertNotSame("", result);
    }
}
