package ar.edu.unq.desapp.grupoo.criptop2p.integrations;

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
        this.mapper = new ObjectMapper();
        this.restTemplate = new RestTemplate();
    }

    protected JsonNode query(String anUrl){
        ResponseEntity<String> response = this.response(anUrl);
        JsonNode root;
        try {
            root = mapper.readTree(response.getBody());
        } catch(IOException ex) {
            throw this.myException(ex);
        }
        return root;
    }

    private ResponseEntity<String> response(String anUrl){
        return restTemplate.getForEntity(anUrl, String.class);
    }
    protected String completeUrl(String parameters){
        return this.resourceUrl + parameters;
    }

    protected abstract RuntimeException myException(IOException ex);
}
