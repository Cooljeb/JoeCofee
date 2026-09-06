package com.joe.coffee.api.Repository;

import com.joe.coffee.api.Entity.Distributeur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository de recherche d'un distributeur
 */
@Repository
public interface DistributeurRepository extends JpaRepository<Distributeur, Integer> {

    Optional<Distributeur>findByNomContainingIgnoreCase(String nom);

    Optional<Distributeur> findBynomDuGroupeDeDistributionContainingIgnoreCase(String nomDuGroupeDeDistribution);
}
