package model;

import java.util.List;

/**
 * Ici on définit la machine à café
 * Machine utilisée pour consommer le café. Elle est définie par une référence, un nom commercial,
 * une marque, une description et une image optionnelle.
 */
public class MachineACafe {

    /**
     * Code de la machine
     */
    private Long codeMachine;

    /**
     * nom Commercial de la machine à café
     */
    private String nomCommercial;

    /**
     * référence commerciale de la machine
     */
    private String referenceCommerciale;

    /**
     * Description de la machine
     */
    private String description;

    /**
     * marque de la machine
     */
    private Marque marque;

    /**
     * Consommations réalisées avec cette machine
     */
    private List<Consommation> listeConsommations;
}
