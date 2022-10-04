package ar.edu.unq.desapp.grupoo.criptop2p.integrations;

import ar.edu.unq.desapp.grupoo.criptop2p.model.CryptoName;
import ar.edu.unq.desapp.grupoo.criptop2p.utils.Integrator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

public class BinanceIntegration extends Integrator {
    public BinanceIntegration() {
        super("https://testnet.binance.vision/api/v3/");
    }

    public String priceOf(CryptoName aCryptoName) {
        String resource = "avgPrice?symbol=" + aCryptoName.name();
        return this.query(String.class, this.completeUrl(resource), "price");
    }

    public String check() {
        return this.query(String.class, this.completeUrl("ping"), "result");
    }

    private String completeUrl(String parameters){
        return this.resourceUrl + parameters;
    }
}
