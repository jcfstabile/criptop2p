package ar.edu.unq.desapp.grupoo.criptop2p.webservice.mappers;

import ar.edu.unq.desapp.grupoo.criptop2p.model.User;
import ar.edu.unq.desapp.grupoo.criptop2p.model.dto.UserCreationDTO;
import ar.edu.unq.desapp.grupoo.criptop2p.model.dto.UserDTO;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserDTO toDto(User user) {
        return new UserDTO( user.getId(), user.getName(), user.getSurname(), user.getEmail(), user.getAddress(), user.getWalletAddress(), user.getCvu());
    }

    public User toUser(UserCreationDTO userCreationDTO){
        return new User(userCreationDTO.getName(), userCreationDTO.getSurname(), userCreationDTO.getEmail(), userCreationDTO.getAddress(), userCreationDTO.getPassword(), userCreationDTO.getWalletAddress(), userCreationDTO.getCvu());
    }
}
