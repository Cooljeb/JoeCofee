package com.joe.coffee.api.Repository;

import com.joe.coffee.api.Entity.MachineACafe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository de recherche d'une machine à café
 */

@Repository
public interface MachineACafeRepository extends JpaRepository<MachineACafe, Integer> {

    Optional<MachineACafe> findByNomCommercialContainingIgnoreCase(String nomCommercial);

    Optional<MachineACafe> findByReferenceCommerciale(String referenceCommerciale);

    List<MachineACafe> findByDescriptionContainingIgnoreCase(String description);

    List<MachineACafe> findByMarqueId(Integer idMarque);

}
