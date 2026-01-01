package com.joe.coffee.api.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

/**
 * Ensemble des marques connues de machine à café
 */
@Entity
@Data
public class Marque {

    /**
     * Id de la marque
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * désignation de la marque
     */
    @Column(nullable = false)
    private String marque;

    /**
     * Liste de machines de cette marque
     */
    @OneToMany(mappedBy = "marque",fetch = FetchType.LAZY)
    private List<MachineACafe> listeMarchines;
}
