package ar.edu.unq.desapp.grupoo.criptop2p.service;

import ar.edu.unq.desapp.grupoo.criptop2p.integrations.BinanceIntegration;
import ar.edu.unq.desapp.grupoo.criptop2p.model.CryptoName;
import ar.edu.unq.desapp.grupoo.criptop2p.model.builders.UserCreationDTOBuilder;
import ar.edu.unq.desapp.grupoo.criptop2p.service.dto.IntentionCreationDTO;
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

    String SIMPSON_ADDRESS = "742 Evergreen Terrace";
    InitDbData(){
        userBuilder = new UserCreationDTOBuilder(
                "any", "any", "email@init.data", SIMPSON_ADDRESS,
                "1234!Aa.", "12345678", "1234567890123456789012");
    }

    InitDbData(UserService anUserService){
        this();
        this.userService = anUserService;
        this.driverClassName = "org.h2.Driver"; // REFACTOR to profiles
        this.logger.warn("Setting driverClassName with test propose with: " + driverClassName );
    }

    @PostConstruct
    void init(){
        if(driverClassName.equals("org.h2.Driver")) {

            var idUser00 = userService.addUser(userBuilder
                    .withName("Apu")
                    .withSurname("Nahasapeemapetilon")
                    .withAddress("Kwik-E-Mart")
                    .withEmail("admin@here.data")
                    .withPassword("Admin1.")
                    .withWalletAddress("IDD09871")
                    .build());

            var idUser01 = userService.addUser(userBuilder
                    .withName("Marge")
                    .withSurname("Simpson")
                    .withAddress(SIMPSON_ADDRESS)
                    .withEmail("marge@init.data")
                    .withPassword("Marge1234.")
                    .withWalletAddress("IDD09872").build());

            var idUser02 = userService.addUser(userBuilder
                    .withName("Homero")
                    .withSurname("Simpson")
                    .withAddress(SIMPSON_ADDRESS)
                    .withEmail("homero@init.data")
                    .withPassword("Homero1234.")
                    .withWalletAddress("IDD09873").build());

            var idUser03 = userService.addUser(userBuilder
                    .withName("Ned")
                    .withSurname("Flanders")
                    .withAddress("744 Evergreen Terrace")
                    .withEmail("ned@init.data")
                    .withPassword("Flanders1234.")
                    .withWalletAddress("IDD09874").build());

            logger.info("Added fake Users on H2 with: " + driverClassName );

            BinanceIntegration binance = new BinanceIntegration();
            String priceBNBUSDT = binance.priceOf(CryptoName.BNBUSDT).getPrice();
            String priceALICEUSDT = binance.priceOf(CryptoName.ALICEUSDT).getPrice();
            String priceAUDIOUSDT = binance.priceOf(CryptoName.AUDIOUSDT).getPrice();
            String priceADAUSDT = binance.priceOf(CryptoName.ADAUSDT).getPrice();
            IntentionCreationDTO intention00 = new IntentionCreationDTO(1, priceBNBUSDT, "BUY", "BNBUSDT");
            IntentionCreationDTO intention01 = new IntentionCreationDTO(2, priceBNBUSDT, "BUY", "BNBUSDT");
            IntentionCreationDTO intention02 = new IntentionCreationDTO(3, priceALICEUSDT, "SELL","ALICEUSDT");
            IntentionCreationDTO intention03 = new IntentionCreationDTO(4, priceALICEUSDT, "BUY", "ALICEUSDT");
            IntentionCreationDTO intention04 = new IntentionCreationDTO(5, priceAUDIOUSDT, "SELL","AUDIOUSDT");
            IntentionCreationDTO intention05 = new IntentionCreationDTO(6, priceAUDIOUSDT, "BUY", "AUDIOUSDT");
            IntentionCreationDTO intention06 = new IntentionCreationDTO(7, priceADAUSDT, "SELL","ADAUSDT");
            IntentionCreationDTO intention07 = new IntentionCreationDTO(8, priceADAUSDT, "SELL","ADAUSDT");

            this.userService.offer(idUser00, intention00);
            this.userService.offer(idUser00, intention01);
            this.userService.offer(idUser01, intention02);
            this.userService.offer(idUser01, intention03);
            this.userService.offer(idUser02, intention04);
            this.userService.offer(idUser02, intention05);
            this.userService.offer(idUser03, intention06);
            this.userService.offer(idUser03, intention07);

            logger.info("Added fake Intentions on H2 with: " + driverClassName );

        }
    }
}
