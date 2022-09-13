package ar.edu.unq.desapp.grupoo.criptop2p.webservice;

import ar.edu.unq.desapp.grupoo.criptop2p.model.Intention;
import ar.edu.unq.desapp.grupoo.criptop2p.model.User;
import ar.edu.unq.desapp.grupoo.criptop2p.model.dto.IntentionDTO;
import ar.edu.unq.desapp.grupoo.criptop2p.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@EnableAutoConfiguration
public class UserRestController {

    @Autowired
    private UserService userService;

    @PostMapping("/users")
    public User register(@Valid @RequestBody User user) {
        this.userService.addUser(user);
        return this.userService.findByID(user.getId());
    }

    @GetMapping("/users/{anId}")
    public User findUserById(@PathVariable Long anId) {
        return this.userService.findByID(anId);
    }

    @PostMapping("/users/{anId}")
    public Intention ofter(@PathVariable Long anId, @RequestBody IntentionDTO anIntentionDTO) {
        return this.userService.offer(anId, anIntentionDTO);
    }
}
