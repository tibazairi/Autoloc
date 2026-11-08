package tn.esprit.tic.cce.autoloc.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.tic.cce.autoloc.domain.Agence;
import tn.esprit.tic.cce.autoloc.domain.Vehicule;
import tn.esprit.tic.cce.autoloc.dto.VehiculeRequestDTO;
import tn.esprit.tic.cce.autoloc.dto.VehiculeResponseDTO;

import java.util.List;

@Component
public class VehiculeMapper {

    /** L'agence est résolue par le service (à partir de idAgence) puis passée ici. */
    public Vehicule toEntity(VehiculeRequestDTO dto, Agence agence) {
        Vehicule vehicule = new Vehicule();
        updateEntity(dto, vehicule, agence);
        return vehicule;
    }

    /** Copie les champs du DTO ; les équipements, maintenances et réservations ne sont pas touchés. */
    public void updateEntity(VehiculeRequestDTO dto, Vehicule vehicule, Agence agence) {
        vehicule.setImmatriculation(dto.getImmatriculation());
        vehicule.setMarque(dto.getMarque());
        vehicule.setModele(dto.getModele());
        vehicule.setCategorie(dto.getCategorie());
        vehicule.setTarifJournalier(dto.getTarifJournalier());
        vehicule.setStatut(dto.getStatut());
        vehicule.setAgence(agence);
    }

    public VehiculeResponseDTO toResponse(Vehicule vehicule) {
        Agence agence = vehicule.getAgence();
        return new VehiculeResponseDTO(
                vehicule.getIdVehicule(),
                vehicule.getImmatriculation(),
                vehicule.getMarque(),
                vehicule.getModele(),
                vehicule.getCategorie(),
                vehicule.getTarifJournalier(),
                vehicule.getStatut(),
                agence != null ? agence.getIdAgence() : null,
                agence != null ? agence.getNom() : null);
    }

    public List<VehiculeResponseDTO> toResponseList(List<Vehicule> vehicules) {
        return vehicules.stream().map(this::toResponse).toList();
    }
}
