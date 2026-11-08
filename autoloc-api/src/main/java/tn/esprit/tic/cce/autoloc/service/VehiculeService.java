package tn.esprit.tic.cce.autoloc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.tic.cce.autoloc.domain.Agence;
import tn.esprit.tic.cce.autoloc.domain.Vehicule;
import tn.esprit.tic.cce.autoloc.dto.VehiculeRequestDTO;
import tn.esprit.tic.cce.autoloc.dto.VehiculeResponseDTO;
import tn.esprit.tic.cce.autoloc.exception.ResourceNotFoundException;
import tn.esprit.tic.cce.autoloc.mapper.VehiculeMapper;
import tn.esprit.tic.cce.autoloc.repository.AgenceRepository;
import tn.esprit.tic.cce.autoloc.repository.VehiculeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VehiculeService implements IVehiculeService {

    private static final String RESSOURCE = "Véhicule";

    private final VehiculeRepository vehiculeRepository;
    private final AgenceRepository agenceRepository;
    private final VehiculeMapper vehiculeMapper;

    @Override
    public List<VehiculeResponseDTO> retrieveAllVehicules() {
        return vehiculeMapper.toResponseList(vehiculeRepository.findAll());
    }

    @Override
    public VehiculeResponseDTO retrieveVehicule(Long idVehicule) {
        return vehiculeMapper.toResponse(findOrThrow(idVehicule));
    }

    @Override
    @Transactional
    public VehiculeResponseDTO addVehicule(VehiculeRequestDTO dto) {
        Vehicule vehicule = vehiculeMapper.toEntity(dto, resolveAgence(dto.getIdAgence()));
        return vehiculeMapper.toResponse(vehiculeRepository.save(vehicule));
    }

    @Override
    @Transactional
    public List<VehiculeResponseDTO> addVehicules(List<VehiculeRequestDTO> dtos) {
        List<Vehicule> vehicules = dtos.stream()
                .map(dto -> vehiculeMapper.toEntity(dto, resolveAgence(dto.getIdAgence())))
                .toList();
        return vehiculeMapper.toResponseList(vehiculeRepository.saveAll(vehicules));
    }

    @Override
    @Transactional
    public VehiculeResponseDTO updateVehicule(Long idVehicule, VehiculeRequestDTO dto) {
        Vehicule vehicule = findOrThrow(idVehicule);
        vehiculeMapper.updateEntity(dto, vehicule, resolveAgence(dto.getIdAgence()));
        return vehiculeMapper.toResponse(vehiculeRepository.save(vehicule));
    }

    @Override
    @Transactional
    public void removeVehicule(Long idVehicule) {
        vehiculeRepository.delete(findOrThrow(idVehicule));
    }

    private Vehicule findOrThrow(Long idVehicule) {
        return vehiculeRepository.findById(idVehicule)
                .orElseThrow(() -> new ResourceNotFoundException(RESSOURCE, idVehicule));
    }

    /** Retourne une référence légère (pas de requête SQL) vers l'agence, ou null si non fournie. */
    private Agence resolveAgence(Long idAgence) {
        if (idAgence == null) {
            return null;
        }
        if (!agenceRepository.existsById(idAgence)) {
            throw new ResourceNotFoundException("Agence", idAgence);
        }
        return agenceRepository.getReferenceById(idAgence);
    }
}
