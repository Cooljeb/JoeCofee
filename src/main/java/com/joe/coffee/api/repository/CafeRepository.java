package com.joe.coffee.api.repository;

import com.joe.coffee.api.entity.Cafe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository de recherche d'un café
 */

@Repository
public interface CafeRepository extends JpaRepository<Cafe, Long> {

    Optional<Cafe> findByNomCafeIgnoreCase(String nom);

    Optional<Cafe> findByDescriptionContainingIgnoreCase(String texte);

    Optional<Cafe> findByLabelCafeIgnoreCase(String label);

}
