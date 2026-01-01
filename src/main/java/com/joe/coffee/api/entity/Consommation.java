package com.joe.coffee.api.entity;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Une consommation  référence exactement un café et une machine
 * un réglage du broyeur, intensité (réglage machine)
 */
@Entity
@Data
public class Consommation {

    /**
     * id de la Consommation
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codeConsommation;

    /**
     * Réglage broyeur
     */
    @Column(nullable = false)
    private byte reglageBroyeur;

    /**
     * Réglage intensité
     */
    @Column(nullable = false)
    private byte reglageIntensite;

    /**
     * Café correspondant à la consommation
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cafe_id")
    private  Cafe cafe;

    /**
     * Machine correspondant à la consommation
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "machine_a_cafe_id")
    private MachineACafe machineACafe;
}
