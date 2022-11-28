package ar.edu.unq.desapp.grupoo.criptop2p.persistence;

import ar.edu.unq.desapp.grupoo.criptop2p.model.User;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
@Configuration
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);
}
