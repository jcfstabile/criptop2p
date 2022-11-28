package ar.edu.unq.desapp.grupoo.criptop2p;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@Configuration
@EnableWebSecurity
public class Criptop2pApplication {

	public static void main(String[] args) {
		SpringApplication.run(Criptop2pApplication.class, args);
	}

}
