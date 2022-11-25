package ar.edu.unq.desapp.grupoo.criptop2p.persistence;

import ar.edu.unq.desapp.grupoo.criptop2p.model.CachedQuotations;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Configuration
public interface QuotationsRepository extends CrudRepository<CachedQuotations, Long> {
    Optional<CachedQuotations> findById(Long id);
}
