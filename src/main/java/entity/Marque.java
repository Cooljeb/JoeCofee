package entity;

import java.util.List;

/**
 * Ensemble des marques connues de machine à café
 */
public class Marque {

    /**
     * CodeMarque
     */
    private String codeMarque;

    /**
     * désignation de la marque
     */
    private String marque;

    /**
     * Liste de machines de cette marque
     */
    private List<MachineACafe> listeMarchines;
}
