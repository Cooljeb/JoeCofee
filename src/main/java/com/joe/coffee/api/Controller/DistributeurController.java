package com.joe.coffee.api.Controller;

import com.joe.coffee.api.Dto.In.DistributeurDtoIn;
import com.joe.coffee.api.Dto.Out.DistributeurDtoOut;
import com.joe.coffee.api.Service.Interfaces.DistributeurService;
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
 * Contrôleur des distributeurs de cafés
 * présente les différents EndPOint REST
 */
@RestController
@RequestMapping("/api/distributeurs")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Distributeurs", description = "Gestion des distributeurs de cafés")
public class DistributeurController {

    private final DistributeurService DistributeurService;

    // GET all
    @Operation(
            summary = "Récupérer tous les distributeurs",
            description = "Retourne la liste complète des distributeurs de cafés disponibles"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Liste des Distributeurs récupérée avec succès",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DistributeurDtoOut.class)
                    )
            )
    })
    @GetMapping
    public ResponseEntity<List<DistributeurDtoOut>> getAllDistributeur() {
        log.info("Récupération de tous les Distributeur");
        return ResponseEntity.ok(DistributeurService.getAllDistributeur());
    }

    // GET by id
    @Operation(
            summary = "Récupérer un Distributeur depuis son id",
            description = "Retourne un Distributeur depuis son id"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Distributeur récupéré avec succès",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DistributeurDtoOut.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Distributeur non trouvé")
    })
    @GetMapping("/{id}")
    public ResponseEntity<DistributeurDtoOut> getDistributeurById(
            @Parameter(description = "Identifiant du distributeur", example = "2") //swagger
            @PathVariable Integer id) {
        log.info("Récupération du distributeur avec id {}", id);
        return ResponseEntity.ok(DistributeurService.getDistributeurById(id));
    }

    // GET by name
    @Operation(
            summary = "Récupérer un distributeur depuis son nom",
            description = "Retourne un distributeur depuis son nom"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Distributeur récupéré avec succès",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DistributeurDtoOut.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Distributeur non trouvé")
    })
    @GetMapping("/name/{name}")
    public ResponseEntity<DistributeurDtoOut> getDistributeurByName(
            @Parameter(description = "Nom du distributeur", example = "Cafés de Paris") //swagger
            @PathVariable String name) {
        log.info("Récupération du distributeur avec le nom {}", name);
        return ResponseEntity.ok(DistributeurService.getDistributeurByName(name));
    }

    // GET by distributeur name
    @Operation(
            summary = "Récupérer un distributeur depuis son groupe de distribution",
            description = "Retourne un distributeur depuis son groupe de distribution"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Distributeur récupéré avec succès",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DistributeurDtoOut.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Distributeur non trouvé")
    })
    @GetMapping("/name-distrib/{name}")
    public ResponseEntity<DistributeurDtoOut> getDistributeurByNameDistribGroup(
            @Parameter(description = "Nom du groupe de distribution du distributeur", example = "COOPERATIVE U") //swagger
            @PathVariable String name) {
        log.info("Récupération du distributeur avec le nom du groupe de distribution {}", name);
        return ResponseEntity.ok(DistributeurService.getDistributeurByNameDistribGroup(name));
    }



    // CREATE
    @Operation(
            summary = "Créer un enregistrement de type distributeur",
            description = "Permet l'enregistrement d'un nouveau distributeur dans la base de données"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Distributeur créé avec succès",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DistributeurDtoOut.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Données invalides fournies")
    })
    @PostMapping
    public ResponseEntity<DistributeurDtoOut> createDistributeur(
            @Valid
            @RequestBody
            @Parameter(description = "DTO contenant les informations du distributeur à créer")
            DistributeurDtoIn dto) {
        log.info("Création d'un nouveau distributeur {}", dto.nom());
        return ResponseEntity.status(HttpStatus.CREATED).body(DistributeurService.createDistributeur(dto));
    }

    // UPDATE
    @Operation(
            summary = "Modifier un enregistrement de type distributeur ",
            description = "Permet de modifier un Distributeur sélectionné"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Distributeur mis à jour avec succès",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DistributeurDtoOut.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Données invalides fournies"),
            @ApiResponse(responseCode = "404", description = "Distributeur non trouvé")
    })
    @PutMapping("/{id}")
    public ResponseEntity<DistributeurDtoOut> updateDistributeur(
            @Parameter(description = "Identifiant du distributeur à modifier", example = "2")
            @PathVariable Integer id,
            @Valid
            @RequestBody
            @Parameter(description = "DTO contenant les nouvelles informations du distributeur")
            DistributeurDtoIn dto) {
        log.info("Mise à jour du distributeur {}", id);
        return ResponseEntity.ok(DistributeurService.updateDistributeur(id, dto));
    }

    // DELETE
    @Operation(
            summary = "Supprimer un enregistrement de type distributeur",
            description = "Permet de supprimer un distributeur"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Distributeur supprimé avec succès"),
            @ApiResponse(responseCode = "404", description = "Distributeur non trouvé"),
            @ApiResponse(responseCode = "409", description = "Suppression NOK,au moins un café est lié à de distributeur")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDistributeur(
            @Parameter(description = "Identifiant du distributeur à supprimer", example = "2")
            @PathVariable Integer id) {
        log.info("Suppression du distributeur {}", id);
        DistributeurService.deleteDistributeur(id);
        return ResponseEntity.noContent().build();
    }
}