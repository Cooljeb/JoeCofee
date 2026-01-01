package entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Définition du commerçant de type Distributeur
 * Ce dernier ne fait que vendre du café
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@DiscriminatorValue("Distributeur")
public class Distributeur extends Commercant {

    private  String nomDuGroupeDeDistribution;


}
