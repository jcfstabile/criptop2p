package ar.edu.unq.desapp.grupoo.criptop2p.persistence;

import ar.edu.unq.desapp.grupoo.criptop2p.model.User;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Repository;

@Configuration
@Repository
public interface UserDAO {
    public void save(User user);
}
