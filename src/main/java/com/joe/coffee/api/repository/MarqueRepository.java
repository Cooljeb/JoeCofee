package com.joe.coffee.api.repository;

import com.joe.coffee.api.entity.Marque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository pour rechercher les informations d'une marque
 */
@Repository
public interface MarqueRepository  extends JpaRepository<Marque, Long> {

    Optional<Marque> findByMarqueIgnoreCase(String marque);


}
