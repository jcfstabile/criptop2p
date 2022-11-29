package ar.edu.unq.desapp.grupoo.criptop2p.webservice.controllers;

import ar.edu.unq.desapp.grupoo.criptop2p.service.LoginService;
import ar.edu.unq.desapp.grupoo.criptop2p.service.dto.UserLoginDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
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
            summary = "Login an user",
            responses = {
                    @ApiResponse( description = "Login authorized", responseCode = "200",
                            headers = { @Header(
                                    name = "Autorization",
                                    description = "Bearer eyEnC0dedHe4de7.eyEnC0dedj5onP4y10ad.EnC0dedVer1fy516n4ture")}
                    ),
                    @ApiResponse( description = "Unauthorized", responseCode = "401"),
            }
    )
    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody UserLoginDTO userLoginDTO ){
        return ResponseEntity.ok().header("Authorization", loginService.authenticateUser(userLoginDTO)).build();
    }

    @GetMapping("/ping")
    public ResponseEntity<String> ping(){
        var auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok("Ping:" + auth.getPrincipal() + ":"+ auth.isAuthenticated());
    }
}
