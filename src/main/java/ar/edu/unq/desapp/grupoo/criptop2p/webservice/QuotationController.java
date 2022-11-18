package ar.edu.unq.desapp.grupoo.criptop2p.webservice;

import ar.edu.unq.desapp.grupoo.criptop2p.service.QuotationService;
import ar.edu.unq.desapp.grupoo.criptop2p.service.dto.QuotationDTO;
import ar.edu.unq.desapp.grupoo.criptop2p.service.dto.TimedQuotationDTO;
import ar.edu.unq.desapp.grupoo.criptop2p.webservice.responses.ResponseErrorSimple;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@EnableAutoConfiguration
public class QuotationController {
    @Autowired
    QuotationService quotationService;

    @Operation(summary = "Show all current quotations",
            responses = {
                    @ApiResponse(description = "List of all current quotations", responseCode = "200",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = QuotationDTO.class))
                            )
                    )
            }
    )
    @GetMapping("/quotations")
    public ResponseEntity<List<QuotationDTO>> allQuotations() throws InterruptedException {
        return ResponseEntity.ok(quotationService.allQuotations());
    }

    @Operation(
            summary = "Show the last 24 hs quotations for a give crypto",
            responses = {
                    @ApiResponse( description = "List of quotations for a crypto on last 24hs", responseCode = "200",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = TimedQuotationDTO.class)))),
                    @ApiResponse( description = "Crypto not found", responseCode = "404",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseErrorSimple.class))),
            }
    )
    @GetMapping("/quotations/last24hs")
    public ResponseEntity<List<TimedQuotationDTO>> last24hs(@RequestParam String crypto){
        return ResponseEntity.ok(quotationService.last24hsOf(crypto));
    }
}
