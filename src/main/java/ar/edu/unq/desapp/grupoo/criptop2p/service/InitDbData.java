package ar.edu.unq.desapp.grupoo.criptop2p.service;

import ar.edu.unq.desapp.grupoo.criptop2p.model.builders.UserCreationDTOBuilder;
import ar.edu.unq.desapp.grupoo.criptop2p.service.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.PostConstruct;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Service
@Transactional
@PropertySource("classpath:application.properties")
public class InitDbData {

    protected final Log logger = LogFactory.getLog(getClass());

    UserCreationDTOBuilder userBuilder;

    @Value("${spring.datasource.driverClassName:NONE}")
    private  String driverClassName;

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
        driverClassName = "org.h2.Driver"; // REFACTOR to profiles
        logger.warn("Setting driverClassName with test propose with: " + driverClassName );
    }

    @PostConstruct
    void init(){
        if(driverClassName.equals("org.h2.Driver")) {
            userService.addUser(userBuilder.withEmail("email1@init.data").withWalletAddress("IDD09871").build());
            userService.addUser(userBuilder.withEmail("email2@init.data").withWalletAddress("IDD09872").build());
            userService.addUser(userBuilder.withEmail("email3@init.data").withWalletAddress("IDD09873").build());
            userService.addUser(userBuilder.withName("Alf").withEmail("email4@init.data").withWalletAddress("IDD09874").build());
            logger.info("Added fake Users on H2 with: " + driverClassName );
        }
    }
}
