package com.joe.coffee.api.Repository;

import com.joe.coffee.api.Entity.Consommation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository de recherche d'une consommation
 */
@Repository
public interface ConsommationRepository extends JpaRepository<Consommation, Long> {

    Optional<Consommation> findByReglageIntensite(byte intensite);

    Optional<Consommation> findByReglageIntensiteLessThanEqual(byte intensite);

}
