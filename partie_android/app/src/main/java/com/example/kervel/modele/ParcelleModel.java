package com.example.kervel.modele;

public class ParcelleModel {

    private int id;
    private String commune;
    private String adresse;
    private String code_cadastre;
    private String numero_cadastre;
    private String surface;
    private String montant_achat;
    private String date_autorisation;
    private String releve_msa;
    private String nom_shapefile;
    private String code_mae;
    private String type;
    private String nom_proprietaire;
    private String nom_locataire;
    private String demande_pac;
    private String type_animaux;
    private int nombre_animaux;
    private String montant_loyer;

    public ParcelleModel(String nom_locataire) {
        this.nom_locataire = nom_locataire;
    }

    public int getId() {
        return id;
    }

    public String getCommune() {
        return commune;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getCode_cadastre() {
        return code_cadastre;
    }

    public String getNumero_cadastre() {
        return numero_cadastre;
    }

    public String getSurface() {
        return surface;
    }

    public String getMontant_achat() {
        return montant_achat;
    }

    public String getDate_autorisation() {
        return date_autorisation;
    }

    public String getReleve_msa() {
        return releve_msa;
    }

    public String getNom_shapefile() {
        return nom_shapefile;
    }

    public String getCode_mae() {
        return code_mae;
    }

    public String getType() {
        return type;
    }

    public String getNom_proprietaire() {
        return nom_proprietaire;
    }

    public String getNom_locataire() {
        return nom_locataire;
    }

    public String getDemande_pac() {
        return demande_pac;
    }

    public String getType_animaux() {
        return type_animaux;
    }

    public int getNombre_animaux() {
        return nombre_animaux;
    }

    public String getMontant_loyer() {
        return montant_loyer;
    }
}
