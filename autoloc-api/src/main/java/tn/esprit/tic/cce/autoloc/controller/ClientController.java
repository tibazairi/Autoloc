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
import tn.esprit.tic.cce.autoloc.dto.ClientRequestDTO;
import tn.esprit.tic.cce.autoloc.dto.ClientResponseDTO;
import tn.esprit.tic.cce.autoloc.service.IClientService;

import java.util.List;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
@Validated
@Tag(name = "Clients", description = "Gestion des clients d'AutoLoc")
public class ClientController {

    private final IClientService clientService;

    @Operation(summary = "Lister les clients",
            description = "Retourne la liste complète des clients (liste vide si aucun).")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = ApiDocs.LISTE_OK,
                    content = @Content(mediaType = ApiDocs.JSON,
                            array = @ArraySchema(schema = @Schema(implementation = ClientResponseDTO.class))))
    })
    @GetMapping
    public List<ClientResponseDTO> retrieveAllClients() {
        return clientService.retrieveAllClients();
    }

    @Operation(summary = "Consulter un client par son identifiant",
            description = "Retourne le client correspondant à l'identifiant fourni.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = ApiDocs.TROUVE,
                    content = @Content(mediaType = ApiDocs.JSON,
                            schema = @Schema(implementation = ClientResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = ApiDocs.NON_TROUVE, content = @Content)
    })
    @GetMapping("/{idClient}")
    public ClientResponseDTO retrieveClient(
            @Parameter(description = ApiDocs.ID_CLIENT, example = "1") @PathVariable Long idClient) {
        return clientService.retrieveClient(idClient);
    }

    @Operation(summary = "Ajouter un client",
            description = "Crée un client. L'identifiant est généré automatiquement : ne pas le fournir.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = ApiDocs.CREE,
                    content = @Content(mediaType = ApiDocs.JSON,
                            schema = @Schema(implementation = ClientResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = ApiDocs.CORPS_INVALIDE, content = @Content)
    })
    @PostMapping
    public ResponseEntity<ClientResponseDTO> addClient(@Valid @RequestBody ClientRequestDTO client) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clientService.addClient(client));
    }

    @Operation(summary = "Ajouter plusieurs clients",
            description = "Crée plusieurs clients en une seule requête.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = ApiDocs.CREES,
                    content = @Content(mediaType = ApiDocs.JSON,
                            array = @ArraySchema(schema = @Schema(implementation = ClientResponseDTO.class)))),
            @ApiResponse(responseCode = "400", description = ApiDocs.CORPS_INVALIDE, content = @Content)
    })
    @PostMapping("/batch")
    public ResponseEntity<List<ClientResponseDTO>> addClients(
            @RequestBody List<@Valid ClientRequestDTO> clients) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clientService.addClients(clients));
    }

    @Operation(summary = "Modifier un client",
            description = "Met à jour le client dont l'identifiant est passé dans l'URL.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = ApiDocs.MODIFIE,
                    content = @Content(mediaType = ApiDocs.JSON,
                            schema = @Schema(implementation = ClientResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = ApiDocs.CORPS_INVALIDE, content = @Content),
            @ApiResponse(responseCode = "404", description = ApiDocs.NON_TROUVE, content = @Content)
    })
    @PutMapping("/{idClient}")
    public ClientResponseDTO updateClient(
            @Parameter(description = ApiDocs.ID_CLIENT, example = "1") @PathVariable Long idClient,
            @Valid @RequestBody ClientRequestDTO client) {
        return clientService.updateClient(idClient, client);
    }

    @Operation(summary = "Supprimer un client",
            description = "Supprime un client à partir de son identifiant.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = ApiDocs.SUPPRIME, content = @Content),
            @ApiResponse(responseCode = "404", description = ApiDocs.NON_TROUVE, content = @Content),
            @ApiResponse(responseCode = "409", description = ApiDocs.CONFLIT, content = @Content)
    })
    @DeleteMapping("/{idClient}")
    public ResponseEntity<Void> removeClient(
            @Parameter(description = ApiDocs.ID_CLIENT, example = "1") @PathVariable Long idClient) {
        clientService.removeClient(idClient);
        return ResponseEntity.noContent().build();
    }
}
