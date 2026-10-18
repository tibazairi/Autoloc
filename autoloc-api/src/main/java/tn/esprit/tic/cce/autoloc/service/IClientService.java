package tn.esprit.tic.cce.autoloc.service;

import tn.esprit.tic.cce.autoloc.domain.Client;

import java.util.List;

public interface IClientService {
    List<Client> retrieveAllClients();
    Client addClient(Client client);
    Client updateClient(Client client);
    Client retrieveClient(Long idClient);
    void removeClient(Long idClient);
    List<Client> addClients(List<Client> clients);
}