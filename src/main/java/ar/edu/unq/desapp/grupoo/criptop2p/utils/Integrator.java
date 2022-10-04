package ar.edu.unq.desapp.grupoo.criptop2p.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import java.io.IOException;

abstract public class Integrator {
    private RestTemplate restTemplate = new RestTemplate();
    protected String resourceUrl;
    private ObjectMapper mapper = new ObjectMapper();

    public Integrator(String aResourceUrl){
        this.resourceUrl = aResourceUrl;
    }

    protected String query(Class aClass, String anUrl, String field){
        ResponseEntity<String> response = this.response(anUrl, aClass);
        JsonNode root = null;
        try {
            root = mapper.readTree(response.getBody());
        } catch(IOException ex) {
            throw new RuntimeException(ex);
        }
        JsonNode value = root.path(field);
        return value.asText();
    }

    private ResponseEntity<String> response(String anUrl, Class aClass){
        return restTemplate.getForEntity(anUrl, aClass);
    }
}
