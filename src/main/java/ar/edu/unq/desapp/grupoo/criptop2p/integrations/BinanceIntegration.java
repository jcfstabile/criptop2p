package ar.edu.unq.desapp.grupoo.criptop2p.utils.integrations;

import ar.edu.unq.desapp.grupoo.criptop2p.model.CryptoName;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

public class BinanceIntegration {
    RestTemplate restTemplate = new RestTemplate();
    String fooResourceUrl
            = "https://testnet.binance.vision";

    public String priceOf(CryptoName aCryptoName) {
        String crypto = aCryptoName.name();
        String api = "/api/v3/avgPrice?symbol=";
        ResponseEntity<String> response = restTemplate.getForEntity(fooResourceUrl + api + crypto, String.class);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = null;
        try {
            root = mapper.readTree(response.getBody());
        } catch(IOException ex) {
            throw new RuntimeException(ex);
        }
        JsonNode name = root.path("price");
        return name.asText();
    }

    public String check() {
        ResponseEntity<String> response = restTemplate.getForEntity(fooResourceUrl + "/api/v3/ping", String.class);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = null;
        try {
            root = mapper.readTree(response.getBody());
        } catch(IOException ex) {
            throw new RuntimeException(ex);
        }
    JsonNode name = root.path("result");
    return name.asText();
    }

}
