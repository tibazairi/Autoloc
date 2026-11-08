package tn.esprit.tic.cce.autoloc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.tic.cce.autoloc.domain.Agence;
import tn.esprit.tic.cce.autoloc.dto.AgenceRequestDTO;
import tn.esprit.tic.cce.autoloc.dto.AgenceResponseDTO;
import tn.esprit.tic.cce.autoloc.exception.ResourceNotFoundException;
import tn.esprit.tic.cce.autoloc.mapper.AgenceMapper;
import tn.esprit.tic.cce.autoloc.repository.AgenceRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AgenceService implements IAgenceService {

    private static final String RESSOURCE = "Agence";

    private final AgenceRepository agenceRepository;
    private final AgenceMapper agenceMapper;

    @Override
    public List<AgenceResponseDTO> retrieveAllAgences() {
        return agenceMapper.toResponseList(agenceRepository.findAll());
    }

    @Override
    public AgenceResponseDTO retrieveAgence(Long idAgence) {
        return agenceMapper.toResponse(findOrThrow(idAgence));
    }

    @Override
    @Transactional
    public AgenceResponseDTO addAgence(AgenceRequestDTO dto) {
        return agenceMapper.toResponse(agenceRepository.save(agenceMapper.toEntity(dto)));
    }

    @Override
    @Transactional
    public List<AgenceResponseDTO> addAgences(List<AgenceRequestDTO> dtos) {
        List<Agence> agences = dtos.stream().map(agenceMapper::toEntity).toList();
        return agenceMapper.toResponseList(agenceRepository.saveAll(agences));
    }

    @Override
    @Transactional
    public AgenceResponseDTO updateAgence(Long idAgence, AgenceRequestDTO dto) {
        Agence agence = findOrThrow(idAgence);
        agenceMapper.updateEntity(dto, agence);
        return agenceMapper.toResponse(agenceRepository.save(agence));
    }

    @Override
    @Transactional
    public void removeAgence(Long idAgence) {
        // Force la vérification d'existence (404) avant suppression
        agenceRepository.delete(findOrThrow(idAgence));
    }

    private Agence findOrThrow(Long idAgence) {
        return agenceRepository.findById(idAgence)
                .orElseThrow(() -> new ResourceNotFoundException(RESSOURCE, idAgence));
    }
}
