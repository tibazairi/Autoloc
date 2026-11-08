package tn.esprit.tic.cce.autoloc.service;

import tn.esprit.tic.cce.autoloc.dto.ClientRequestDTO;
import tn.esprit.tic.cce.autoloc.dto.ClientResponseDTO;

import java.util.List;

public interface IClientService {
    List<ClientResponseDTO> retrieveAllClients();
    ClientResponseDTO retrieveClient(Long idClient);
    ClientResponseDTO addClient(ClientRequestDTO client);
    List<ClientResponseDTO> addClients(List<ClientRequestDTO> clients);
    ClientResponseDTO updateClient(Long idClient, ClientRequestDTO client);
    void removeClient(Long idClient);
}
