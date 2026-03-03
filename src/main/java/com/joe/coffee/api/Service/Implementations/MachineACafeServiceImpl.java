package com.joe.coffee.api.Service.Implementations;

import com.joe.coffee.api.Dto.In.MachineACafeDtoIn;
import com.joe.coffee.api.Dto.Out.MachineACafeDtoOut;
import com.joe.coffee.api.Entity.MachineACafe;
import com.joe.coffee.api.Exception.MachineACafeExceptions.DuplicateMachineACafeException;
import com.joe.coffee.api.Exception.MachineACafeExceptions.MachineACafeNotFoundException;
import com.joe.coffee.api.Mapper.MachineACafeMapper;
import com.joe.coffee.api.Repository.MachineACafeRepository;
import com.joe.coffee.api.Service.Interfaces.MachineACafeService;
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
public class MachineACafeServiceImpl implements MachineACafeService {

    private  final MachineACafeRepository machineACafeRepository;
    private final MachineACafeMapper machineACafeMapper;
    private static final Logger log = LoggerFactory.getLogger(MachineACafeServiceImpl.class);

    @Override
    public MachineACafeDtoOut createMachineACafe(MachineACafeDtoIn machineACafeIn) {

        log.info("Création d'une marque avec le nom '{}'",machineACafeIn.nomCommercial());
        // Vérifie si une machine à café avec le même nom existe déjà
        machineACafeRepository.findByNomCommercialContainingIgnoreCase(machineACafeIn.nomCommercial())
                .ifPresent( m -> {
                    log.warn("Tentative de création d'une machine à café déjà existante: '{}'",machineACafeIn.nomCommercial());
                    throw new DuplicateMachineACafeException(machineACafeIn.nomCommercial());
                });
        // Transformation DTO → Entity
        MachineACafe machineACafe = machineACafeMapper.toEntity(machineACafeIn);

        // Sauvegarde
        MachineACafe mmachineACafeSaved = machineACafeRepository.save(machineACafe);
        log.info("Machine à café créée avec id {}", mmachineACafeSaved.getId());
        return machineACafeMapper.toDto(mmachineACafeSaved);
    }

    @Override
    public List<MachineACafeDtoOut> getAllMachineACafe() {

        log.info("Récupération de toutes les machines à café");
        return machineACafeRepository.findAll().stream()
                .map(machineACafeMapper::toDto)
                .toList();

    }

    @Override
    public MachineACafeDtoOut getMachineACafeById(Integer id) {

        log.info("Récupération de la machine à café avec id {}", id);

        return machineACafeRepository.findById(id)
                .map(machineACafeMapper::toDto)
                .orElseThrow(() -> {
                    log.warn("Machine à café avec id {} introuvable", id);
                    return new MachineACafeNotFoundException(id);
                });
    }

    @Override
    public MachineACafeDtoOut updateMachineACafe(Integer id, MachineACafeDtoIn machineACafeIn) {

        log.info("Mise à jour de la machine à café avec id {}", id);
        // Récupère la machine à café existante ou lance exception si introuvable
        MachineACafe existingMachineACafe = machineACafeRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Machine à café avec id {} introuvable mise à jour impossible", id);
                    return new MachineACafeNotFoundException(id);
                });

        // Met à jour l'entité existante avec les nouvelles valeurs du DTO
        machineACafeMapper.updateEntityFromDto(machineACafeIn, existingMachineACafe);

        // Sauvegarde les modifications
        MachineACafe updatedMachineACafe = machineACafeRepository.save(existingMachineACafe);
        log.info("Machine à café avec id {} mise à jour", updatedMachineACafe.getId());
        // Retourne DTO OUT
        return machineACafeMapper.toDto(updatedMachineACafe);
    }

    @Override
    public MachineACafeDtoOut getMachineACafeByName(String name) {

        log.info("Recherche de la machine à café avec id {}", name);

        return machineACafeRepository.findByNomCommercialContainingIgnoreCase(name)
                .map(machineACafeMapper::toDto)
                .orElseThrow(() -> {
                    log.warn("Machine à café avec le nom {} introuvable", name);
                    return new MachineACafeNotFoundException(name);
                });

    }

    @Override
    public void deleteMachineACafe(Integer id) {

        log.info("Suppression de la marque avec id {}", id);

        MachineACafe machineACafe = machineACafeRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Machine à café avec id {} introuvable suppression impossible", id);
                    return new MachineACafeNotFoundException(id);
                });
        machineACafeRepository.delete(machineACafe);
        log.info("Machine à café avec id {} supprimée", id);
    }
}
