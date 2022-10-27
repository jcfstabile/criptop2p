package ar.edu.unq.desapp.grupoo.criptop2p;

import ar.edu.unq.desapp.grupoo.criptop2p.service.InitDbData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
public class Criptop2pApplication {

	public static void main(String[] args) {
		SpringApplication.run(Criptop2pApplication.class, args);
	}

}
