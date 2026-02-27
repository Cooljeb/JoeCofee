package com.joe.coffee.api.Service.Interfaces;

import com.joe.coffee.api.Dto.In.DistributeurDtoIn;
import com.joe.coffee.api.Dto.Out.DistributeurDtoOut;

import java.util.List;

/**
 * Interface du service des Distributeurs
 */
public interface DistributeurService {

    /**
     * Méthode de création  d'un Distributeur
     *
     * @param DistributeurIn Dto  d'entrée
     * @return Dto de sortie
     */
    DistributeurDtoOut createDistributeur(DistributeurDtoIn DistributeurIn);

    /**
     * Méthode qui permet de voir tous les distributeurs
     *
     * @return tous les distributeurs présents dans la bdd
     */
    List<DistributeurDtoOut> getAllDistributeur();

    /**
     * Retourne un distributeur à partir de son id
     *
     * @param id distributeur
     * @return le distributeur depuis son id
     */
    DistributeurDtoOut getDistributeurById(Long id);

    /**
     * Méthode de recherche d'un distributeur depuis son nom
     *
     * @param name nom de la marque
     * @return Dto de sortie
     */
    DistributeurDtoOut getDistributeurByName(String name);

    /**
     * Méthode de mise à jour d'un distributeur
     *
     * @param id             distributeur
     * @param DistributeurIn DTO d'entrée
     * @return DO de sortie
     */
    DistributeurDtoOut updateDistributeur(Long id, DistributeurDtoIn DistributeurIn);

    /**
     * Méthode de suppression d'un distributeur
     *
     * @param id de la marque
     */
    void deleteDistributeur(Long id);

}