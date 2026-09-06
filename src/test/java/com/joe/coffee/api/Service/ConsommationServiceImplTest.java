package com.joe.coffee.api.Service;

import com.joe.coffee.api.Dto.In.ConsommationDtoIn;
import com.joe.coffee.api.Dto.Out.ConsommationDtoOut;
import com.joe.coffee.api.Entity.Cafe;
import com.joe.coffee.api.Entity.Consommation;
import com.joe.coffee.api.Entity.MachineACafe;
import com.joe.coffee.api.Exception.CafeExceptions.CafeNotFoundException;
import com.joe.coffee.api.Exception.ConsommationExceptions.ConsommationNotFoundException;
import com.joe.coffee.api.Mapper.ConsommationMapper;
import com.joe.coffee.api.Repository.CafeRepository;
import com.joe.coffee.api.Repository.ConsommationRepository;
import com.joe.coffee.api.Repository.MachineACafeRepository;
import com.joe.coffee.api.Service.Implementations.ConsommationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConsommationServiceImplTest {

    @Mock
    private ConsommationRepository consommationRepository;
    @Mock
    private CafeRepository cafeRepository;
    @Mock
    private MachineACafeRepository machineACafeRepository;
    @Mock
    private ConsommationMapper consommationMapper;

    private ConsommationServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new ConsommationServiceImpl(
                consommationRepository,
                cafeRepository,
                machineACafeRepository,
                consommationMapper
        );
    }

    @Test
    void createConsommation_shouldSaveAndReturnDto() {
        ConsommationDtoIn input = new ConsommationDtoIn((byte) 2, (byte) 3, 10, 20);
        Cafe cafe = new Cafe();
        MachineACafe machine = new MachineACafe();
        Consommation saved = new Consommation();
        saved.setCodeConsommation(1);
        ConsommationDtoOut expected = new ConsommationDtoOut(1, (byte) 2, (byte) 3, 10, 20);

        when(cafeRepository.findById(10)).thenReturn(Optional.of(cafe));
        when(machineACafeRepository.findById(20)).thenReturn(Optional.of(machine));
        when(consommationRepository.save(any(Consommation.class))).thenReturn(saved);
        when(consommationMapper.toDto(saved)).thenReturn(expected);

        ConsommationDtoOut result = service.createConsommation(input);

        assertEquals(expected, result);
        verify(consommationMapper).updateEntityFromDto(input, any(Consommation.class));
        verify(consommationRepository).save(any(Consommation.class));
    }

    @Test
    void createConsommation_shouldRejectUnknownCafe() {
        ConsommationDtoIn input = new ConsommationDtoIn((byte) 2, (byte) 3, 10, 20);
        when(cafeRepository.findById(10)).thenReturn(Optional.empty());

        assertThrows(CafeNotFoundException.class, () -> service.createConsommation(input));

        verify(machineACafeRepository, never()).findById(any());
        verify(consommationRepository, never()).save(any());
    }

    @Test
    void getConsommationById_shouldThrowWhenMissing() {
        when(consommationRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(ConsommationNotFoundException.class, () -> service.getConsommationById(99));
    }

    @Test
    void getAllConsommations_shouldMapRepositoryResults() {
        Consommation first = new Consommation();
        Consommation second = new Consommation();
        ConsommationDtoOut firstDto = new ConsommationDtoOut(1, (byte) 1, (byte) 2, 10, 20);
        ConsommationDtoOut secondDto = new ConsommationDtoOut(2, (byte) 2, (byte) 3, 11, 21);

        when(consommationRepository.findAll()).thenReturn(List.of(first, second));
        when(consommationMapper.toDto(first)).thenReturn(firstDto);
        when(consommationMapper.toDto(second)).thenReturn(secondDto);

        assertEquals(List.of(firstDto, secondDto), service.getAllConsommations());
    }
}
