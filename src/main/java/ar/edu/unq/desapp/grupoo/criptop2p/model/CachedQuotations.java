package ar.edu.unq.desapp.grupoo.criptop2p.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.*;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name="cached_quotations")
public class CachedQuotations {

//    @Id
//    @GeneratedValue
//    Long id;

    @Id
    @Column(name="time_stamp", columnDefinition = "TIMESTAMP")
    private Timestamp timeStamp;

    @Lob
    @Column(name="quotations", columnDefinition = "CLOB")
    private String quotationsBlob;

    @Transient
    private final ObjectMapper mapper = new ObjectMapper();

    protected CachedQuotations() {}

    public CachedQuotations(Timestamp timeStamp, List<Quotation> quotationList) throws JsonProcessingException, IllegalStateException {
        this.timeStamp = timeStamp;
        this.quotationsBlob = mapper.writeValueAsString(quotationList);
    }

    public Timestamp getTimeStamp() {
        return this.timeStamp;
    }

    public String getQuotationBlob() {
        return this.quotationsBlob;
    }

    public Quotation getQuotation(CryptoName cryptoName) throws IOException {
        List<Quotation> qs = Arrays.asList(mapper.readValue (this.quotationsBlob, Quotation[].class));
        return qs.stream().filter(q -> cryptoName.equals(q.getCryptoName())).findFirst().orElseThrow();
    }

}
