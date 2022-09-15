package ar.edu.unq.desapp.grupoo.criptop2p.webservice;

import ar.edu.unq.desapp.grupoo.criptop2p.model.User;
import ar.edu.unq.desapp.grupoo.criptop2p.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@EnableAutoConfiguration
public class UserRestController {

    @Autowired
    private UserService userService;

    @PostMapping("/users")
    public ResponseEntity<User> register(@Valid @RequestBody User user) {
        this.userService.addUser(user);
        return ResponseEntity.ok(this.userService.findByID(user.getId()));
    }

    @GetMapping("/users/{anId}")
    public ResponseEntity<User> findUserById(@PathVariable Long anId) {
        return ResponseEntity.ok(this.userService.findByID(anId));
    }
}
