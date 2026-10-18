package tn.esprit.tic.cce.autoloc.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tic.cce.autoloc.domain.Agence;
import tn.esprit.tic.cce.autoloc.service.IAgenceService;

import java.util.List;

@RestController
@RequestMapping("/agences")
@RequiredArgsConstructor
@Tag(name = "Agences", description = "Gestion des agences d'AutoLoc")
public class AgenceController {

    private final IAgenceService agenceService;

    @Operation(summary = "Lister les agences",
            description = "Retourne la liste complète des agences (liste vide si aucun).")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Liste retournée avec succès",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Agence.class))))
    })
    @GetMapping
    public List<Agence> retrieveAllAgences() {
        return agenceService.retrieveAllAgences();
    }

    @Operation(summary = "Consulter une agence par son identifiant",
            description = "Retourne une agence correspondant à l'identifiant fourni.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Élément trouvé",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Agence.class))),
            @ApiResponse(responseCode = "404", description = "Aucun élément avec cet identifiant",
                    content = @Content)
    })
    @GetMapping("/{idAgence}")
    public ResponseEntity<Agence> retrieveAgence(
            @Parameter(description = "Identifiant d'une agence", example = "1") @PathVariable Long idAgence) {
        Agence agence = agenceService.retrieveAgence(idAgence);
        return agence != null ? ResponseEntity.ok(agence) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Ajouter une agence",
            description = "Crée une agence. L'identifiant est généré automatiquement : ne pas le fournir.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Élément créé",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Agence.class))),
            @ApiResponse(responseCode = "400", description = "Corps de requête invalide",
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<Agence> addAgence(@RequestBody Agence agence) {
        return ResponseEntity.status(HttpStatus.CREATED).body(agenceService.addAgence(agence));
    }

    @Operation(summary = "Ajouter plusieurs agences",
            description = "Crée plusieurs agences en une seule requête.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Éléments créés",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Agence.class)))),
            @ApiResponse(responseCode = "400", description = "Corps de requête invalide",
                    content = @Content)
    })
    @PostMapping("/batch")
    public ResponseEntity<List<Agence>> addAgences(@RequestBody List<Agence> agences) {
        return ResponseEntity.status(HttpStatus.CREATED).body(agenceService.addAgences(agences));
    }

    @Operation(summary = "Modifier une agence",
            description = "Met à jour une agence existant. L'identifiant doit être présent dans le corps de la requête.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Élément modifié",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Agence.class))),
            @ApiResponse(responseCode = "404", description = "Identifiant absent ou inexistant",
                    content = @Content)
    })
    @PutMapping
    public ResponseEntity<Agence> updateAgence(@RequestBody Agence agence) {
        if (agence.getIdAgence() == null || agenceService.retrieveAgence(agence.getIdAgence()) == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(agenceService.updateAgence(agence));
    }

    @Operation(summary = "Supprimer une agence",
            description = "Supprime une agence à partir de son identifiant.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Élément supprimé", content = @Content),
            @ApiResponse(responseCode = "409",
                    description = "Suppression impossible : élément encore référencé par d'autres données",
                    content = @Content)
    })
    @DeleteMapping("/{idAgence}")
    public ResponseEntity<Void> removeAgence(
            @Parameter(description = "Identifiant d'une agence à supprimer", example = "1") @PathVariable Long idAgence) {
        agenceService.removeAgence(idAgence);
        return ResponseEntity.noContent().build();
    }
}