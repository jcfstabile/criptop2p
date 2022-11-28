package ar.edu.unq.desapp.grupoo.criptop2p.webservice.controllers;

import ar.edu.unq.desapp.grupoo.criptop2p.service.dto.*;
import ar.edu.unq.desapp.grupoo.criptop2p.service.services.UserService;
import ar.edu.unq.desapp.grupoo.criptop2p.webservice.interfaces.UserControllerInterface;
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
public class UserController implements UserControllerInterface {

    @Autowired
    private UserService userService;

    @Operation(
            summary = "List of registered users",
            responses = {
                    @ApiResponse( responseCode = "200", content = @Content(mediaType = "application/json",
                                  array = @ArraySchema(schema = @Schema(implementation = UserInfoDTO.class)))),
            }
    )
    @GetMapping("/users")
    @Override
    public ResponseEntity<List<UserInfoDTO>> allUsers() {
        return ResponseEntity.ok(this.userService.findAll());
    }
    @Operation(
            summary = "Register an user",
            responses = {
                    @ApiResponse( description = "User registered on platform", responseCode = "201",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UserDTO.class))),
                    @ApiResponse( description = "Malformed data", responseCode = "400",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseErrorList.class))),
                    @ApiResponse( description = "User already registered", responseCode = "409",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseErrorSimple.class))),
            }
    )

    @PostMapping("/users")
    @Override
    public ResponseEntity<UserDTO> register(@Valid @RequestBody UserCreationDTO userCreationDTO) {
        Long id = this.userService.addUser(userCreationDTO);
        return ResponseEntity.status(201).body(
                this.userService.findByID(id)
        );
    }

    @Operation(
            summary = "Get user information",
            responses = {
                    @ApiResponse( description = "User information for id", responseCode = "200",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UserInfoDTO.class))),
                    @ApiResponse( description = "User not found", responseCode = "404",
                            content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseErrorSimple.class))),
            }
    )
    @Parameter(name = "id", description = "Id of the user to retrieve information")
    @GetMapping("/users/{id}")
    @Override
    public ResponseEntity<UserDTO> findUserById(@PathVariable Long id) {
        return ResponseEntity.ok(
                this.userService.findByID(id)
        );
    }

    @Operation(
            summary = "Add an intention of buy or sell a crypto",
            responses = {
                    @ApiResponse( description = "Intention registered", responseCode = "201",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = IntentionDTO.class))),
                    @ApiResponse( description = "User not found", responseCode = "404",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseErrorSimple.class))),
            }
    )

    @Parameter(name = "id", description = "Id of the user adding the intention")
    @PostMapping("/users/{id}/intentions")
    @Override
    public ResponseEntity<IntentionDTO> offer(@PathVariable Long id, @RequestBody IntentionCreationDTO anIntentionDTO) {
        return ResponseEntity.status(201).body(this.userService.offer(id, anIntentionDTO));
    }

    @Operation( summary = "Remove user by id",
    responses = {
            @ApiResponse( description = "User has been deleted", responseCode = "204", content = { @Content }),
            @ApiResponse( description = "User not found", responseCode = "404",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseErrorSimple.class))),
    }
)
    @Parameter(name = "id", description = "Id of the user to delete")
    @DeleteMapping("/users/{id}")
    @Override
    public ResponseEntity<Void> unregister(@PathVariable Long id) {
        this.userService.deleteUserById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Parameter(name = "id", description = "Id of the user to retrieve activated intentions")
    @GetMapping("/users/{id}/intentions")
    @Operation(
            summary = "List of activated intentions from a given user",
            responses = {
                    @ApiResponse( responseCode = "200", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = IntentionDTO.class)))),
                    @ApiResponse( description = "User not found", responseCode = "404",
                            content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseErrorSimple.class))),
            }
    )
    @Override
    public ResponseEntity<List<IntentionDTO>> activatedIntentionsOf(@PathVariable Long id) {
        return ResponseEntity.ok(this.userService.activatedIntentionsOf(id));
    }

    @Parameter(name = "id", description = "Id of the user to retrieve intentions")
    @Parameter(name = "start", description = "Date to start, with format MM/dd/yyyy")
    @Parameter(name = "end", description = "Date to end, with format MM/dd/yyyy")
    @GetMapping("/users/between/{id}/start={start}&end={end}")
    @Operation(
            summary = "Get user form about amount operated between 2 dates",
            responses = {
                    @ApiResponse( description = "User information for id", responseCode = "200",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = FormDTO.class))),
                    @ApiResponse( description = "User not found", responseCode = "404",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseErrorSimple.class))),
                    @ApiResponse( description = "Date cant be parse", responseCode = "505",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseErrorSimple.class))),
            }
    )
    @Override
    public ResponseEntity<FormDTO> intentionsBetween(@PathVariable Long id, @PathVariable String start, @PathVariable String end) {
        return ResponseEntity.ok(this.userService.intentionsBetween(id, start, end));
    }

    @PatchMapping("users/{userId}/intentions/{intentionId}")
    @Override
    public ResponseEntity<IntentionDTO> processIntention(@PathVariable Long userId, @PathVariable Long intentionId,
                                                   @RequestParam String action){
        return ResponseEntity.status(200).body(userService.processIntention(userId, intentionId, action));

    }
}
