package ar.edu.unq.desapp.grupoo.criptop2p.persistence;

import ar.edu.unq.desapp.grupoo.criptop2p.model.Intention;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Configuration
public interface IntentionRepository extends CrudRepository<Intention, Long> {
    @Override
    Optional<Intention> findById(Long id);
}
