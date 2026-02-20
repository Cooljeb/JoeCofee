package com.joe.coffee.api.Service.Implementations;

import com.joe.coffee.api.Dto.In.CafeDtoIn;
import com.joe.coffee.api.Dto.Out.CafeDtoOut;
import com.joe.coffee.api.Entity.Cafe;
import com.joe.coffee.api.Exception.CafeExceptions.CafeNotFoundException;
import com.joe.coffee.api.Exception.CafeExceptions.DuplicateCafeException;
import com.joe.coffee.api.Mapper.CafeMapper;
import com.joe.coffee.api.Repository.CafeRepository;
import com.joe.coffee.api.Service.Interfaces.CafeService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implémentation de CafeService
 */

@Service
@RequiredArgsConstructor
public class CafeServiceImpl implements CafeService {

    private final CafeRepository cafeRepository;
    private final CafeMapper cafeMapper;
    private static final Logger log = LoggerFactory.getLogger(CafeServiceImpl.class);

    @Override
    public CafeDtoOut createCafe(CafeDtoIn cafeIn) {

        log.info("Création d'un café avec le nom '{}'", cafeIn.nomCafe());
        // Vérifie si un café avec le même nom existe déjà
        cafeRepository.findByNomCafeIgnoreCase(cafeIn.nomCafe())
                .ifPresent(c -> {
                    log.warn("Tentative de création d'un café déjà existant: '{}'", cafeIn.nomCafe());
                    throw new DuplicateCafeException(cafeIn.nomCafe());
                });
        // Transformation DTO → Entity
        Cafe cafe = cafeMapper.toEntity(cafeIn);

        // Sauvegarde
        Cafe savedCafe = cafeRepository.save(cafe);
        log.info("Café créé avec id {}", savedCafe.getId());
        // Transformation Entity → DTO OUT
        return cafeMapper.toDto(savedCafe);
    }

    @Override
    public CafeDtoOut updateCafe(Long id, CafeDtoIn cafeIn) {

        log.info("Mise à jour du café avec id {}", id);
        // Récupère le café existant ou lance exception si introuvable
        Cafe existingCafe = cafeRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Café avec id {} introuvable pour mise à jour", id);
                    return new CafeNotFoundException(id);
                });

        // Met à jour l'entité existante avec les nouvelles valeurs du DTO
        cafeMapper.updateEntityFromDto(cafeIn, existingCafe);

        // Sauvegarde les modifications
        Cafe updatedCafe = cafeRepository.save(existingCafe);
        log.info("Café avec id {} mis à jour", updatedCafe.getId());
        // Retourne DTO OUT
        return cafeMapper.toDto(updatedCafe);
    }

    @Override
    public void deleteCafe(Long id) {
        log.info("Suppression du café avec id {}", id);

        Cafe cafe = cafeRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Café avec id {} introuvable pour suppression", id);
                    return new CafeNotFoundException(id);
                });

        cafeRepository.delete(cafe);
        log.info("Café avec id {} supprimé", id);
    }

    @Override
    public List<CafeDtoOut> getAllCafe() {
       log.info("Récupération de tous les cafés");
       return cafeRepository.findAll().stream()
               .map(cafeMapper::toDto)
               .toList();
    }

    @Override
    public CafeDtoOut getCafeById(Long id) {

        log.info("Récupération du café avec id {}", id);

        return cafeRepository.findById(id)
                .map(cafeMapper::toDto)
                .orElseThrow(() -> {
                    log.warn("Café avec id {} introuvable", id);
                    return new CafeNotFoundException(id);
                });
    }




}
