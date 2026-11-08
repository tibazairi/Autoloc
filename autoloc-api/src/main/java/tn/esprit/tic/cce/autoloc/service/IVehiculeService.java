package tn.esprit.tic.cce.autoloc.service;

import tn.esprit.tic.cce.autoloc.dto.VehiculeRequestDTO;
import tn.esprit.tic.cce.autoloc.dto.VehiculeResponseDTO;

import java.util.List;

public interface IVehiculeService {
    List<VehiculeResponseDTO> retrieveAllVehicules();
    VehiculeResponseDTO retrieveVehicule(Long idVehicule);
    VehiculeResponseDTO addVehicule(VehiculeRequestDTO vehicule);
    List<VehiculeResponseDTO> addVehicules(List<VehiculeRequestDTO> vehicules);
    VehiculeResponseDTO updateVehicule(Long idVehicule, VehiculeRequestDTO vehicule);
    void removeVehicule(Long idVehicule);
}
