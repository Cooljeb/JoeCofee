package com.joe.coffee.api.Repository;

import com.joe.coffee.api.Entity.Commercant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommercantRepository extends JpaRepository<Commercant, Integer> {


}
