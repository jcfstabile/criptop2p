package ar.edu.unq.desapp.grupoo.criptop2p.webservice;

import ar.edu.unq.desapp.grupoo.criptop2p.service.dto.UserLoginDTO;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@EnableAutoConfiguration
@EnableWebSecurity
public class LoginController {


    @PostMapping("/login")
    public ResponseEntity<Authentication> login(@RequestBody UserLoginDTO userLoginDTO ){
        System.out.println("login:" + userLoginDTO.getUser() + ":" + userLoginDTO.getPassword());
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userLoginDTO.getUser(), userLoginDTO.getPassword());
        Authentication authentication = null; // authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        return ResponseEntity.ok(authentication);
    }

    @GetMapping("/ping")
    public ResponseEntity<String> ping(){
        System.out.println("ping");
        var auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok("Ping:" + auth.getPrincipal() + ":"+ auth.isAuthenticated());
    }
}
