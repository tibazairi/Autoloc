package tn.esprit.tic.cce.autoloc.service;

import tn.esprit.tic.cce.autoloc.dto.AgenceRequestDTO;
import tn.esprit.tic.cce.autoloc.dto.AgenceResponseDTO;

import java.util.List;

public interface IAgenceService {
    List<AgenceResponseDTO> retrieveAllAgences();
    AgenceResponseDTO retrieveAgence(Long idAgence);
    AgenceResponseDTO addAgence(AgenceRequestDTO agence);
    List<AgenceResponseDTO> addAgences(List<AgenceRequestDTO> agences);
    AgenceResponseDTO updateAgence(Long idAgence, AgenceRequestDTO agence);
    void removeAgence(Long idAgence);
}
