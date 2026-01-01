package com.joe.coffee.api.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

/**
 * Ici on définit la machine à café
 * Machine utilisée pour consommer le café. Elle est définie par une référence, un nom commercial,
 * une marque, une description et une image optionnelle.
 */
@Entity
@Data
public class MachineACafe {

    /**
     * id de la machine
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * nom Commercial de la machine à café
     */
    @Column(nullable = false)
    private String nomCommercial;

    /**
     * référence commerciale de la machine
     */
    @Column(nullable = false)
    private String referenceCommerciale;

    /**
     * Description de la machine
     */
    @Column(nullable = false)
    private String description;

    /**
     * marque de la machine
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "marque_id")
    private Marque marque;

    /**
     * Consommations réalisées avec cette machine
     */

    @OneToMany(mappedBy = "machineACafe",fetch = FetchType.LAZY)
    private List<Consommation> listeConsommations;
}
