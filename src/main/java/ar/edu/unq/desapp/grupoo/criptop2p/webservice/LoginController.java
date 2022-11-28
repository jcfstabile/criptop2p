package ar.edu.unq.desapp.grupoo.criptop2p.webservice;

import ar.edu.unq.desapp.grupoo.criptop2p.service.LoginService;
import ar.edu.unq.desapp.grupoo.criptop2p.service.dto.UserLoginDTO;
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
