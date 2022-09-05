package ar.edu.unq.desapp.grupoo.criptop2p.webservice;

import ar.edu.unq.desapp.grupoo.criptop2p.model.User;
import ar.edu.unq.desapp.grupoo.criptop2p.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@EnableAutoConfiguration
public class UserRestController {

    @Autowired
    private UserService userService;

    @PostMapping("/users")
    public User register(@RequestBody User user) {
        this.userService.addUser(user);
        return this.userService.findByID(user.getId());
    }
}
