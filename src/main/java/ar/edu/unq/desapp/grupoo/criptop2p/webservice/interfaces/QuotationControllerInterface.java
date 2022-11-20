package ar.edu.unq.desapp.grupoo.criptop2p.webservice.interfaces;

import ar.edu.unq.desapp.grupoo.criptop2p.service.dto.QuotationDTO;
import ar.edu.unq.desapp.grupoo.criptop2p.service.dto.TimedQuotationDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface QuotationControllerInterface {
    ResponseEntity<List<QuotationDTO>> allQuotations() throws InterruptedException;
    ResponseEntity<List<TimedQuotationDTO>> last24hs(@RequestParam String crypto);
}
