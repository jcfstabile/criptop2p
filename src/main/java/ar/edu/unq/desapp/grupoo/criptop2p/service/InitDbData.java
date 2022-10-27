package ar.edu.unq.desapp.grupoo.criptop2p.service;

import ar.edu.unq.desapp.grupoo.criptop2p.model.builders.UserCreationDTOBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class InitDbData {

    UserCreationDTOBuilder userBuilder;

    @Autowired
    UserService userService;

    InitDbData(){
        userBuilder = new UserCreationDTOBuilder(
                "any", "any", "email@init.data", "Address of init data",
                "1234!Aa.", "12345678", "1234567890123456789012");
    }

    InitDbData(UserService anUserService){
        this();
        userService = anUserService;
    }

    @PostConstruct
    void init(){
        userService.addUser(userBuilder.withEmail("email1@init.data").withWalletAddress("IDD09871").build());
        userService.addUser(userBuilder.withEmail("email2@init.data").withWalletAddress("IDD09872").build());
        userService.addUser(userBuilder.withEmail("email3@init.data").withWalletAddress("IDD09873").build());
        userService.addUser(userBuilder.withEmail("email4@init.data").withWalletAddress("IDD09874").build());
    }

}
