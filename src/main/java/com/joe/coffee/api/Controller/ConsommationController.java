package com.joe.coffee.api.Controller;

import com.joe.coffee.api.Dto.In.ConsommationDtoIn;
import com.joe.coffee.api.Dto.Out.ConsommationDtoOut;
import com.joe.coffee.api.Service.Interfaces.ConsommationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/consommations")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Consommations", description = "Gestion des consommations de café")
public class ConsommationController {

    private final ConsommationService consommationService;

    @Operation(summary = "Récupérer toutes les consommations")
    @ApiResponse(responseCode = "200", description = "Liste des consommations récupérée avec succès")
    @GetMapping
    public ResponseEntity<List<ConsommationDtoOut>> getAllConsommations() {
        log.info("Récupération de toutes les consommations");
        return ResponseEntity.ok(consommationService.getAllConsommations());
    }

    @Operation(summary = "Récupérer une consommation par son identifiant")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Consommation récupérée avec succès",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ConsommationDtoOut.class))),
            @ApiResponse(responseCode = "404", description = "Consommation non trouvée")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ConsommationDtoOut> getConsommationById(
            @Parameter(description = "Identifiant de la consommation", example = "1") @PathVariable Integer id) {
        log.info("Récupération de la consommation {}", id);
        return ResponseEntity.ok(consommationService.getConsommationById(id));
    }

    @Operation(summary = "Créer une consommation")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Consommation créée avec succès",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ConsommationDtoOut.class))),
            @ApiResponse(responseCode = "400", description = "Données invalides fournies"),
            @ApiResponse(responseCode = "404", description = "Café ou machine à café non trouvé")
    })
    @PostMapping
    public ResponseEntity<ConsommationDtoOut> createConsommation(
            @Valid @RequestBody @Parameter(description = "Informations de la consommation à créer") ConsommationDtoIn dto) {
        log.info("Création d'une consommation pour le café {} et la machine {}", dto.cafeId(), dto.machineACafeId());
        return ResponseEntity.status(HttpStatus.CREATED).body(consommationService.createConsommation(dto));
    }

    @Operation(summary = "Modifier une consommation")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Consommation mise à jour avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides fournies"),
            @ApiResponse(responseCode = "404", description = "Consommation, café ou machine à café non trouvé")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ConsommationDtoOut> updateConsommation(
            @Parameter(description = "Identifiant de la consommation", example = "1") @PathVariable Integer id,
            @Valid @RequestBody @Parameter(description = "Nouvelles informations de la consommation") ConsommationDtoIn dto) {
        log.info("Mise à jour de la consommation {}", id);
        return ResponseEntity.ok(consommationService.updateConsommation(id, dto));
    }

    @Operation(summary = "Supprimer une consommation")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Consommation supprimée avec succès"),
            @ApiResponse(responseCode = "404", description = "Consommation non trouvée")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConsommation(
            @Parameter(description = "Identifiant de la consommation", example = "1") @PathVariable Integer id) {
        log.info("Suppression de la consommation {}", id);
        consommationService.deleteConsommation(id);
        return ResponseEntity.noContent().build();
    }
}
