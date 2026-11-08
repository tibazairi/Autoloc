package tn.esprit.tic.cce.autoloc.config;

/** Constantes partagées par les annotations Swagger (évite les littéraux dupliqués, règle Sonar S1192). */
public final class ApiDocs {

    public static final String JSON = "application/json";
    public static final String LISTE_OK = "Liste retournée avec succès";
    public static final String TROUVE = "Élément trouvé";
    public static final String CREE = "Élément créé";
    public static final String CREES = "Éléments créés";
    public static final String MODIFIE = "Élément modifié";
    public static final String SUPPRIME = "Élément supprimé";
    public static final String NON_TROUVE = "Aucun élément avec cet identifiant";
    public static final String CORPS_INVALIDE = "Données invalides (contrôle de saisie)";
    public static final String CONFLIT = "Opération impossible : doublon ou élément encore référencé";
    public static final String ID_AGENCE = "Identifiant d'une agence";
    public static final String ID_CLIENT = "Identifiant d'un client";
    public static final String ID_VEHICULE = "Identifiant d'un véhicule";

    private ApiDocs() {
    }
}
