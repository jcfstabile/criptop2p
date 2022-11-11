package ar.edu.unq.desapp.grupoo.criptop2p.webservice;

import ar.edu.unq.desapp.grupoo.criptop2p.service.IntentionService;
import ar.edu.unq.desapp.grupoo.criptop2p.service.dto.IntentionCreationDTO;
import ar.edu.unq.desapp.grupoo.criptop2p.service.dto.IntentionDTO;
import ar.edu.unq.desapp.grupoo.criptop2p.service.dto.UserCreationDTO;
import ar.edu.unq.desapp.grupoo.criptop2p.service.dto.UserDTO;
import ar.edu.unq.desapp.grupoo.criptop2p.webservice.responses.ResponseErrorList;
import ar.edu.unq.desapp.grupoo.criptop2p.webservice.responses.ResponseErrorSimple;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
@EnableAutoConfiguration
public class IntentionController {
    @Autowired
    private IntentionService intentionService;

    @Operation(
            summary = "Get intention information by ID",
            responses = {
                    @ApiResponse( description = "Intention information for id", responseCode = "200",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = IntentionDTO.class))),
                    @ApiResponse( description = "Intention not found", responseCode = "404",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseErrorSimple.class))),
            }
    )
    @Parameter(name = "id", description = "Id of the intention to search")
    @PostMapping("/intentions/{id}")
    public ResponseEntity<IntentionDTO> intentionById(@PathVariable Long id) {
        return ResponseEntity.status(200).body(this.intentionService.findById(id));
    }

    @Operation(
            summary = "List of registered intentions",
            responses = {
                    @ApiResponse( responseCode = "200", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = IntentionDTO.class)))),
            }
    )
    @GetMapping("/intentions")
    public ResponseEntity<List<IntentionDTO>> intentions() {
        return ResponseEntity.status(200).body(this.intentionService.intentions());
    }



    @GetMapping("/intentions&status={aState}")
    @Parameter(name = "state", description = "Status to filter")
    @Operation(
            summary = "Get intentions with a determinated status",
            responses = {
                    @ApiResponse( description = "Intentions with this status", responseCode = "200",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = IntentionDTO.class))),
                    @ApiResponse( description = "Incorrect status for an intention", responseCode = "404",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseErrorSimple.class))),
            }
    )
    public ResponseEntity<List<IntentionDTO>> intentionsWithState(@PathVariable String aState) {
        return ResponseEntity.status(200).body(this.intentionService.intentionsWithState(aState));
    }

    @Operation( summary = "Remove intention by id",
            responses = {
                    @ApiResponse( description = "Intention has been deleted", responseCode = "204", content = { @Content }),
                    @ApiResponse( description = "Intention not found", responseCode = "404",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseErrorSimple.class))),
            }
    )
    @Parameter(name = "id", description = "Id of the intention to delete")
    @DeleteMapping("/intentions/={id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.intentionService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(
            summary = "Save an intention",
            responses = {
                    @ApiResponse( description = "Intention registered on platform", responseCode = "201",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = IntentionDTO.class))),
                    @ApiResponse( description = "Malformed data", responseCode = "400",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseErrorList.class))),
                    @ApiResponse( description = "User already registered", responseCode = "409",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseErrorSimple.class))),
            }
    )

    @PostMapping("/intentions")
    public ResponseEntity<IntentionDTO> add(@Valid @RequestBody IntentionCreationDTO intentionDTO, UserDTO userDTO) {
        IntentionDTO intention = this.intentionService.add(intentionDTO, userDTO);
        Long id = intention.getIntentionId();
        return ResponseEntity.status(201).body(
                this.intentionService.findById(id)
        );
    }

}
