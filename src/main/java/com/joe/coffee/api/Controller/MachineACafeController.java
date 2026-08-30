package com.joe.coffee.api.Controller;

import com.joe.coffee.api.Dto.In.MachineACafeDtoIn;
import com.joe.coffee.api.Dto.Out.MachineACafeDtoOut;
import com.joe.coffee.api.Service.Interfaces.MachineACafeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Contrôleur REST des machines à café.
 */
@RestController
@RequestMapping("/api/machines-a-cafe")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Machines à café", description = "Gestion des machines à café")
public class MachineACafeController {

    private final MachineACafeService machineACafeService;

    @Operation(
            summary = "Récupérer toutes les machines à café",
            description = "Retourne la liste complète des machines à café disponibles"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Liste des machines à café récupérée avec succès",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = MachineACafeDtoOut.class))
            )
    )
    @GetMapping
    public ResponseEntity<List<MachineACafeDtoOut>> getAllMachinesACafe() {
        log.info("Récupération de toutes les machines à café");
        return ResponseEntity.ok(machineACafeService.getAllMachineACafe());
    }

    @Operation(
            summary = "Récupérer une machine à café par son identifiant",
            description = "Retourne la machine à café correspondant à l'identifiant fourni"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Machine à café récupérée avec succès",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MachineACafeDtoOut.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Machine à café introuvable", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<MachineACafeDtoOut> getMachineACafeById(
            @Parameter(description = "Identifiant de la machine à café", example = "1", required = true)
            @PathVariable Integer id) {
        log.info("Récupération de la machine à café avec l'id {}", id);
        return ResponseEntity.ok(machineACafeService.getMachineACafeById(id));
    }

    @Operation(
            summary = "Rechercher une machine à café par son nom",
            description = "Retourne la machine à café correspondant au nom commercial fourni"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Machine à café récupérée avec succès",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MachineACafeDtoOut.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Machine à café introuvable", content = @Content)
    })
    @GetMapping("/name/{name}")
    public ResponseEntity<MachineACafeDtoOut> getMachineACafeByName(
            @Parameter(description = "Nom commercial de la machine à café", example = "Magnifica S", required = true)
            @PathVariable String name) {
        log.info("Recherche de la machine à café nommée {}", name);
        return ResponseEntity.ok(machineACafeService.getMachineACafeByName(name));
    }

    @Operation(
            summary = "Créer une machine à café",
            description = "Enregistre une nouvelle machine à café"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Machine à café créée avec succès",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MachineACafeDtoOut.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Données invalides", content = @Content),
            @ApiResponse(responseCode = "409", description = "Machine à café déjà existante", content = @Content)
    })
    @PostMapping
    public ResponseEntity<MachineACafeDtoOut> createMachineACafe(
            @Valid @RequestBody
            @Parameter(description = "Informations de la machine à café à créer", required = true)
            MachineACafeDtoIn dto) {
        log.info("Création de la machine à café {}", dto.nomCommercial());
        return ResponseEntity.status(HttpStatus.CREATED).body(machineACafeService.createMachineACafe(dto));
    }

    @Operation(
            summary = "Modifier une machine à café",
            description = "Met à jour la machine à café correspondant à l'identifiant fourni"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Machine à café mise à jour avec succès",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MachineACafeDtoOut.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Données invalides", content = @Content),
            @ApiResponse(responseCode = "404", description = "Machine à café introuvable", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<MachineACafeDtoOut> updateMachineACafe(
            @Parameter(description = "Identifiant de la machine à café", example = "1", required = true)
            @PathVariable Integer id,
            @Valid @RequestBody
            @Parameter(description = "Nouvelles informations de la machine à café", required = true)
            MachineACafeDtoIn dto) {
        log.info("Mise à jour de la machine à café avec l'id {}", id);
        return ResponseEntity.ok(machineACafeService.updateMachineACafe(id, dto));
    }

    @Operation(
            summary = "Supprimer une machine à café",
            description = "Supprime la machine à café correspondant à l'identifiant fourni"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Machine à café supprimée avec succès"),
            @ApiResponse(responseCode = "404", description = "Machine à café introuvable", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMachineACafe(
            @Parameter(description = "Identifiant de la machine à café", example = "1", required = true)
            @PathVariable Integer id) {
        log.info("Suppression de la machine à café avec l'id {}", id);
        machineACafeService.deleteMachineACafe(id);
        return ResponseEntity.noContent().build();
    }
}
