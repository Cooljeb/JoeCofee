package entity;


import java.util.List;

/**
 * CLasse abstraite pour définir le comportenant de base d'un commerçant
 */
public abstract class Commerçant {
    /**
     * Indique le code du comemrçant (pour la bdd)
     */
    private Long  codeCommerçant;
    /**
     * Indique le nom du commerçant
     */
    private String nom;

    /**
     * Adresse du commerçant
     */
    private String adresse;

    /**
     * Adresse e-mail du commerçant
     */
    private String email;

    /**
     * Téléphone du commerçant
     */
    private String telephone;

    /**
     * site internet
     */
    private String siteInternet;

    /**
     * Liste des cafés vendus ou produits et ou les 2
     */

    private List<Cafe> listeDesCafes;

    /**
     * défini le type du commerçant
     * @return Artisan torréfacteur ou distributeur
     */
    public abstract String getType();
}
