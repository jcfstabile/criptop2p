package ar.edu.unq.desapp.grupoo.criptop2p.webservice;

import ar.edu.unq.desapp.grupoo.criptop2p.model.Intention;
import ar.edu.unq.desapp.grupoo.criptop2p.model.User;
import ar.edu.unq.desapp.grupoo.criptop2p.model.dto.IntentionDTO;
import ar.edu.unq.desapp.grupoo.criptop2p.model.dto.UserCreationDTO;
import ar.edu.unq.desapp.grupoo.criptop2p.model.dto.UserDTO;
import ar.edu.unq.desapp.grupoo.criptop2p.model.dto.UserInfoDTO;
import ar.edu.unq.desapp.grupoo.criptop2p.service.UserService;
import ar.edu.unq.desapp.grupoo.criptop2p.webservice.mappers.UserMapper;
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

    @GetMapping("/users")
    public ResponseEntity<List<UserInfoDTO>> allUsers() {
        return ResponseEntity.ok(this.userService.findAll());
    }

    @PostMapping("/users")
    public ResponseEntity<UserDTO> register(@Valid @RequestBody UserCreationDTO userCreationDTO) {
        User user = mapper.toUser(userCreationDTO);
        this.userService.addUser(user);
        return ResponseEntity.ok(
                mapper.toUserDto(this.userService.findByID(user.getId()))
        );
    }

    @GetMapping("/users/{anId}")
    public ResponseEntity<UserDTO> findUserById(@PathVariable Long anId) {
        return ResponseEntity.ok(
                mapper.toUserDto(this.userService.findByID(anId))
        );
    }

    @PostMapping("/users/{anId}")
    public Intention offer(@PathVariable Long anId, @RequestBody IntentionDTO anIntentionDTO) {
        return this.userService.offer(anId, anIntentionDTO);
    }

    @DeleteMapping("/users/{anId}")
    public ResponseEntity unregister(@PathVariable Long anId) {
        this.userService.deleteUserById(anId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
