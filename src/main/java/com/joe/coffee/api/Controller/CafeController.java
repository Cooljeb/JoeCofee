package com.joe.coffee.api.Controller;

import com.joe.coffee.api.Dto.In.CafeDtoIn;
import com.joe.coffee.api.Dto.Out.CafeDtoOut;
import com.joe.coffee.api.Enum.LabelCafe;
import com.joe.coffee.api.Enum.TypeCafe;
import com.joe.coffee.api.Service.Interfaces.CafeService;
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
 * Contrôleur de café
 * présente les différents EndPOint REST
 */
@RestController
@RequestMapping("/api/cafes")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Cafés", description = "Gestion des cafés")
public class CafeController {

    private final CafeService cafeService;

    // GET all
    @Operation(
            summary = "Récupérer tous les cafés",
            description = "Retourne la liste complète des cafés disponibles"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Liste de cafés récupérée avec succès",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CafeDtoOut.class)
                    )
            )
    })
    @GetMapping
    public ResponseEntity<List<CafeDtoOut>> getAllCafes() {
        log.info("Récupération de tous les cafés");
        return ResponseEntity.ok(cafeService.getAllCafe());
    }

    // GET by id
    @Operation(
            summary = "Récupérer un café depuis son id",
            description = "Retourne un café depuis son id"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Café récupéré avec succès",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CafeDtoOut.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Café non trouvé")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CafeDtoOut> getCafeById(
            @Parameter(description = "Identifiant du café", example = "1") //swagger
            @PathVariable Integer id) {
        log.info("Récupération du café avec id {}", id);
        return ResponseEntity.ok(cafeService.getCafeById(id));
    }

    // CREATE
    @Operation(
            summary = "Créer un enregistrement de type café",
            description = "Permet l'enregistrement d'un nouveau café dans la base de données"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Café créé avec succès",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CafeDtoOut.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Données invalides fournies")
    })
    @PostMapping
    public ResponseEntity<CafeDtoOut> createCafe(
            @Valid
            @RequestBody
            @Parameter(description = "DTO contenant les informations du café à créer")
            CafeDtoIn dto) {
        log.info("Création d'un nouveau café {}", dto.nomCafe());
        return ResponseEntity.status(HttpStatus.CREATED).body(cafeService.createCafe(dto));
    }

    // UPDATE
    @Operation(
            summary = "Modifier un enregistrement de type café",
            description = "Permet de modifier un café sélectionné"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Café mis à jour avec succès",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CafeDtoOut.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Données invalides fournies"),
            @ApiResponse(responseCode = "404", description = "Café non trouvé")
    })
    @PutMapping("/{id}")
    public ResponseEntity<CafeDtoOut> updateCafe(
            @Parameter(description = "Identifiant du café à modifier", example = "1")
            @PathVariable Integer id,
            @Valid
            @RequestBody
            @Parameter(description = "DTO contenant les nouvelles informations du café")
            CafeDtoIn dto) {
        log.info("Mise à jour du café {}", id);
        return ResponseEntity.ok(cafeService.updateCafe(id, dto));
    }

    // DELETE
    @Operation(
            summary = "Supprimer un enregistrement de type café",
            description = "Permet de supprimer un café sélectionné"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Café supprimé avec succès"),
            @ApiResponse(responseCode = "404", description = "Café non trouvé")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCafe(
            @Parameter(description = "Identifiant du café à supprimer", example = "1")
            @PathVariable Integer id) {
        log.info("Suppression du café {}", id);
        cafeService.deleteCafe(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Filtrer les cafés",
            description = "Filtre les cafés par type et/ou label. Au moins un filtre doit être fourni."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des cafés correspondant aux critères"),
            @ApiResponse(responseCode = "400", description = "Aucun filtre fourni"),
            @ApiResponse(responseCode = "404", description = "Aucun café trouvé avec les critères fournis")
    })
    @GetMapping("/filter")
    public List<CafeDtoOut> filter(
            @Parameter(description = "Type de café (ex: ESPRESSO, FILTRE)")
            @RequestParam(required = false) TypeCafe type,

            @Parameter(description = "Label du café (ex: BIO, FAIR_TRADE)")
            @RequestParam(required = false) LabelCafe label) {

        return cafeService.filterCafes(type, label);
    }

    @Operation(summary = "Récupère tous les cafés pour un commerçant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cafés trouvés",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CafeDtoOut.class))),
            @ApiResponse(responseCode = "404", description = "Aucun café trouvé pour ce commerçant",
                    content = @Content)
    })
    @GetMapping("/by-commercant/{commercantId}")
    public ResponseEntity<List<CafeDtoOut>> getCafesByCommercant(
            @PathVariable("commercantId") Integer commercantId) {
        List<CafeDtoOut> cafes = cafeService.getCafesByCommercantId(commercantId);
        return ResponseEntity.ok(cafes);
    }

}
