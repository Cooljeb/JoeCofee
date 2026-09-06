package com.joe.coffee.api.Service.Interfaces;

import com.joe.coffee.api.Dto.In.MarqueDtoIn;
import com.joe.coffee.api.Dto.Out.MarqueDtoOut;

import java.util.List;

/**
 * Interface du service des marques
 */
public interface MarqueService {

    /**
     * Méthode de création d'une marque
     * @param marqueIn Dto  d'entrée
     * @return Dto de sortie
     */
    MarqueDtoOut createMarque(MarqueDtoIn marqueIn);

    /**
     * Méthode qui permet de voir toutes les marques
     * @return toutes les marques présentes dans la bdd
     */
    List<MarqueDtoOut> getAllMarques();

    /**
     * Retourne une marque à partir de son id
     * @param id de la marque
     * @return la marque depuis son id
     */
    MarqueDtoOut getMarqueById(Integer id);

    /**
     * Méthode de mise à jour d'une marque
     * @param id de la marque
     * @param marqueIn Dto d'entrée
     * @return DTO de sortie
     */
    MarqueDtoOut updateMarque(Integer id, MarqueDtoIn marqueIn);

    /**
     * Méthode de recherche d'une marque depuis son nom
     * @param name nom de la marque
     * @return Dto de sortie
     */
    MarqueDtoOut getMarqueByName(String name);


    /**
     * Méthode de suppression d'une marque
     * @param id de la marque
     */
    void  deleteMarque(Integer id);
}
