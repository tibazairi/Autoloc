package tn.esprit.tic.cce.autoloc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.tic.cce.autoloc.domain.Client;
import tn.esprit.tic.cce.autoloc.dto.ClientRequestDTO;
import tn.esprit.tic.cce.autoloc.dto.ClientResponseDTO;
import tn.esprit.tic.cce.autoloc.exception.ResourceNotFoundException;
import tn.esprit.tic.cce.autoloc.mapper.ClientMapper;
import tn.esprit.tic.cce.autoloc.repository.ClientRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClientService implements IClientService {

    private static final String RESSOURCE = "Client";

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    @Override
    public List<ClientResponseDTO> retrieveAllClients() {
        return clientMapper.toResponseList(clientRepository.findAll());
    }

    @Override
    public ClientResponseDTO retrieveClient(Long idClient) {
        return clientMapper.toResponse(findOrThrow(idClient));
    }

    @Override
    @Transactional
    public ClientResponseDTO addClient(ClientRequestDTO dto) {
        return clientMapper.toResponse(clientRepository.save(clientMapper.toEntity(dto)));
    }

    @Override
    @Transactional
    public List<ClientResponseDTO> addClients(List<ClientRequestDTO> dtos) {
        List<Client> clients = dtos.stream().map(clientMapper::toEntity).toList();
        return clientMapper.toResponseList(clientRepository.saveAll(clients));
    }

    @Override
    @Transactional
    public ClientResponseDTO updateClient(Long idClient, ClientRequestDTO dto) {
        Client client = findOrThrow(idClient);
        clientMapper.updateEntity(dto, client);
        return clientMapper.toResponse(clientRepository.save(client));
    }

    @Override
    @Transactional
    public void removeClient(Long idClient) {
        clientRepository.delete(findOrThrow(idClient));
    }

    private Client findOrThrow(Long idClient) {
        return clientRepository.findById(idClient)
                .orElseThrow(() -> new ResourceNotFoundException(RESSOURCE, idClient));
    }
}
