package com.joe.coffee.api.Repository;

import com.joe.coffee.api.Entity.Cafe;
import com.joe.coffee.api.Enum.LabelCafe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository de recherche d'un café
 */

@Repository
public interface CafeRepository extends JpaRepository<Cafe, Long>, JpaSpecificationExecutor<Cafe> {

    Optional<Cafe> findByNomCafeIgnoreCase(String nom);

    Optional<Cafe> findByDescriptionContainingIgnoreCase(String texte);

    List<Cafe> findByCommercantId(Long commercantId);


}
