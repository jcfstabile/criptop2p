package ar.edu.unq.desapp.grupoo.criptop2p.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@DisplayName("CachedQuotations Tests")
@SpringBootTest
class CachedQuotationsTest {

    List<Quotation> quotations;
    Timestamp timeStamp;

    Quotation quotationATOM, quotationBTC, quotationCAKE;

    @BeforeEach
    void setUp(){
        quotations = new ArrayList<>();
        timeStamp = new Timestamp(System.currentTimeMillis());
        quotationATOM = new Quotation(CryptoName.ATOMUSDT, BigDecimal.valueOf(1.2));
        quotationBTC = new Quotation(CryptoName.BTCUSDT, BigDecimal.valueOf(2.3));
        quotationCAKE = new Quotation(CryptoName.CAKEUSDT, BigDecimal.valueOf(3.4));
    }
    @DisplayName("There is a CachedQuotations with timeStamp, quotations")
    @Test
    void existCachedQuotations() throws JsonProcessingException {
        quotations.add(quotationATOM);
        CachedQuotations cq = new CachedQuotations(timeStamp, quotations);

        assertNotNull(cq.getTimeStamp());
        assertNotNull(cq.getQuotationBlob());
        assertEquals(timeStamp, cq.getTimeStamp());
        assertInstanceOf(String.class, cq.getQuotationBlob());
        assertEquals("[{\"cryptoName\":\"ATOMUSDT\",\"price\":1.2}]", cq.getQuotationBlob());
    }


    @DisplayName("Quotation with cryptoName in Blob has price and a value")
    @Test
    void quotationInBlobHasPrice() throws IOException {
        quotations.add(quotationATOM);
        quotations.add(quotationBTC);
        quotations.add(quotationCAKE);
        CachedQuotations cq = new CachedQuotations( timeStamp, quotations);

        Quotation q = cq.getQuotation(CryptoName.BTCUSDT);

        assertEquals(quotationBTC.getCryptoName(),  q.getCryptoName());
        assertEquals(quotationBTC.getPrice()     ,  q.getPrice());
    }
}