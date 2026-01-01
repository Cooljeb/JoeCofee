package entity;

/**
 * Classe de définition du commerçant de type ArtisantTorréfacteur
 * Ce dernier, en plus de vendre son café, le torréfie
 */
public class ArtisanTorrefacteur extends Commerçant {

    /**
     * Année de création de la structure de cet artisan
     */
    private String anneeCréation;

    @Override
    public String getType() {
        return "Je suis un artisant torréfacteur";
    }
}
