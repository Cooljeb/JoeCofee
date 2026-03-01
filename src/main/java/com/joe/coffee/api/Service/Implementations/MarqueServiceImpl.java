package com.joe.coffee.api.Service.Implementations;

import com.joe.coffee.api.Dto.In.MarqueDtoIn;
import com.joe.coffee.api.Dto.Out.MarqueDtoOut;
import com.joe.coffee.api.Entity.Marque;
import com.joe.coffee.api.Exception.MarqueExceptions.DuplicateMarqueException;
import com.joe.coffee.api.Exception.MarqueExceptions.MarqueNotFoundException;
import com.joe.coffee.api.Mapper.MarqueMapper;
import com.joe.coffee.api.Repository.MarqueRepository;
import com.joe.coffee.api.Service.Interfaces.MarqueService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implémentation de MarqueService
 */

@Service
@RequiredArgsConstructor
public class MarqueServiceImpl implements MarqueService {

    private final MarqueRepository marqueRepository;
    private final MarqueMapper marqueMapper;
    private static final Logger log = LoggerFactory.getLogger(MarqueServiceImpl.class);


    @Override
    public MarqueDtoOut createMarque(MarqueDtoIn marqueIn) {

        log.info("Création d'une marque avec le nom '{}'",marqueIn.marque());
        // Vérifie si une marque avec le même nom existe déjà
        marqueRepository.findByMarqueIgnoreCase(marqueIn.marque())
                .ifPresent( m -> {
                    log.warn("Tentative de création d'un café déjà existant: '{}'",marqueIn.marque());
                    throw new DuplicateMarqueException(marqueIn.marque());
                });
        // Transformation DTO → Entity
        Marque marque = marqueMapper.toEntity(marqueIn);

        // Sauvegarde
        Marque marqueSaved = marqueRepository.save(marque);
        log.info("Marque créée avec id {}", marqueSaved.getId());
        return marqueMapper.toDto(marqueSaved);

    }

    @Override
    public List<MarqueDtoOut> getAllMarques() {

        log.info("Récupération de toutes les marques");
        return marqueRepository.findAll().stream()
                .map(marqueMapper::toDto)
                .toList();

    }

    @Override
    public MarqueDtoOut getMarqueById(Integer id) {

        log.info("Récupération de la marque avec id {}", id);

        return marqueRepository.findById(id)
                .map(marqueMapper::toDto)
                .orElseThrow(() -> {
                    log.warn("Marque avec id {} introuvable", id);
                    return new MarqueNotFoundException(id);
                });
    }

    @Override
    public MarqueDtoOut updateMarque(Integer id, MarqueDtoIn marqueIn) {
        log.info("Mise à jour de la marque avec id {}", id);
        // Récupère la marque existante ou lance exception si introuvable
        Marque existingMarque = marqueRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Marque avec id {} introuvable mise à jour impossible", id);
                    return new MarqueNotFoundException(id);
                });

        // Met à jour l'entité existante avec les nouvelles valeurs du DTO
        marqueMapper.updateEntityFromDto(marqueIn, existingMarque);

        // Sauvegarde les modifications
        Marque updatedMarque = marqueRepository.save(existingMarque);
        log.info("Marque avec id {} mise à jour", updatedMarque.getId());
        // Retourne DTO OUT
        return marqueMapper.toDto(updatedMarque);
    }

    @Override
    public MarqueDtoOut getMarqueByName(String name) {
        log.info("Recherche de la marque avec id {}", name);

        return marqueRepository.findByMarqueIgnoreCase(name)
                .map(marqueMapper::toDto)
                .orElseThrow(() -> {
                    log.warn("Marque avec le nom {} introuvable", name);
                    return new MarqueNotFoundException(name);
                });
    }

    @Override
    public void deleteMarque(Integer id) {

        log.info("Suppression de la marque avec id {}", id);

        Marque marque = marqueRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Marque avec id {} introuvable suppression impossible", id);
                    return new MarqueNotFoundException(id);
                });
        marqueRepository.delete(marque);
        log.info("Marque avec id {} supprimée", id);

    }
}
