package com.joe.coffee.api.Service.Interfaces;

import com.joe.coffee.api.Dto.In.MachineACafeDtoIn;
import com.joe.coffee.api.Dto.Out.MachineACafeDtoOut;

import java.util.List;

/**
 * Interface du service des machines à café
 */
public interface MachineACafeService {

    /**
     * Méthode de création d'une machine à café
     * @param machineACafeIn à café Dto  d'entrée
     * @return Dto de sortie
     */
    MachineACafeDtoOut createMachineACafe(MachineACafeDtoIn machineACafeIn);

    /**
     * Méthode qui permet de voir toutes les machines à café
     * @return toutes les machines à café présentes dans la bdd
     */
    List<MachineACafeDtoOut> getAllMachineACafe();

    /**
     * Retourne une machine à café à partir de son id
     * @param id de la machine à café
     * @return la machine à café depuis son id
     */
    MachineACafeDtoOut getMachineACafeById(Integer id);

    /**
     * Méthode de mise à jour d'une machine à café
     * @param id de la machine à café
     * @param machineACafeIn Dto d'entrée
     * @return DTO de sortie
     */
    MachineACafeDtoOut updateMachineACafe(Integer id, MachineACafeDtoIn machineACafeIn);

    /**
     * Méthode de recherche d'une machine à café depuis son nom
     * @param name nom de la machine à café
     * @return Dto de sortie
     */
    MachineACafeDtoOut getMachineACafeByName(String name);


    /**
     * Méthode de suppression d'une machine à café
     * @param id de la machine à café
     */
    void  deleteMachineACafe(Integer id);
}

