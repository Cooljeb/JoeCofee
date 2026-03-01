package com.joe.coffee.api.Service.Implementations;

import com.joe.coffee.api.Dto.In.DistributeurDtoIn;
import com.joe.coffee.api.Dto.Out.DistributeurDtoOut;
import com.joe.coffee.api.Entity.Distributeur;
import com.joe.coffee.api.Exception.DistributeurExceptions.DistributeurNotFoundException;
import com.joe.coffee.api.Exception.DistributeurExceptions.DuplicateDistributeurException;
import com.joe.coffee.api.Mapper.DistributeurMapper;
import com.joe.coffee.api.Repository.DistributeurRepository;
import com.joe.coffee.api.Service.Interfaces.DistributeurService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DistributeurServiceImpl implements DistributeurService {

    private final DistributeurRepository DistributeurRepository;
    private final DistributeurMapper DistributeurMapper;
    private static final Logger log = LoggerFactory.getLogger(DistributeurServiceImpl.class);


    @Override
    public DistributeurDtoOut createDistributeur(DistributeurDtoIn DistributeurIn) {

        log.info("Création distributeur avec le nom '{}'",DistributeurIn.nom());
        // Vérifie si un distributeur avec le même nom existe déjà
        DistributeurRepository.findByNomIgnoreCase(DistributeurIn.nom())
                .ifPresent( d -> {
                    log.warn("Tentative de création d'un at déjà existant: '{}'",DistributeurIn.nom());
                    throw new DuplicateDistributeurException(DistributeurIn.nom());
                });
        // Transformation DTO → Entity
        Distributeur d = DistributeurMapper.toEntity(DistributeurIn);

        // Sauvegarde
        Distributeur distribSaved = DistributeurRepository.save(d);
        log.info("distributeur créé avec id {}", distribSaved.getId());
        return DistributeurMapper.toDto(distribSaved);
    }

    @Override
    public List<DistributeurDtoOut> getAllDistributeur() {
        log.info("Récupération de tous les distributeur");
        return DistributeurRepository.findAll().stream()
                .map(DistributeurMapper::toDto)
                .toList();

    }

    @Override
    public DistributeurDtoOut getDistributeurById(Integer id) {
        log.info("Récupération du distributeur avec id {}", id);

        return DistributeurRepository.findById(id)
                .map(DistributeurMapper::toDto)
                .orElseThrow(() -> {
                    log.warn("distributeur avec id {} introuvable", id);
                    return new DistributeurNotFoundException(id);
                });
    }

    @Override
    public DistributeurDtoOut getDistributeurByName(String name) {
        log.info("Recherche du distributeur avec le nom {}", name);

        return DistributeurRepository.findByNomIgnoreCase(name)
                .map(DistributeurMapper::toDto)
                .orElseThrow(() -> {
                    log.warn("distributeur avec le nom {} introuvable", name);
                    return new DistributeurNotFoundException(name);
                });
    }

    @Override
    public DistributeurDtoOut updateDistributeur(Integer id, DistributeurDtoIn DistributeurIn) {
        log.info("Mise à jour de la marque avec id {}", id);
        // Récupère le distributeur existant ou lance exception si introuvable
        Distributeur existingDistributeur = DistributeurRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("distributeur avec id {} introuvable mise à jour impossible", id);
                    return new DistributeurNotFoundException(id);
                });

        // Met à jour l'entité existante avec les nouvelles valeurs du DTO
        DistributeurMapper.updateEntityFromDto(DistributeurIn, existingDistributeur);

        // Sauvegarde les modifications
        Distributeur updatedDistributeur = DistributeurRepository.save(existingDistributeur);
        log.info("distributeur avec id {} mise à jour", updatedDistributeur.getId());
        // Retourne DTO OUT
        return DistributeurMapper.toDto(updatedDistributeur);
    }

    @Override
    public void deleteDistributeur(Integer id) {

        log.info("Suppression du distributeur avec id {}", id);

        Distributeur Distributeur = DistributeurRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("distributeur avec id {} introuvable suppression impossible", id);
                    return new DistributeurNotFoundException(id);
                });
        DistributeurRepository.delete(Distributeur);
        log.info("Distributeur avec id {} supprimé", id);

    }

}

