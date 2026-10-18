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
import tn.esprit.tic.cce.autoloc.domain.Client;
import tn.esprit.tic.cce.autoloc.service.IClientService;

import java.util.List;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
@Tag(name = "Clients", description = "Gestion des clients d'AutoLoc")
public class ClientController {

    private final IClientService clientService;

    @Operation(summary = "Lister les clients",
            description = "Retourne la liste complète des clients (liste vide si aucun).")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Liste retournée avec succès",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Client.class))))
    })
    @GetMapping
    public List<Client> retrieveAllClients() {
        return clientService.retrieveAllClients();
    }

    @Operation(summary = "Consulter un client par son identifiant",
            description = "Retourne un client correspondant à l'identifiant fourni.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Élément trouvé",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Client.class))),
            @ApiResponse(responseCode = "404", description = "Aucun élément avec cet identifiant",
                    content = @Content)
    })
    @GetMapping("/{idClient}")
    public ResponseEntity<Client> retrieveClient(
            @Parameter(description = "Identifiant d'un client", example = "1") @PathVariable Long idClient) {
        Client client = clientService.retrieveClient(idClient);
        return client != null ? ResponseEntity.ok(client) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Ajouter un client",
            description = "Crée un client. L'identifiant est généré automatiquement : ne pas le fournir.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Élément créé",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Client.class))),
            @ApiResponse(responseCode = "400", description = "Corps de requête invalide",
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<Client> addClient(@RequestBody Client client) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clientService.addClient(client));
    }

    @Operation(summary = "Ajouter plusieurs clients",
            description = "Crée plusieurs clients en une seule requête.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Éléments créés",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Client.class)))),
            @ApiResponse(responseCode = "400", description = "Corps de requête invalide",
                    content = @Content)
    })
    @PostMapping("/batch")
    public ResponseEntity<List<Client>> addClients(@RequestBody List<Client> clients) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clientService.addClients(clients));
    }

    @Operation(summary = "Modifier un client",
            description = "Met à jour un client existant. L'identifiant doit être présent dans le corps de la requête.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Élément modifié",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Client.class))),
            @ApiResponse(responseCode = "404", description = "Identifiant absent ou inexistant",
                    content = @Content)
    })
    @PutMapping
    public ResponseEntity<Client> updateClient(@RequestBody Client client) {
        if (client.getIdClient() == null || clientService.retrieveClient(client.getIdClient()) == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(clientService.updateClient(client));
    }

    @Operation(summary = "Supprimer un client",
            description = "Supprime un client à partir de son identifiant.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Élément supprimé", content = @Content),
            @ApiResponse(responseCode = "409",
                    description = "Suppression impossible : élément encore référencé par d'autres données",
                    content = @Content)
    })
    @DeleteMapping("/{idClient}")
    public ResponseEntity<Void> removeClient(
            @Parameter(description = "Identifiant d'un client à supprimer", example = "1") @PathVariable Long idClient) {
        clientService.removeClient(idClient);
        return ResponseEntity.noContent().build();
    }
}