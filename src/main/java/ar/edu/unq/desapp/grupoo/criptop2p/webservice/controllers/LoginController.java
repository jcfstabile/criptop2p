package ar.edu.unq.desapp.grupoo.criptop2p.webservice.controllers;

import ar.edu.unq.desapp.grupoo.criptop2p.service.LoginService;
import ar.edu.unq.desapp.grupoo.criptop2p.service.dto.UserDTO;
import ar.edu.unq.desapp.grupoo.criptop2p.service.dto.UserLoginDTO;
import ar.edu.unq.desapp.grupoo.criptop2p.webservice.responses.ResponseErrorList;
import ar.edu.unq.desapp.grupoo.criptop2p.webservice.responses.ResponseErrorSimple;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@EnableAutoConfiguration
public class LoginController {

    @Autowired
    LoginService loginService;

    @Operation(
            summary = "Register an user",
            responses = {
                    @ApiResponse( description = "Login sucessed", responseCode = "201",
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

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginDTO userLoginDTO ){
        String bearerToken = loginService.authenticateUser(userLoginDTO);
        return ResponseEntity.ok().header("Authorization", bearerToken).body("Ok");
    }

    @GetMapping("/ping")
    public ResponseEntity<String> ping(){
        var auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok("Ping:" + auth.getPrincipal() + ":"+ auth.isAuthenticated());
    }
}
