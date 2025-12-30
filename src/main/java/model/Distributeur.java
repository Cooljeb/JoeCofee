package model;

/**
 * Définition du commerçant de type Distributeur
 * Ce dernier ne fait que vendre du café
 */
public class Distributeur extends Commerçant{

    private  String nomDuGroupeDeDistribution;

    @Override
    public String getType() {
        return "Je suis un distributeur";
    }
}
