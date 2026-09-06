package com.joe.coffee.api.Controller;

import com.joe.coffee.api.Dto.In.MarqueDtoIn;
import com.joe.coffee.api.Dto.Out.MarqueDtoOut;
import com.joe.coffee.api.Service.Interfaces.MarqueService;
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

/**
 * Contrôleur des marques des machines
 * présente les différents EndPOint REST
 */
@RestController
@RequestMapping("/api/marques")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Marques", description = "Gestion des marques des machines")
public class MarqueController {
    
    private final MarqueService marqueService;

    // GET all
    @Operation(
            summary = "Récupérer toutes les marques",
            description = "Retourne la liste complète des marques de machine disponibles"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Liste des marques récupérée avec succès",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MarqueDtoOut.class)
                    )
            )
    })
    @GetMapping
    public ResponseEntity<List<MarqueDtoOut>> getAllMarques() {
        log.info("Récupération de tous les cafés");
        return ResponseEntity.ok(marqueService.getAllMarques());
    }

    // GET by id
    @Operation(
            summary = "Récupérer une marque depuis son id",
            description = "Retourne une marque depuis son id"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Marque récupérée avec succès",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MarqueDtoOut.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Marque non trouvée")
    })
    @GetMapping("/{id}")
    public ResponseEntity<MarqueDtoOut> getMarqueById(
            @Parameter(description = "Identifiant de la marque", example = "1") //swagger
            @PathVariable Integer id) {
        log.info("Récupération de la marque avec id {}", id);
        return ResponseEntity.ok(marqueService.getMarqueById(id));
    }

    // GET by name
    @Operation(
            summary = "Récupérer une marque depuis son nom",
            description = "Retourne une marque depuis son nom"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Marque récupérée avec succès",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MarqueDtoOut.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Marque non trouvée")
    })
    @GetMapping("/name/{name}")
    public ResponseEntity<MarqueDtoOut> getMarqueByName(
            @Parameter(description = "Nom de la marque", example = "DeLonghi") //swagger
            @PathVariable String name) {
        log.info("Récupération de la marque avec le nom {}", name);
        return ResponseEntity.ok(marqueService.getMarqueByName(name));
    }

    // CREATE
    @Operation(
            summary = "Créer un enregistrement de type marque",
            description = "Permet l'enregistrement d'une nouvelle marque dans la base de données"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Marque créée avec succès",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MarqueDtoOut.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Données invalides fournies")
    })
    @PostMapping
    public ResponseEntity<MarqueDtoOut> createMarque(
            @Valid
            @RequestBody
            @Parameter(description = "DTO contenant les informations de la marque à créer")
            MarqueDtoIn dto) {
        log.info("Création d'une nouvelle marque {}", dto.marque());
        return ResponseEntity.status(HttpStatus.CREATED).body(marqueService.createMarque(dto));
    }

    // UPDATE
    @Operation(
            summary = "Modifier un enregistrement de type marque",
            description = "Permet de modifier une marque sélectionnée"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Marque mise à jour avec succès",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MarqueDtoOut.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Données invalides fournies"),
            @ApiResponse(responseCode = "404", description = "Marque non trouvée")
    })
    @PutMapping("/{id}")
    public ResponseEntity<MarqueDtoOut> updateCafe(
            @Parameter(description = "Identifiant de la marque à modifier", example = "1")
            @PathVariable Integer id,
            @Valid
            @RequestBody
            @Parameter(description = "DTO contenant les nouvelles informations de la marque")
            MarqueDtoIn dto) {
        log.info("Mise à jour du café {}", id);
        return ResponseEntity.ok(marqueService.updateMarque(id, dto));
    }

    // DELETE
    @Operation(
            summary = "Supprimer un enregistrement de type marque",
            description = "Permet de supprimer une marque sélectionnée"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Marque supprimée avec succès"),
            @ApiResponse(responseCode = "404", description = "Marque non trouvée")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMarque(
            @Parameter(description = "Identifiant de la marque à supprimer", example = "1")
            @PathVariable Integer id) {
        log.info("Suppression de la marque {}", id);
        marqueService.deleteMarque(id);
        return ResponseEntity.noContent().build();
    }

}
