package com.joe.coffee.api.Controller;

import com.joe.coffee.api.Dto.In.CafeDtoIn;
import com.joe.coffee.api.Dto.Out.CafeDtoOut;
import com.joe.coffee.api.Service.Interfaces.CafeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contrôleur de café
 * présente les différents EndPOint REST
 */
@RestController
@RequestMapping("/api/cafes")
@RequiredArgsConstructor
@Slf4j
public class CafeController {

    private final CafeService cafeService;

    // GET all
    @GetMapping
    public ResponseEntity<List<CafeDtoOut>> getAllCafes() {
        log.info("Récupération de tous les cafés");
        return ResponseEntity.ok(cafeService.getAllCafe());
    }

    // GET by id
    @GetMapping("/{id}")
    public ResponseEntity<CafeDtoOut> getCafeById(@PathVariable Long id) {
        log.info("Récupération du café avec id {}", id);
        return ResponseEntity.ok(cafeService.getCafeById(id));
    }

    // CREATE
    @PostMapping
    public ResponseEntity<CafeDtoOut> createCafe(@Valid @RequestBody CafeDtoIn dto) {
        log.info("Création d'un nouveau café {}", dto.nomCafe());
        return ResponseEntity.status(HttpStatus.CREATED).body(cafeService.createCafe(dto));
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<CafeDtoOut> updateCafe(
            @PathVariable Long id,
            @Valid @RequestBody CafeDtoIn dto) {
        log.info("Mise à jour du café {}", id);
        return ResponseEntity.ok(cafeService.updateCafe(id, dto));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCafe(@PathVariable Long id) {
        log.info("Suppression du café {}", id);
        cafeService.deleteCafe(id);
        return ResponseEntity.noContent().build();
    }
}
