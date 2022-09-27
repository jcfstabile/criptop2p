package ar.edu.unq.desapp.grupoo.criptop2p.webservice;

import ar.edu.unq.desapp.grupoo.criptop2p.model.Intention;
import ar.edu.unq.desapp.grupoo.criptop2p.model.User;
import ar.edu.unq.desapp.grupoo.criptop2p.model.dto.IntentionDTO;
import ar.edu.unq.desapp.grupoo.criptop2p.model.dto.UserCreationDTO;
import ar.edu.unq.desapp.grupoo.criptop2p.model.dto.UserDTO;
import ar.edu.unq.desapp.grupoo.criptop2p.model.dto.UserInfoDTO;
import ar.edu.unq.desapp.grupoo.criptop2p.service.UserService;
import ar.edu.unq.desapp.grupoo.criptop2p.webservice.mappers.UserMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Autowired
    private UserMapper mapper;

    @Operation( description = "List of users on platform")
    @ApiResponses(value = {@ApiResponse(responseCode = "200")})
    @GetMapping("/users")
    public ResponseEntity<List<UserInfoDTO>> allUsers() {
        return ResponseEntity.ok(this.userService.findAll());
    }

    // TODO
    @Operation(
            description = "Register an user on platform"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    description = "User registered on platform",
                    responseCode = "200"),
            @ApiResponse(
                    description = "Malformed data",
                    responseCode = "400"
            )})
    @PostMapping("/users")
    public ResponseEntity<UserDTO> register(@Valid @RequestBody UserCreationDTO userCreationDTO) {
        User user = mapper.toUser(userCreationDTO);
        this.userService.addUser(user);
        return ResponseEntity.ok(
                mapper.toUserDto(this.userService.findByID(user.getId()))
        );
    }

    @Operation( description = "Get user information")
    @Parameter(name = "anId", description = "Id of the user to retrieve information")
    @ApiResponses(value = {@ApiResponse(responseCode = "200"), @ApiResponse(responseCode = "404")})
    @GetMapping("/users/{anId}")
    public ResponseEntity<UserDTO> findUserById(@PathVariable Long anId) {
        return ResponseEntity.ok(
                mapper.toUserDto(this.userService.findByID(anId))
        );
    }

    @Operation( description = "Add an intention of buy or sell a crypto")
    @Parameter(name = "anId", description = "Id of the user adding the intention")
    @ApiResponses(value = {@ApiResponse(responseCode = "200"), @ApiResponse(responseCode = "404")})
    @PostMapping("/users/{anId}/intentions")
    public Intention offer(@PathVariable Long anId, @RequestBody IntentionDTO anIntentionDTO) {
        return this.userService.offer(anId, anIntentionDTO);
    }

    @Operation( description = "Remove user by id" )
    @Parameter(name = "anId", description = "Id of the user to delete")
    @ApiResponses(value = {@ApiResponse(responseCode = "204"), @ApiResponse(responseCode = "404")})
    @DeleteMapping("/users/{anId}")
    public ResponseEntity unregister(@PathVariable Long anId) {
        this.userService.deleteUserById(anId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
