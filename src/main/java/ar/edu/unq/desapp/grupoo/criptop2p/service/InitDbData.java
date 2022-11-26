package ar.edu.unq.desapp.grupoo.criptop2p.service;

import ar.edu.unq.desapp.grupoo.criptop2p.model.builders.UserCreationDTOBuilder;
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
                "any", "any", "email@init.data", "742 Evergreen Terrace",
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

            userService.addUser(userBuilder
                    .withName("Apu")
                    .withSurname("Nahasapeemapetilon")
                    .withAddress("Kwik-E-Mart")
                    .withEmail("admin@here.data")
                    .withPassword("Admin1.")
                    .withWalletAddress("IDD09871")
                    .build());

            userService.addUser(userBuilder
                    .withName("Marge")
                    .withSurname("Simpson")
                    .withAddress("742 Evergreen Terrace")
                    .withEmail("marge@init.data")
                    .withPassword("Marge1234.")
                    .withWalletAddress("IDD09872").build());

            userService.addUser(userBuilder
                    .withName("Homero")
                    .withSurname("Simpson")
                    .withAddress("742 Evergreen Terrace")
                    .withEmail("homero@init.data")
                    .withPassword("Homero1234.")
                    .withWalletAddress("IDD09873").build());

            userService.addUser(userBuilder
                    .withName("Ned")
                    .withSurname("Flanders")
                    .withAddress("744 Evergreen Terrace")
                    .withEmail("ned@init.data")
                    .withPassword("Flanders1234.")
                    .withWalletAddress("IDD09874").build());

            logger.info("Added fake Users on H2 with: " + driverClassName );
        }
    }

}
