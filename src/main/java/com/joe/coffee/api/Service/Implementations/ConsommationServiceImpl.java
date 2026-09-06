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

        Cafe cafe = getCafe(consommationIn.cafeId());
        MachineACafe machine = getMachine(consommationIn.machineACafeId());

        Consommation consommation = new Consommation();
        consommationMapper.updateEntityFromDto(consommationIn, consommation);
        consommation.setCafe(cafe);
        consommation.setMachineACafe(machine);

        Consommation saved = consommationRepository.save(consommation);
        log.info("Consommation créée avec id {}", saved.getCodeConsommation());
        return consommationMapper.toDto(saved);
    }

    @Override
    public ConsommationDtoOut updateConsommation(Integer id, ConsommationDtoIn consommationIn) {
        log.info("Mise à jour de la consommation avec id {}", id);

        Consommation existing = consommationRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Consommation avec id {} introuvable pour mise à jour", id);
                    return new ConsommationNotFoundException(id);
                });

        Cafe cafe = getCafe(consommationIn.cafeId());
        MachineACafe machine = getMachine(consommationIn.machineACafeId());

        consommationMapper.updateEntityFromDto(consommationIn, existing);
        existing.setCafe(cafe);
        existing.setMachineACafe(machine);

        Consommation updated = consommationRepository.save(existing);
        log.info("Consommation avec id {} mise à jour", updated.getCodeConsommation());
        return consommationMapper.toDto(updated);
    }

    @Override
    public void deleteConsommation(Integer id) {
        log.info("Suppression de la consommation avec id {}", id);

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

    private Cafe getCafe(Integer cafeId) {
        return cafeRepository.findById(cafeId)
                .orElseThrow(() -> {
                    log.warn("Café avec id {} introuvable pour la consommation", cafeId);
                    return new CafeNotFoundException(cafeId);
                });
    }

    private MachineACafe getMachine(Integer machineACafeId) {
        return machineACafeRepository.findById(machineACafeId)
                .orElseThrow(() -> {
                    log.warn("Machine à café avec id {} introuvable pour la consommation", machineACafeId);
                    return new MachineACafeNotFoundException(machineACafeId);
                });
    }
}
