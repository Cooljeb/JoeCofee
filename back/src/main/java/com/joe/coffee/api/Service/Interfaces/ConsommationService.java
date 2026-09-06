package com.joe.coffee.api.Service.Interfaces;

import com.joe.coffee.api.Dto.In.ConsommationDtoIn;
import com.joe.coffee.api.Dto.Out.ConsommationDtoOut;

import java.util.List;

/**
 * Interface du service des consommations.
 */
public interface ConsommationService {

    ConsommationDtoOut createConsommation(ConsommationDtoIn consommationIn);

    ConsommationDtoOut updateConsommation(Integer id, ConsommationDtoIn consommationIn);

    void deleteConsommation(Integer id);

    List<ConsommationDtoOut> getAllConsommations();

    ConsommationDtoOut getConsommationById(Integer id);
}
