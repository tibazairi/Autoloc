package tn.esprit.tic.cce.autoloc.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.tic.cce.autoloc.domain.Agence;
import tn.esprit.tic.cce.autoloc.dto.AgenceRequestDTO;
import tn.esprit.tic.cce.autoloc.dto.AgenceResponseDTO;

import java.util.List;

@Component
public class AgenceMapper {

    public Agence toEntity(AgenceRequestDTO dto) {
        Agence agence = new Agence();
        updateEntity(dto, agence);
        return agence;
    }

    /** Copie les champs du DTO dans une entité existante (les relations ne sont pas touchées). */
    public void updateEntity(AgenceRequestDTO dto, Agence agence) {
        agence.setNom(dto.getNom());
        agence.setVille(dto.getVille());
        agence.setAdresse(dto.getAdresse());
        agence.setTelephone(dto.getTelephone());
    }

    public AgenceResponseDTO toResponse(Agence agence) {
        return new AgenceResponseDTO(
                agence.getIdAgence(),
                agence.getNom(),
                agence.getVille(),
                agence.getAdresse(),
                agence.getTelephone());
    }

    public List<AgenceResponseDTO> toResponseList(List<Agence> agences) {
        return agences.stream().map(this::toResponse).toList();
    }
}
