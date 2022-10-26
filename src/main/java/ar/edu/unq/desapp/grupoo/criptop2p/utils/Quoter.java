package ar.edu.unq.desapp.grupoo.criptop2p.utils;

import ar.edu.unq.desapp.grupoo.criptop2p.integrations.Integrator;
import ar.edu.unq.desapp.grupoo.criptop2p.service.exceptions.BinanceQueryErrorException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.math.BigDecimal;

public class Quoter{

    private final RestTemplate restTemplate;
    protected final String resourceUrl;
    private final ObjectMapper mapper;

    public Quoter() {
        this.resourceUrl = "https://www.dolarsi.com/api/api.php?type=valoresprincipales";
        mapper = new ObjectMapper();
        restTemplate = new RestTemplate();
    }

    public BigDecimal quotationOfUsd(){
        ResponseEntity<String> response = restTemplate.getForEntity(this.resourceUrl, String.class);;
        JsonNode root;
        try {
            root = mapper.readTree(response.getBody());
        }
        catch (IOException ex){
            throw new RuntimeException("");
        }
        JsonNode value = root.path(0);
        String quotation= value.path("casa").path("compra").asText().replaceAll(",", ".");
        return new BigDecimal(quotation);
    }

}
