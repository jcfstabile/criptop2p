package ar.edu.unq.desapp.grupoo.criptop2p.utils.integrations;


import ar.edu.unq.desapp.grupoo.criptop2p.model.CryptoName;
import ar.edu.unq.desapp.grupoo.criptop2p.model.Sell;
import ar.edu.unq.desapp.grupoo.criptop2p.model.TypeName;
import ar.edu.unq.desapp.grupoo.criptop2p.utils.TypeIntentionConverter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static ar.edu.unq.desapp.grupoo.criptop2p.model.CryptoName.BNBUSDT;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Binance Integration tests")
@SpringBootTest
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
        assertEquals("286.60000000", binanceIntegrator.priceOf(CryptoName.BNBUSDT));
    }


}
