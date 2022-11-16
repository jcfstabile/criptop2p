package ar.edu.unq.desapp.grupoo.criptop2p;

import static org.assertj.core.api.Assertions.assertThat;

import ar.edu.unq.desapp.grupoo.criptop2p.model.CryptoName;
import ar.edu.unq.desapp.grupoo.criptop2p.webservice.UserRestController;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.math.BigDecimal;

@DisplayName("User end-to_end Test")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class UsersEndToEndTest {

    private static final String HTTP_LOCALHOST = "http://localhost:";

    @Value("${local.server.port}")
    private int port;

    @Autowired
    private UserRestController controller;

    @Autowired
    private TestRestTemplate restTemplate;

    @DisplayName("UserRestController is up")
    @Test
    void contextLoads() {
        assertThat(controller).isNotNull();
    }

    @DisplayName("Server has a user just registered")
    @Test
    void getUsersTest() throws Exception {

        JSONObject newUserJson = new JSONObject();
        newUserJson.put("name", "Bert");
        newUserJson.put("surname", "Olagan");
        newUserJson.put("email", "b.olagan@there.dom");
        newUserJson.put("address", "Here at 1213, Earth");
        newUserJson.put("walletAddress", "HERE1234");
        newUserJson.put("cvu", "1234567890123456789012");
        newUserJson.put("password", "12345aA$");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(newUserJson.toString(), headers);

        assertThat(restTemplate.postForObject(HTTP_LOCALHOST + port + "/api/users", request, String.class))
                .contains("id").contains("1")
                .contains("name").contains("surname");

        assertThat(restTemplate.getForObject(HTTP_LOCALHOST + port + "/api/users", String.class))
                .contains("id")
                .contains("name")
                .contains("surname")
                .contains("operationCount")
                .contains("reputation");
    }

    @DisplayName("Server has an intention offers by a user just registered")
    @Test
    void testPostIntnetionByUser() throws JSONException {
        JSONObject newUserJson = new JSONObject();
        newUserJson.put("name", "Bort");
        newUserJson.put("surname", "Simpsons");
        newUserJson.put("email", "bort@there.dom");
        newUserJson.put("address", "Here at 1213, Earth");
        newUserJson.put("walletAddress", "HHRE1234");
        newUserJson.put("cvu", "6634567890123456789016");
        newUserJson.put("password", "12345aA$");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(newUserJson.toString(), headers);


        assertThat(restTemplate.postForObject(HTTP_LOCALHOST + port + "/api/users", request, String.class))
                .contains("id").contains("1")
                .contains("name").contains("surname");

        assertThat(restTemplate.getForObject(HTTP_LOCALHOST + port + "/api/users", String.class))
                .contains("id")
                .contains("name")
                .contains("surname")
                .contains("operationCount")
                .contains("reputation");


        JSONObject newIntentionJson = new JSONObject();
        //int aCount, BigDecimal aPrice, String aType, CryptoName aCryptoName
        newIntentionJson.put("count", "1");
        newIntentionJson.put("type", "SELL");
        newIntentionJson.put("cryptoName", "ALICEUSDT");



    }


}