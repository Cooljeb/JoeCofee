package entity;
import Enum.TypeCafe;
import Enum.LabelCafe;

import java.util.List;

/**
 * Description simple d'un café
 */
public class Cafe {

    /**
     * Code du café
     */
    private Long codeCafe;

    /**
     * Nom du café
     */
    private String nomCafe;

    /**
     * Description du café
     */
    private String description;

    /**
     * Type du café
     */
    private TypeCafe typeCafe;

    /**
     * Label du café
     */
    private LabelCafe labelCafe;

    /**
     * Consommations réalisées avec ce café
     */
    private List<Consommation> listeConsommations;
}
