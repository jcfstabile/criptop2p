package ar.edu.unq.desapp.grupoo.criptop2p.webservice;

import ar.edu.unq.desapp.grupoo.criptop2p.model.Intention;
import ar.edu.unq.desapp.grupoo.criptop2p.service.dto.IntentionDTO;
import ar.edu.unq.desapp.grupoo.criptop2p.service.dto.UserCreationDTO;
import ar.edu.unq.desapp.grupoo.criptop2p.service.dto.UserDTO;
import ar.edu.unq.desapp.grupoo.criptop2p.service.dto.UserInfoDTO;
import ar.edu.unq.desapp.grupoo.criptop2p.service.UserService;
import ar.edu.unq.desapp.grupoo.criptop2p.webservice.responses.ResponseErrorList;
import ar.edu.unq.desapp.grupoo.criptop2p.webservice.responses.ResponseErrorSimple;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
public class UserRestController {

    @Autowired
    private UserService userService;



    @Operation(
            summary = "List of registered users",
            responses = {
                    @ApiResponse( responseCode = "200", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = UserInfoDTO.class)))),
            }
    )
    @GetMapping("/users")
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
    public ResponseEntity<UserDTO> findUserById(@PathVariable Long id) {
        return ResponseEntity.ok(
                this.userService.findByID(id)
        );
    }



    @Operation(
            summary = "Add an intention of buy or sell a crypto",
            responses = {
                    @ApiResponse( description = "Intention registered", responseCode = "200",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Intention.class))),
                    @ApiResponse( description = "User not found", responseCode = "404",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseErrorSimple.class))),
            }
    )
    @Parameter(name = "id", description = "Id of the user adding the intention")
    @SecurityRequirement(name = "JWT")
    @PostMapping("/users/{id}/intentions")
    public Intention offer(@PathVariable Long id, @RequestBody IntentionDTO anIntentionDTO) {
        return this.userService.offer(id, anIntentionDTO);
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
    public ResponseEntity<Void> unregister(@PathVariable Long id) {
        this.userService.deleteUserById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
