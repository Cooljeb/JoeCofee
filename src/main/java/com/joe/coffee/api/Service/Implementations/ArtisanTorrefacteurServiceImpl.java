package com.joe.coffee.api.Service.Implementations;

import com.joe.coffee.api.Dto.In.ArtisanTorrefacteurDtoIn;
import com.joe.coffee.api.Dto.Out.ArtisanTorrefacteurDtoOut;
import com.joe.coffee.api.Entity.ArtisanTorrefacteur;
import com.joe.coffee.api.Exception.ArtisanTorrefacteurExceptions.ArtisanTorrefacteurNotFoundException;
import com.joe.coffee.api.Exception.ArtisanTorrefacteurExceptions.DeleteLinkCafeArtisanTorrefacteurException;
import com.joe.coffee.api.Exception.ArtisanTorrefacteurExceptions.DuplicateArtisanTorrefacteurException;
import com.joe.coffee.api.Mapper.ArtisanTorrefacteurMapper;
import com.joe.coffee.api.Repository.ArtisanTorrefacteurRepository;
import com.joe.coffee.api.Repository.CafeRepository;
import com.joe.coffee.api.Service.Interfaces.ArtisanTorrefacteurService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ArtisanTorrefacteurServiceImpl implements ArtisanTorrefacteurService {

    private final ArtisanTorrefacteurRepository artisanTorrefacteurRepository;
    private final CafeRepository cafeRepository;
    private final ArtisanTorrefacteurMapper artisanTorrefacteurMapper;
    private static final Logger log = LoggerFactory.getLogger(ArtisanTorrefacteurServiceImpl.class);


    @Override
    public ArtisanTorrefacteurDtoOut createArtisanTorrefacteur(ArtisanTorrefacteurDtoIn artisanTorrefacteurIn) {

        log.info("Création d'une marque avec le nom '{}'",artisanTorrefacteurIn.nom());
        // Vérifie si un AT avec le même nom existe déjà
        artisanTorrefacteurRepository.findByNomContainingIgnoreCase(artisanTorrefacteurIn.nom())
                .ifPresent( at -> {
                    log.warn("Tentative de création d'un at déjà existant: '{}'",artisanTorrefacteurIn.nom());
                    throw new DuplicateArtisanTorrefacteurException(artisanTorrefacteurIn.nom());
                });
        // Transformation DTO → Entity
        ArtisanTorrefacteur at = artisanTorrefacteurMapper.toEntity(artisanTorrefacteurIn);

        // Sauvegarde
        ArtisanTorrefacteur atSaved = artisanTorrefacteurRepository.save(at);
        log.info("AT créé avec id {}", atSaved.getId());
        return artisanTorrefacteurMapper.toDto(atSaved);
    }

    @Override
    public List<ArtisanTorrefacteurDtoOut> getAllArtisanTorrefacteur() {
        log.info("Récupération de tous les AT");
        return artisanTorrefacteurRepository.findAll().stream()
                .map(artisanTorrefacteurMapper::toDto)
                .toList();

    }

    @Override
    public ArtisanTorrefacteurDtoOut getArtisanTorrefacteurById(Integer id) {
        log.info("Récupération de l'AT avec id {}", id);

        return artisanTorrefacteurRepository.findById(id)
                .map(artisanTorrefacteurMapper::toDto)
                .orElseThrow(() -> {
                    log.warn("AT avec id {} introuvable", id);
                    return new ArtisanTorrefacteurNotFoundException(id);
                });
    }

    @Override
    public ArtisanTorrefacteurDtoOut getArtisanTorrefacteurByName(String name) {
        log.info("Recherche de l'AT avec le nom {}", name);

        return artisanTorrefacteurRepository.findByNomContainingIgnoreCase(name)
                .map(artisanTorrefacteurMapper::toDto)
                .orElseThrow(() -> {
                    log.warn("AT avec le nom {} introuvable", name);
                    return new ArtisanTorrefacteurNotFoundException(name);
                });
    }

    @Override
    public ArtisanTorrefacteurDtoOut updateArtisanTorrefacteur(Integer id, ArtisanTorrefacteurDtoIn artisanTorrefacteurIn) {
        log.info("Mise à jour de la marque avec id {}", id);
        // Récupère l'AT existant ou lance exception si introuvable
        ArtisanTorrefacteur existingArtisanTorrefacteur = artisanTorrefacteurRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("AT avec id {} introuvable mise à jour impossible", id);
                    return new ArtisanTorrefacteurNotFoundException(id);
                });

        // Met à jour l'entité existante avec les nouvelles valeurs du DTO
        artisanTorrefacteurMapper.updateEntityFromDto(artisanTorrefacteurIn, existingArtisanTorrefacteur);

        // Sauvegarde les modifications
        ArtisanTorrefacteur updatedArtisanTorrefacteur = artisanTorrefacteurRepository.save(existingArtisanTorrefacteur);
        log.info("AT avec id {} mise à jour", updatedArtisanTorrefacteur.getId());
        // Retourne DTO OUT
        return artisanTorrefacteurMapper.toDto(updatedArtisanTorrefacteur);
    }

    @Override
    public void deleteArtisanTorrefacteur(Integer id) {

        log.info("Suppression de l'AT avec id {}", id);

        ArtisanTorrefacteur artisanTorrefacteur = artisanTorrefacteurRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("AT avec id {} introuvable suppression impossible", id);
                    return new ArtisanTorrefacteurNotFoundException(id);
                });
        if (cafeRepository.existsByCommercantId(id)) {
            log.warn("Impossible de supprimer cet artisan : des cafés sont rattachés");
            throw new DeleteLinkCafeArtisanTorrefacteurException();
        }
        artisanTorrefacteurRepository.delete(artisanTorrefacteur);
        log.info("Marque avec id {} supprimée", id);

    }

}
