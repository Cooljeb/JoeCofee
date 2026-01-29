package com.joe.coffee.api.repository;

import com.joe.coffee.api.entity.Distributeur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository de recherche d'un distributeur
 */
@Repository
public interface DistributeurRepository extends JpaRepository<Distributeur, Long> {

    Optional<Distributeur> findByNomIgnoreCase(String nom);

    List<Distributeur> findBynomDuGroupeDeDistribution(String nomDuGroupeDeDistribution);
}
