package tn.esprit.tic.cce.autoloc.controller;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tic.cce.autoloc.config.ApiDocs;
import tn.esprit.tic.cce.autoloc.dto.AgenceRequestDTO;
import tn.esprit.tic.cce.autoloc.dto.AgenceResponseDTO;
import tn.esprit.tic.cce.autoloc.service.IAgenceService;

import java.util.List;

@RestController
@RequestMapping("/agences")
@RequiredArgsConstructor
@Validated
@Tag(name = "Agences", description = "Gestion des agences d'AutoLoc")
public class AgenceController {

    private final IAgenceService agenceService;

    @Operation(summary = "Lister les agences",
            description = "Retourne la liste complète des agences (liste vide si aucune).")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = ApiDocs.LISTE_OK,
                    content = @Content(mediaType = ApiDocs.JSON,
                            array = @ArraySchema(schema = @Schema(implementation = AgenceResponseDTO.class))))
    })
    @GetMapping
    public List<AgenceResponseDTO> retrieveAllAgences() {
        return agenceService.retrieveAllAgences();
    }

    @Operation(summary = "Consulter une agence par son identifiant",
            description = "Retourne l'agence correspondant à l'identifiant fourni.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = ApiDocs.TROUVE,
                    content = @Content(mediaType = ApiDocs.JSON,
                            schema = @Schema(implementation = AgenceResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = ApiDocs.NON_TROUVE, content = @Content)
    })
    @GetMapping("/{idAgence}")
    public AgenceResponseDTO retrieveAgence(
            @Parameter(description = ApiDocs.ID_AGENCE, example = "1") @PathVariable Long idAgence) {
        return agenceService.retrieveAgence(idAgence);
    }

    @Operation(summary = "Ajouter une agence",
            description = "Crée une agence. L'identifiant est généré automatiquement : ne pas le fournir.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = ApiDocs.CREE,
                    content = @Content(mediaType = ApiDocs.JSON,
                            schema = @Schema(implementation = AgenceResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = ApiDocs.CORPS_INVALIDE, content = @Content)
    })
    @PostMapping
    public ResponseEntity<AgenceResponseDTO> addAgence(@Valid @RequestBody AgenceRequestDTO agence) {
        return ResponseEntity.status(HttpStatus.CREATED).body(agenceService.addAgence(agence));
    }

    @Operation(summary = "Ajouter plusieurs agences",
            description = "Crée plusieurs agences en une seule requête.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = ApiDocs.CREES,
                    content = @Content(mediaType = ApiDocs.JSON,
                            array = @ArraySchema(schema = @Schema(implementation = AgenceResponseDTO.class)))),
            @ApiResponse(responseCode = "400", description = ApiDocs.CORPS_INVALIDE, content = @Content)
    })
    @PostMapping("/batch")
    public ResponseEntity<List<AgenceResponseDTO>> addAgences(
            @RequestBody List<@Valid AgenceRequestDTO> agences) {
        return ResponseEntity.status(HttpStatus.CREATED).body(agenceService.addAgences(agences));
    }

    @Operation(summary = "Modifier une agence",
            description = "Met à jour l'agence dont l'identifiant est passé dans l'URL.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = ApiDocs.MODIFIE,
                    content = @Content(mediaType = ApiDocs.JSON,
                            schema = @Schema(implementation = AgenceResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = ApiDocs.CORPS_INVALIDE, content = @Content),
            @ApiResponse(responseCode = "404", description = ApiDocs.NON_TROUVE, content = @Content)
    })
    @PutMapping("/{idAgence}")
    public AgenceResponseDTO updateAgence(
            @Parameter(description = ApiDocs.ID_AGENCE, example = "1") @PathVariable Long idAgence,
            @Valid @RequestBody AgenceRequestDTO agence) {
        return agenceService.updateAgence(idAgence, agence);
    }

    @Operation(summary = "Supprimer une agence",
            description = "Supprime une agence à partir de son identifiant.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = ApiDocs.SUPPRIME, content = @Content),
            @ApiResponse(responseCode = "404", description = ApiDocs.NON_TROUVE, content = @Content),
            @ApiResponse(responseCode = "409", description = ApiDocs.CONFLIT, content = @Content)
    })
    @DeleteMapping("/{idAgence}")
    public ResponseEntity<Void> removeAgence(
            @Parameter(description = ApiDocs.ID_AGENCE, example = "1") @PathVariable Long idAgence) {
        agenceService.removeAgence(idAgence);
        return ResponseEntity.noContent().build();
    }
}
