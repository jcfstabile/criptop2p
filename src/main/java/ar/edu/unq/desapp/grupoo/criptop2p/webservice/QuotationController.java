package ar.edu.unq.desapp.grupoo.criptop2p.webservice;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/quotation")
@EnableAutoConfiguration
public class QuotationController {
    public List<Quotation> allQuotations() {
        return new ArrayList<>();
    }
}
