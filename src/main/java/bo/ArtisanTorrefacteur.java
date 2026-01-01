package bo;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Classe de définition du commerçant de type ArtisantTorréfacteur
 * Ce dernier, en plus de vendre son café, le torréfie
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@DiscriminatorValue("Artisan")
public class ArtisanTorrefacteur extends Commercant {

    /**
     * Année de création de la structure de cet artisan
     */
    private String anneeCreation;

}
