package com.joe.coffee.api.Controller;

import com.joe.coffee.api.Dto.In.ArtisanTorrefacteurDtoIn;
import com.joe.coffee.api.Dto.Out.ArtisanTorrefacteurDtoOut;
import com.joe.coffee.api.Service.Interfaces.ArtisanTorrefacteurService;
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
 * Contrôleur des artisans Torréfacteurs de cafés
 * présente les différents EndPOint REST
 */
@RestController
@RequestMapping("/api/artisanTorrefacteur")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Artisans Torréfacteurs", description = "Gestion des des artisans Torréfacteurs de cafés")
public class ArtisanTorrefacteurController {

    private final ArtisanTorrefacteurService artisanTorrefacteurService;

    // GET all
    @Operation(
            summary = "Récupérer tous les artisans torréfacteurs",
            description = "Retourne la liste complète des artisans torréfacteurs (AT) de cafés disponibles"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Liste des AT récupérée avec succès",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ArtisanTorrefacteurDtoOut.class)
                    )
            )
    })
    @GetMapping
    public ResponseEntity<List<ArtisanTorrefacteurDtoOut>> getAllArtisanTorrefacteur() {
        log.info("Récupération de tous les AT");
        return ResponseEntity.ok(artisanTorrefacteurService.getAllArtisanTorrefacteur());
    }

    // GET by id
    @Operation(
            summary = "Récupérer un AT depuis son id",
            description = "Retourne un AT depuis son id"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "AT récupéré avec succès",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ArtisanTorrefacteurDtoOut.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Artisan non trouvée")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ArtisanTorrefacteurDtoOut> getArtisanTorrefacteurById(
            @Parameter(description = "Identifiant de l'AT", example = "2") //swagger
            @PathVariable Integer id) {
        log.info("Récupération de l'artisan avec id {}", id);
        return ResponseEntity.ok(artisanTorrefacteurService.getArtisanTorrefacteurById(id));
    }

    // GET by name
    @Operation(
            summary = "Récupérer un artisan depuis son nom",
            description = "Retourne un artisan depuis son nom"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "AT récupéré avec succès",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ArtisanTorrefacteurDtoOut.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "AT non trouvé")
    })
    @GetMapping("/name/{name}")
    public ResponseEntity<ArtisanTorrefacteurDtoOut> getArtisanTorrefacteurByName(
            @Parameter(description = "Nom de l'AT", example = "Lyon Torréfaction") //swagger
            @PathVariable String name) {
        log.info("Récupération de l'artisan avec le nom {}", name);
        return ResponseEntity.ok(artisanTorrefacteurService.getArtisanTorrefacteurByName(name));
    }

    // CREATE
    @Operation(
            summary = "Créer un enregistrement de type Artisan torréfacteur (AT)",
            description = "Permet l'enregistrement d'un nouvel artisan torréfacteur dans la base de données"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "AT créé avec succès",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ArtisanTorrefacteurDtoOut.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Données invalides fournies")
    })
    @PostMapping
    public ResponseEntity<ArtisanTorrefacteurDtoOut> createArtisanTorrefacteur(
            @Valid
            @RequestBody
            @Parameter(description = "DTO contenant les informations de l' AT à créer")
            ArtisanTorrefacteurDtoIn dto) {
        log.info("Création d'une nouvel at {}", dto.nom());
        return ResponseEntity.status(HttpStatus.CREATED).body(artisanTorrefacteurService.createArtisanTorrefacteur(dto));
    }

    // UPDATE
    @Operation(
            summary = "Modifier un enregistrement de type Artisan torréfacteur (AT)",
            description = "Permet de modifier un AT sélectionné"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "AT mis à jour avec succès",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ArtisanTorrefacteurDtoOut.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Données invalides fournies"),
            @ApiResponse(responseCode = "404", description = "AT non trouvé")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ArtisanTorrefacteurDtoOut> updateArtisanTorrefacteur(
            @Parameter(description = "Identifiant de l'AT à modifier", example = "2")
            @PathVariable Integer id,
            @Valid
            @RequestBody
            @Parameter(description = "DTO contenant les nouvelles informations de l'AT")
            ArtisanTorrefacteurDtoIn dto) {
        log.info("Mise à jour de l'AT {}", id);
        return ResponseEntity.ok(artisanTorrefacteurService.updateArtisanTorrefacteur(id, dto));
    }

    // DELETE
    @Operation(
            summary = "Supprimer un enregistrement de type artisan torréfacteur",
            description = "Permet de supprimer un artisan torréfacteur"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "AT supprimé avec succès"),
            @ApiResponse(responseCode = "404", description = "AT non trouvé"),
            @ApiResponse(responseCode = "409", description = "Suppression NOK,au moins un café est lié à l'AT")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArtisanTorrefacteur(
            @Parameter(description = "Identifiant de l'AT à supprimer", example = "2")
            @PathVariable Integer id) {
        log.info("Suppression de l'AT {}", id);
        artisanTorrefacteurService.deleteArtisanTorrefacteur(id);
        return ResponseEntity.noContent().build();
    }


}
