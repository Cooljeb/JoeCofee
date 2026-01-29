package com.joe.coffee.api.repository;

import com.joe.coffee.api.entity.MachineACafe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository de recherche d'une machine à café
 */

@Repository
public interface MachineACafeRepository extends JpaRepository<MachineACafe, Long> {

    Optional<MachineACafe> findByNomCommercial(String nomCommercial);

    Optional<MachineACafe> findByReferenceCommerciale(String referenceCommerciale);

    List<MachineACafe> findByDescriptionContainingIgnoreCase(String description);

    List<MachineACafe> findByMarque(Long marque);

}
