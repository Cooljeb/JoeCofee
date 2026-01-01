package entity;

/**
 * Une consommation  référence exactement un café et une machine
 * un réglage du broyeur, intensité (réglage machine)
 */
public class Consommation {

    /**
     * CodeConsommation
     */
    private Long codeConsommation;

    /**
     * Réglage broyeur
     */
    private byte reglageBroyeur;

    /**
     * Réglage intensité
     */
    private byte reglageIntensite;

    /**
     * Café correspondant à la consommation
     */
    private  Cafe cafe;

    /**
     * Machine correspondant à la consommation
     */
    private MachineACafe machineACafe;
}
