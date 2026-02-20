package com.joe.coffee.api.Service.Interfaces;

import com.joe.coffee.api.Dto.In.CafeDtoIn;
import com.joe.coffee.api.Dto.Out.CafeDtoOut;

import java.util.List;

/**
 * Interface du service des Cafés
 */
public interface CafeService {

    /**
     * Méthode de création d'un café
     * @param cafeIn qui est le DTO d'entrée
     * @return le DTO de sortie ou une exception
     */
    CafeDtoOut createCafe(CafeDtoIn cafeIn);

    /**
     * Méthode de mise à jour d'un enregistrement de type café
     * @param id du café concerné
     * @param cafeIn Dto d'entrée
     * @return DTO de sortie
     */
    CafeDtoOut updateCafe(Long id, CafeDtoIn cafeIn);

    /**
     * Suppression d'un café à aprtir de son idée
     * @param id du café concerné
     */
    void deleteCafe(Long id);

    /**
     * Affiche tout les cafés présents dans la bdd
     * @return une liste de café correspondant au DTO de sortie
     */
    List<CafeDtoOut> getAllCafe();

    /**
     * Affiche le café recherché par son id
     * @param id
     * @return le café correspondant au DTO de sortie
     */
    CafeDtoOut getCafeById(Long id);


}
