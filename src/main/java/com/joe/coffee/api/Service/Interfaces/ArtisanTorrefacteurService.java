package com.joe.coffee.api.Service.Interfaces;

import com.joe.coffee.api.Dto.In.ArtisanTorrefacteurDtoIn;
import com.joe.coffee.api.Dto.Out.ArtisanTorrefacteurDtoOut;

import java.util.List;

/**
 * Interface du service des Artisan Torrefacteur
 */
public interface ArtisanTorrefacteurService {

    /**
     * Méthode de création d'une artisan torréfacteur (AT)
     * @param artisanTorrefacteurIn Dto  d'entrée
     * @return Dto de sortie
     */
    ArtisanTorrefacteurDtoOut createArtisanTorrefacteur(ArtisanTorrefacteurDtoIn artisanTorrefacteurIn);

    /**
     * Méthode qui permet de voir tous les AT
     * @return tous les AT présents dans la bdd
     */
    List<ArtisanTorrefacteurDtoOut> getAllArtisanTorrefacteur();

    /**
     *  Retourne un AT à partir de son id
     *  @param id de l'AT
     *  @return l'AT depuis son id
     */
    ArtisanTorrefacteurDtoOut getArtisanTorrefacteurById(Long id);

    /**
     * Méthode de recherche d'un AT depuis son nom
     * @param name nom de la marque
     * @return Dto de sortie
     */
    ArtisanTorrefacteurDtoOut getArtisanTorrefacteurByName(String name);

    /**
     * Méthode de mise à jour d'un AT
     * @param id de l'AT
     * @param artisanTorrefacteurIn DTO d'entrée
     * @return DO de sortie
     */
    ArtisanTorrefacteurDtoOut updateArtisanTorrefacteur(Long id, ArtisanTorrefacteurDtoIn artisanTorrefacteurIn);

    /**
     * Méthode de suppression d'un AT
     * @param id de la marque
     */
    void  deleteArtisanTorrefacteur(Long id);

}
