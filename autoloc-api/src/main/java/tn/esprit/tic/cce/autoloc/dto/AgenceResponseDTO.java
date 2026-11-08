package tn.esprit.tic.cce.autoloc.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Agence retournée par l'API")
public class AgenceResponseDTO {
    private Long idAgence;
    private String nom;
    private String ville;
    private String adresse;
    private String telephone;
}
