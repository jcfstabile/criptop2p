package ar.edu.unq.desapp.grupoo.criptop2p.webservice;

import ar.edu.unq.desapp.grupoo.criptop2p.service.QuotationService;
import ar.edu.unq.desapp.grupoo.criptop2p.service.dto.QuotationDTO;
import ar.edu.unq.desapp.grupoo.criptop2p.service.dto.TimedQuotationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api")
@EnableAutoConfiguration
public class QuotationController {
    @Autowired
    private QuotationService quotationService;

    @GetMapping("/quotations")
    public List<QuotationDTO> allQuotations() throws InterruptedException {
        return quotationService.allQuotations();
    }

    @GetMapping("/quotations/{quotationName}/last24hs")
    public List<TimedQuotationDTO> last24hs(@PathVariable String quotationName){
        System.out.println("Quotation name: " + quotationName);
        return quotationService.last24hsOf(quotationName);
    }
}
