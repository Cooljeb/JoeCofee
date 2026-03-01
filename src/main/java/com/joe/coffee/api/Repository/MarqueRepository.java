package com.joe.coffee.api.Repository;

import com.joe.coffee.api.Entity.Marque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository pour rechercher les informations d'une marque
 */
@Repository
public interface MarqueRepository  extends JpaRepository<Marque, Integer> {

    Optional<Marque> findByMarqueIgnoreCase(String marque);


}
