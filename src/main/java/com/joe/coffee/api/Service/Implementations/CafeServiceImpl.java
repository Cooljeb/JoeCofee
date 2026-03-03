package com.joe.coffee.api.Service.Implementations;

import com.joe.coffee.api.Dto.In.CafeDtoIn;
import com.joe.coffee.api.Dto.Out.CafeDtoOut;
import com.joe.coffee.api.Entity.Cafe;
import com.joe.coffee.api.Entity.Commercant;
import com.joe.coffee.api.Enum.LabelCafe;
import com.joe.coffee.api.Enum.TypeCafe;
import com.joe.coffee.api.Exception.CafeExceptions.CafeCommercantNotFoundException;
import com.joe.coffee.api.Exception.CafeExceptions.CafeNotFoundException;
import com.joe.coffee.api.Exception.CafeExceptions.DuplicateCafeException;
import com.joe.coffee.api.Exception.CafeExceptions.EmptyCafeFilterException;
import com.joe.coffee.api.Mapper.CafeMapper;
import com.joe.coffee.api.Repository.CafeRepository;
import com.joe.coffee.api.Repository.CommercantRepository;
import com.joe.coffee.api.Service.Interfaces.CafeService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implémentation de CafeService
 */

@Service
@RequiredArgsConstructor
public class CafeServiceImpl implements CafeService {

    private final CafeRepository cafeRepository;
    private final CommercantRepository commercantRepository;
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
        // Vérifie si le commerçant existe en base sinon on refuse la création
        Commercant c = commercantRepository.findById(cafeIn.commercant())
                .orElseThrow(() -> new RuntimeException("Commercant non trouvé"));
        // Transformation DTO → Entity
        Cafe cafe = cafeMapper.toEntity(cafeIn);
        cafe.setCommercant(c);
        // Sauvegarde
        Cafe savedCafe = cafeRepository.save(cafe);
        log.info("Café créé avec id {}", savedCafe.getId());
        // Transformation Entity → DTO OUT
        return cafeMapper.toDto(savedCafe);
    }

    @Override
    public CafeDtoOut updateCafe(Integer id, CafeDtoIn cafeIn) {

        log.info("Mise à jour du café avec id {}", id);
        // Récupère le café existant ou lance exception si introuvable
        Cafe existingCafe = cafeRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Café avec id {} introuvable pour mise à jour", id);
                    return new CafeNotFoundException(id);
                });
        // Vérifie si le commerçant existe en base sinon on refuse la maj
        Commercant c = commercantRepository.findById(cafeIn.commercant())
                .orElseThrow(() -> new RuntimeException("Commercant non trouvé"));
        // Transformation DTO → Entity
        // Met à jour l'entité existante avec les nouvelles valeurs du DTO
        cafeMapper.updateEntityFromDto(cafeIn, existingCafe);
        existingCafe.setCommercant(c);
        // Sauvegarde les modifications
        Cafe updatedCafe = cafeRepository.save(existingCafe);
        log.info("Café avec id {} mis à jour", updatedCafe.getId());
        // Retourne DTO OUT
        return cafeMapper.toDto(updatedCafe);
    }

    @Override
    public void deleteCafe(Integer id) {
        log.info("Suppression du café avec id {}", id);

        Cafe cafe = cafeRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Café avec id {} introuvable pour suppression", id);
                    return new CafeNotFoundException(id);
                });
//        if (consommationRepository.existsByCafeId(id)) {
//            throw new IllegalStateException(
//                    "Impossible de supprimer ce café : des consommations existent"
//            log.warn("Impossible de supprimer ce café : des consommations existent");
//            );
//        }
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
    public CafeDtoOut getCafeById(Integer id) {

        log.info("Récupération du café avec id {}", id);

        return cafeRepository.findById(id)
                .map(cafeMapper::toDto)
                .orElseThrow(() -> {
                    log.warn("Café avec id {} introuvable", id);
                    return new CafeNotFoundException(id);
                });
    }

    @Override
    public List<CafeDtoOut> filterCafes(TypeCafe type, LabelCafe label) {
        if (type == null && label == null) {
            log.info("Aucun filtre fourni pour filterCafes");
            throw  new EmptyCafeFilterException(); // ou tu peux lancer une exception si tu préfères
        }

        log.info("Filtrage des cafés par type={} et label={}", type, label);

        // Construction de la spécification dynamique
        Specification<Cafe> spec = Specification
                .where(byType(type))
                .and(byLabel(label));

        // Exécution de la requête
        List<CafeDtoOut> cafes = cafeRepository.findAll(spec)
                .stream()
                .map(cafeMapper::toDto)
                .collect(Collectors.toList());

        // Vérification du résultat
        if (cafes.isEmpty()) {
            log.warn("Aucun café trouvé pour type={} et label={}", type, label);
            throw new CafeNotFoundException(
                    "Aucun café trouvé avec les critères fournis."
            );
        }

        log.info("{} café(s) trouvé(s)", cafes.size());

        return cafes;
    }

    @Override
    public List<CafeDtoOut> getCafesByCommercantId(Integer commercantId) {
        log.info("Récupération des cafés pour le commerçant avec id {}", commercantId);

        List<Cafe> cafes = cafeRepository.findByCommercantId(commercantId);

        if (cafes.isEmpty()) {
            log.warn("Aucun café trouvé pour le commerçant avec id {}", commercantId);
            throw new CafeCommercantNotFoundException(commercantId);
        }

        return cafes.stream()
                .map(cafeMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Spécification pour filtrer le type de café
     * @param type type de café
     * @return la spécification du type de café trouvé
     */
    private Specification<Cafe> byType(TypeCafe type) {
        return (root, query, cb) -> {
            if (type == null) return null; // ignore si type non fourni
            return cb.equal(root.get("typeCafe"), type);
        };
    }

    /**
     * Spécification pour filtrer  le lable de café
     * @param label label du café
     * @return la spécification du label de café trouvé
     */
    private Specification<Cafe> byLabel(LabelCafe label) {
        return (root, query, cb) -> {
            if (label == null) return null; // ignore si label non fourni
            return cb.equal(root.get("labelCafe"), label);
        };
    }


}
