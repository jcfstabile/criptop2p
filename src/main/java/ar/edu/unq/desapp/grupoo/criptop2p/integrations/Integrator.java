package ar.edu.unq.desapp.grupoo.criptop2p.integrations;

import ar.edu.unq.desapp.grupoo.criptop2p.service.exceptions.BinanceQueryErrorException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import java.io.IOException;

public abstract class Integrator {
    private final RestTemplate restTemplate;
    protected final String resourceUrl;
    private final ObjectMapper mapper;

    protected Integrator(String aResourceUrl){
        this.resourceUrl = aResourceUrl;
        mapper = new ObjectMapper();
        restTemplate = new RestTemplate();
    }

    protected String query(String anUrl, String field){
        ResponseEntity<String> response = this.response(anUrl);
        JsonNode root;
        try {
            root = mapper.readTree(response.getBody());
        } catch(IOException ex) {
            throw new BinanceQueryErrorException(ex);
        }
        JsonNode value = root.path(field);
        return value.asText();
    }

    private ResponseEntity<String> response(String anUrl){
        return restTemplate.getForEntity(anUrl, String.class);
    }
}
