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
import tn.esprit.tic.cce.autoloc.domain.Vehicule;
import tn.esprit.tic.cce.autoloc.service.IVehiculeService;

import java.util.List;

@RestController
@RequestMapping("/vehicules")
@RequiredArgsConstructor
@Tag(name = "Véhicules", description = "Gestion de la flotte de véhicules d'AutoLoc")
public class VehiculeController {

    private final IVehiculeService vehiculeService;

    @Operation(summary = "Lister les véhicules",
            description = "Retourne la liste complète des véhicules (liste vide si aucun).")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Liste retournée avec succès",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Vehicule.class))))
    })
    @GetMapping
    public List<Vehicule> retrieveAllVehicules() {
        return vehiculeService.retrieveAllVehicules();
    }

    @Operation(summary = "Consulter un véhicule par son identifiant",
            description = "Retourne un véhicule correspondant à l'identifiant fourni.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Élément trouvé",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Vehicule.class))),
            @ApiResponse(responseCode = "404", description = "Aucun élément avec cet identifiant",
                    content = @Content)
    })
    @GetMapping("/{idVehicule}")
    public ResponseEntity<Vehicule> retrieveVehicule(
            @Parameter(description = "Identifiant d'un véhicule", example = "1") @PathVariable Long idVehicule) {
        Vehicule vehicule = vehiculeService.retrieveVehicule(idVehicule);
        return vehicule != null ? ResponseEntity.ok(vehicule) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Ajouter un véhicule",
            description = "Crée un véhicule rattaché à une agence existante. L'identifiant est généré automatiquement.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Élément créé",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Vehicule.class))),
            @ApiResponse(responseCode = "400", description = "Corps de requête invalide",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "Immatriculation déjà utilisée ou agence inexistante",
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<Vehicule> addVehicule(@RequestBody Vehicule vehicule) {
        return ResponseEntity.status(HttpStatus.CREATED).body(vehiculeService.addVehicule(vehicule));
    }

    @Operation(summary = "Ajouter plusieurs véhicules",
            description = "Crée plusieurs véhicules en une seule requête.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Éléments créés",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Vehicule.class)))),
            @ApiResponse(responseCode = "400", description = "Corps de requête invalide",
                    content = @Content)
    })
    @PostMapping("/batch")
    public ResponseEntity<List<Vehicule>> addVehicules(@RequestBody List<Vehicule> vehicules) {
        return ResponseEntity.status(HttpStatus.CREATED).body(vehiculeService.addVehicules(vehicules));
    }

    @Operation(summary = "Modifier un véhicule",
            description = "Met à jour un véhicule existant. L'identifiant doit être présent dans le corps de la requête.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Élément modifié",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Vehicule.class))),
            @ApiResponse(responseCode = "404", description = "Identifiant absent ou inexistant",
                    content = @Content)
    })
    @PutMapping
    public ResponseEntity<Vehicule> updateVehicule(@RequestBody Vehicule vehicule) {
        if (vehicule.getIdVehicule() == null || vehiculeService.retrieveVehicule(vehicule.getIdVehicule()) == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(vehiculeService.updateVehicule(vehicule));
    }

    @Operation(summary = "Supprimer un véhicule",
            description = "Supprime un véhicule à partir de son identifiant.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Élément supprimé", content = @Content),
            @ApiResponse(responseCode = "409",
                    description = "Suppression impossible : élément encore référencé par d'autres données",
                    content = @Content)
    })
    @DeleteMapping("/{idVehicule}")
    public ResponseEntity<Void> removeVehicule(
            @Parameter(description = "Identifiant d'un véhicule à supprimer", example = "1") @PathVariable Long idVehicule) {
        vehiculeService.removeVehicule(idVehicule);
        return ResponseEntity.noContent().build();
    }
}