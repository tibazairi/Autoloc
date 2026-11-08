package tn.esprit.tic.cce.autoloc.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.tic.cce.autoloc.domain.Client;
import tn.esprit.tic.cce.autoloc.dto.ClientRequestDTO;
import tn.esprit.tic.cce.autoloc.dto.ClientResponseDTO;

import java.time.LocalDate;
import java.util.List;

@Component
public class ClientMapper {

    public Client toEntity(ClientRequestDTO dto) {
        Client client = new Client();
        updateEntity(dto, client);
        return client;
    }

    /** Copie les champs du DTO dans une entité existante (les réservations ne sont pas touchées). */
    public void updateEntity(ClientRequestDTO dto, Client client) {
        client.setNom(dto.getNom());
        client.setPrenom(dto.getPrenom());
        client.setEmail(dto.getEmail());
        client.setTelephone(dto.getTelephone());
        client.setNumPermis(dto.getNumPermis());
        // Date d'inscription : celle du DTO, sinon conservée, sinon aujourd'hui (création)
        if (dto.getDateInscription() != null) {
            client.setDateInscription(dto.getDateInscription());
        } else if (client.getDateInscription() == null) {
            client.setDateInscription(LocalDate.now());
        }
    }

    public ClientResponseDTO toResponse(Client client) {
        return new ClientResponseDTO(
                client.getIdClient(),
                client.getNom(),
                client.getPrenom(),
                client.getEmail(),
                client.getTelephone(),
                client.getNumPermis(),
                client.getDateInscription());
    }

    public List<ClientResponseDTO> toResponseList(List<Client> clients) {
        return clients.stream().map(this::toResponse).toList();
    }
}
