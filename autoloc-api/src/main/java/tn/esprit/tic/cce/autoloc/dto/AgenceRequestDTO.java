package tn.esprit.tic.cce.autoloc.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Données de création / modification d'une agence")
public class AgenceRequestDTO {

    @NotBlank(message = "Le nom est obligatoire")
    @Size(max = 100, message = "Le nom ne doit pas dépasser 100 caractères")
    @Schema(example = "AutoLoc Tunis Centre")
    private String nom;

    @NotBlank(message = "La ville est obligatoire")
    @Size(max = 60, message = "La ville ne doit pas dépasser 60 caractères")
    @Schema(example = "Tunis")
    private String ville;

    @NotBlank(message = "L'adresse est obligatoire")
    @Size(max = 200, message = "L'adresse ne doit pas dépasser 200 caractères")
    @Schema(example = "12 Avenue Habib Bourguiba")
    private String adresse;

    @NotBlank(message = "Le téléphone est obligatoire")
    @Pattern(regexp = "^\\+?[0-9 ]{8,15}$",
            message = "Le téléphone doit contenir entre 8 et 15 chiffres (préfixe + autorisé)")
    @Schema(example = "71123456")
    private String telephone;
}
