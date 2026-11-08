package tn.esprit.tic.cce.autoloc.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Données de création / modification d'un client")
public class ClientRequestDTO {

    @NotBlank(message = "Le nom est obligatoire")
    @Size(max = 50, message = "Le nom ne doit pas dépasser 50 caractères")
    @Schema(example = "Ben Ali")
    private String nom;

    @NotBlank(message = "Le prénom est obligatoire")
    @Size(max = 50, message = "Le prénom ne doit pas dépasser 50 caractères")
    @Schema(example = "Sami")
    private String prenom;

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "L'email n'est pas valide")
    @Size(max = 100, message = "L'email ne doit pas dépasser 100 caractères")
    @Schema(example = "sami.benali@mail.tn")
    private String email;

    @NotBlank(message = "Le téléphone est obligatoire")
    @Pattern(regexp = "^\\+?[0-9 ]{8,15}$",
            message = "Le téléphone doit contenir entre 8 et 15 chiffres (préfixe + autorisé)")
    @Schema(example = "22123456")
    private String telephone;

    @NotBlank(message = "Le numéro de permis est obligatoire")
    @Size(max = 20, message = "Le numéro de permis ne doit pas dépasser 20 caractères")
    @Schema(example = "TN123456")
    private String numPermis;

    @PastOrPresent(message = "La date d'inscription ne peut pas être dans le futur")
    @Schema(example = "2026-10-26")
    private LocalDate dateInscription;
}
