package ar.edu.unq.desapp.grupoo.criptop2p.webservice;

import ar.edu.unq.desapp.grupoo.criptop2p.model.User;
import ar.edu.unq.desapp.grupoo.criptop2p.service.UserService;
import ar.edu.unq.desapp.grupoo.criptop2p.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@EnableAutoConfiguration
public class UserRestController {

    static UserService userService;
    @Autowired
    public UserRestController(UserService anUserServiceImpl){
        this.userService = anUserServiceImpl;
    }

    @PostMapping("/users")
    public User register(@RequestBody User user) {
        userService.save(user);
        return user;
    }
}
