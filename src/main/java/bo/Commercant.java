package bo;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;

import java.util.List;

/**
 * CLasse abstraite pour définir le comportenant de base d'un commerçant
 */
@Entity
@Data
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "typeCommercant")
public  class Commercant {
    /**
     * Indique le code du comemrçant (pour la bdd)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  id;
    /**
     * Indique le nom du commerçant
     */
    @Column(nullable = false)
    private String nom;

    /**
     * Adresse du commerçant
     */
    @Column(nullable = false)
    private String adresse;

    /**
     * Adresse e-mail du commerçant
     */
    @Column(nullable = false)
    @Email
    private String email;

    /**
     * Téléphone du commerçant
     */
    @Column(nullable = false)
    private String telephone;

    /**
     * site internet
     */
    @Column(nullable = false)
    private String siteInternet;

    /**
     * Liste des cafés vendus ou produits et ou les 2
     */
    @OneToMany(mappedBy = "cafe", fetch = FetchType.LAZY)
    private List<Cafe> listeDesCafes;


}
