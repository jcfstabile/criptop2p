package ar.edu.unq.desapp.grupoo.criptop2p;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import ar.edu.unq.desapp.grupoo.criptop2p.integrations.BinanceIntegration;
import ar.edu.unq.desapp.grupoo.criptop2p.model.CryptoName;
import ar.edu.unq.desapp.grupoo.criptop2p.service.dto.UserDTO;
import ar.edu.unq.desapp.grupoo.criptop2p.webservice.controllers.UserController;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;


@DisplayName("User end-to-end Test")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class UsersEndToEndTest {

    private static final String HTTP_LOCALHOST = "http://localhost:";

    private static final String TOKEN = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbkBoZXJlLmRhdGEiLCJleHAiOjE2Njk4NTk4ODUsIm5vbWJyZSI6ImFueSJ9.T8JVOnWHTcBqL5DBZsUYyEcno6s7FpO3reXSON0VnVY";
    @Value("${local.server.port}")
    private int port;

    @Autowired
    private UserController controller;

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
        headers.set("Authorization", TOKEN);
        HttpEntity<String> request = new HttpEntity<>(newUserJson.toString(), headers);

        assertThat(restTemplate.postForObject(HTTP_LOCALHOST + port + "/api/users", request, String.class))
                .contains("id")
                .contains("name").contains("surname");

        assertThat(restTemplate.exchange(HTTP_LOCALHOST + port + "/api/users", HttpMethod.GET, request, String.class).getBody())
                .contains("id")
                .contains("name")
                .contains("surname")
                .contains("operationCount")
                .contains("reputation");
    }

    @DisplayName("Server has an intention offers by a user just registered")
    @Test
    void testPostIntnetionByUser() throws JSONException {
        String price = new BinanceIntegration().priceOf(CryptoName.ALICEUSDT).getPrice();
        JSONObject newUserJson = new JSONObject();
        newUserJson.put("name", "Bort");
        newUserJson.put("surname", "Simpsons");
        newUserJson.put("email", "bort@there.dom");
        newUserJson.put("address", "Here at 1213, Earth");
        newUserJson.put("walletAddress", "HHRE1234");
        newUserJson.put("cvu", "6634567890123456789016");
        newUserJson.put("password", "12345aA$");
        HttpHeaders headers_register = new HttpHeaders();
        headers_register.setContentType(MediaType.APPLICATION_JSON);
        headers_register.set("Authorization", TOKEN);
        HttpEntity<String> request_register = new HttpEntity<>(newUserJson.toString(), headers_register);
        UserDTO anUser = restTemplate.postForObject(HTTP_LOCALHOST + port + "/api/users", request_register, UserDTO.class);

        assertEquals("Bort", anUser.getName());
        assertEquals("Simpsons", anUser.getSurname());
        assertEquals("bort@there.dom", anUser.getEmail());
        assertEquals("Here at 1213, Earth", anUser.getAddress());
        assertEquals("HHRE1234", anUser.getWalletAddress());
        assertEquals("6634567890123456789016", anUser.getCvu());


        JSONObject newIntentionJson = new JSONObject();
        newIntentionJson.put("count", "1");
        newIntentionJson.put("price", price);
        newIntentionJson.put("type", "SELL");
        newIntentionJson.put("cryptoName", "ALICEUSDT");

        HttpHeaders headers_offer = new HttpHeaders();
        headers_offer.setContentType(MediaType.APPLICATION_JSON);
        headers_offer.set("Authorization", TOKEN);
        HttpEntity<String> request_offer = new HttpEntity<>(newIntentionJson.toString(), headers_offer);
        String url = "/api/users/" + anUser.getId() + "/intentions";
        String intentionST = restTemplate.postForObject(HTTP_LOCALHOST + port + url, request_offer, String.class);

        assertThat(intentionST)
                .contains("intentionId")
                .contains("count")
                .contains("price")
                .contains("cryptoName")
                .contains("offeredId")
                .contains("status");
    }
}