package com.joe.coffee.api.Service.Implementations;

import com.joe.coffee.api.Dto.In.ConsommationDtoIn;
import com.joe.coffee.api.Dto.Out.ConsommationDtoOut;
import com.joe.coffee.api.Entity.Cafe;
import com.joe.coffee.api.Entity.Consommation;
import com.joe.coffee.api.Entity.MachineACafe;
import com.joe.coffee.api.Exception.CafeExceptions.CafeNotFoundException;
import com.joe.coffee.api.Exception.ConsommationExceptions.ConsommationNotFoundException;
import com.joe.coffee.api.Exception.MachineACafeExceptions.MachineACafeNotFoundException;
import com.joe.coffee.api.Mapper.ConsommationMapper;
import com.joe.coffee.api.Repository.CafeRepository;
import com.joe.coffee.api.Repository.ConsommationRepository;
import com.joe.coffee.api.Repository.MachineACafeRepository;
import com.joe.coffee.api.Service.Interfaces.ConsommationService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implémentation du service des consommations.
 *
 * Une consommation relie obligatoirement un café et une machine existants.
 * Le service porte cette règle afin que les contrôleurs restent responsables
 * uniquement de l'exposition HTTP de l'API.
 */
@Service
@RequiredArgsConstructor
public class ConsommationServiceImpl implements ConsommationService {

    private static final Logger log = LoggerFactory.getLogger(ConsommationServiceImpl.class);

    private final ConsommationRepository consommationRepository;
    private final CafeRepository cafeRepository;
    private final MachineACafeRepository machineACafeRepository;
    private final ConsommationMapper consommationMapper;

    @Override
    public ConsommationDtoOut createConsommation(ConsommationDtoIn consommationIn) {
        log.info("Création d'une consommation pour cafeId={} et machineACafeId={}",
                consommationIn.cafeId(), consommationIn.machineACafeId());

        // Vérifie d'abord les deux références métier : on ne sauvegarde jamais
        // une consommation qui pointerait vers un café ou une machine inexistante.
        Cafe cafe = getCafe(consommationIn.cafeId());
        MachineACafe machine = getMachine(consommationIn.machineACafeId());

        // Transformation DTO -> Entity. Les relations sont affectées ici et non
        // dans le mapper car elles proviennent d'entités réellement chargées en base.
        Consommation consommation = new Consommation();
        consommationMapper.updateEntityFromDto(consommationIn, consommation);
        consommation.setCafe(cafe);
        consommation.setMachineACafe(machine);

        // Sauvegarde puis transformation Entity -> DTO OUT.
        Consommation saved = consommationRepository.save(consommation);
        log.info("Consommation créée avec id {}", saved.getCodeConsommation());
        return consommationMapper.toDto(saved);
    }

    @Override
    public ConsommationDtoOut updateConsommation(Integer id, ConsommationDtoIn consommationIn) {
        log.info("Mise à jour de la consommation avec id {}", id);

        // Récupère la consommation existante ou lance une exception si introuvable.
        Consommation existing = consommationRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Consommation avec id {} introuvable pour mise à jour", id);
                    return new ConsommationNotFoundException(id);
                });

        // Comme lors de la création, les références reçues du Front doivent
        // correspondre à des objets existants avant toute modification en base.
        Cafe cafe = getCafe(consommationIn.cafeId());
        MachineACafe machine = getMachine(consommationIn.machineACafeId());

        // Met à jour les valeurs simples puis remplace explicitement les relations.
        consommationMapper.updateEntityFromDto(consommationIn, existing);
        existing.setCafe(cafe);
        existing.setMachineACafe(machine);

        // Sauvegarde les modifications et retourne le DTO exposé par l'API.
        Consommation updated = consommationRepository.save(existing);
        log.info("Consommation avec id {} mise à jour", updated.getCodeConsommation());
        return consommationMapper.toDto(updated);
    }

    @Override
    public void deleteConsommation(Integer id) {
        log.info("Suppression de la consommation avec id {}", id);

        // On charge l'entité avant suppression afin de conserver le même comportement
        // que les autres services : une ressource inconnue produit une exception métier.
        Consommation consommation = consommationRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Consommation avec id {} introuvable pour suppression", id);
                    return new ConsommationNotFoundException(id);
                });

        consommationRepository.delete(consommation);
        log.info("Consommation avec id {} supprimée", id);
    }

    @Override
    public List<ConsommationDtoOut> getAllConsommations() {
        log.info("Récupération de toutes les consommations");

        // Transformation de chaque entité en DTO afin de ne jamais exposer
        // directement les entités JPA au consommateur de l'API.
        return consommationRepository.findAll().stream()
                .map(consommationMapper::toDto)
                .toList();
    }

    @Override
    public ConsommationDtoOut getConsommationById(Integer id) {
        log.info("Récupération de la consommation avec id {}", id);

        return consommationRepository.findById(id)
                .map(consommationMapper::toDto)
                .orElseThrow(() -> {
                    log.warn("Consommation avec id {} introuvable", id);
                    return new ConsommationNotFoundException(id);
                });
    }

    /**
     * Récupère le café référencé par une consommation.
     *
     * @param cafeId identifiant du café reçu dans le DTO d'entrée
     * @return le café existant en base
     * @throws CafeNotFoundException si l'identifiant ne correspond à aucun café
     */
    private Cafe getCafe(Integer cafeId) {
        return cafeRepository.findById(cafeId)
                .orElseThrow(() -> {
                    log.warn("Café avec id {} introuvable pour la consommation", cafeId);
                    return new CafeNotFoundException(cafeId);
                });
    }

    /**
     * Récupère la machine référencée par une consommation.
     *
     * @param machineACafeId identifiant de la machine reçu dans le DTO d'entrée
     * @return la machine existante en base
     * @throws MachineACafeNotFoundException si l'identifiant ne correspond à aucune machine
     */
    private MachineACafe getMachine(Integer machineACafeId) {
        return machineACafeRepository.findById(machineACafeId)
                .orElseThrow(() -> {
                    log.warn("Machine à café avec id {} introuvable pour la consommation", machineACafeId);
                    return new MachineACafeNotFoundException(machineACafeId);
                });
    }
}
