package com.joe.coffee.api.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.joe.coffee.api.Dto.In.ConsommationDtoIn;
import com.joe.coffee.api.Dto.Out.ConsommationDtoOut;
import com.joe.coffee.api.Service.Interfaces.ConsommationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ConsommationController.class)
class ConsommationControllerTest {

    private static final String BASE_PATH = "/api/consommations";

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @MockBean private ConsommationService consommationService;

    @Test
    void getAllConsommationsReturnsConsommations() throws Exception {
        when(consommationService.getAllConsommations()).thenReturn(List.of(consommationDto()));
        mockMvc.perform(get(BASE_PATH))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].codeConsommation").value(1))
                .andExpect(jsonPath("$[0].cafeId").value(2));
    }

    @Test
    void getConsommationByIdReturnsConsommation() throws Exception {
        when(consommationService.getConsommationById(1)).thenReturn(consommationDto());
        mockMvc.perform(get(BASE_PATH + "/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.machineACafeId").value(3));
    }

    @Test
    void createConsommationReturnsCreated() throws Exception {
        when(consommationService.createConsommation(any(ConsommationDtoIn.class))).thenReturn(consommationDto());
        mockMvc.perform(post(BASE_PATH).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(consommationInput())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.codeConsommation").value(1));
    }

    @Test
    void createConsommationRejectsInvalidPayload() throws Exception {
        ConsommationDtoIn invalid = new ConsommationDtoIn(null, null, null, null);
        mockMvc.perform(post(BASE_PATH).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalid)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateConsommationReturnsUpdated() throws Exception {
        when(consommationService.updateConsommation(eq(1), any(ConsommationDtoIn.class))).thenReturn(consommationDto());
        mockMvc.perform(put(BASE_PATH + "/1").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(consommationInput())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reglageIntensite").value(4));
    }

    @Test
    void deleteConsommationReturnsNoContent() throws Exception {
        mockMvc.perform(delete(BASE_PATH + "/1")).andExpect(status().isNoContent());
        verify(consommationService).deleteConsommation(1);
    }

    private ConsommationDtoIn consommationInput() {
        return new ConsommationDtoIn((byte) 3, (byte) 4, 2, 3);
    }

    private ConsommationDtoOut consommationDto() {
        return new ConsommationDtoOut(1, (byte) 3, (byte) 4, 2, 3);
    }
}
