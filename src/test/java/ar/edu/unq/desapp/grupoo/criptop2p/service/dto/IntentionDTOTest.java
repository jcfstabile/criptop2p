package ar.edu.unq.desapp.grupoo.criptop2p.service.dto;

import ar.edu.unq.desapp.grupoo.criptop2p.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("IntentionDTO Tests")
@SpringBootTest
@Transactional
class IntentionDTOTest {

    IntentionDTO intentionDTO;
    @BeforeEach
    void setUp() {
        intentionDTO = new IntentionDTO(
                1L,
                2,
                BigDecimal.valueOf(0.12345678),
                new Sell(),
                CryptoName.AAVEUSDT,
                34L,
                Status.OFFERED);
    }


    @DisplayName("IntentionDTO id is a Long")
    @Test
    void getIntentionId() {
        assertEquals(1L, intentionDTO.getIntentionId());
    }

    @DisplayName("IntentionDTO count is a int")
    @Test
    void getCount() {
        assertEquals(2, intentionDTO.getCount());
    }

    @DisplayName("IntentionDTO price is a String")
    @Test
    void getPrice() {
        assertEquals("0.12345678", intentionDTO.getPrice());
    }

    @DisplayName("IntentionDTO type is a TypeName")
    @Test
    void getType() {
        assertEquals(TypeName.SELL, intentionDTO.getType());
    }

    @DisplayName("IntentionDTO crypto name is a CryptoName")
    @Test
    void getCryptoName() {
        assertEquals(CryptoName.AAVEUSDT, intentionDTO.getCryptoName());
    }

    @DisplayName("IntentionDTO offered id is a Long")
    @Test
    void getOfferedId() {
        assertEquals(34L, intentionDTO.getOfferedId());
    }

    @DisplayName("IntentionDTO status is a Status")
    @Test
    void getStatus() {
        assertEquals(Status.OFFERED, intentionDTO.getStatus());
    }
}