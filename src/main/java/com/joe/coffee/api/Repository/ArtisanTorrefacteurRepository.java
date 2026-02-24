package com.joe.coffee.api.Repository;

import com.joe.coffee.api.Entity.ArtisanTorrefacteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository de recherche d'un artisan torréfacteur
 */
@Repository
public interface ArtisanTorrefacteurRepository extends JpaRepository<ArtisanTorrefacteur, Long> {

    Optional<ArtisanTorrefacteur> findByNomIgnoreCase(String nom);

}
