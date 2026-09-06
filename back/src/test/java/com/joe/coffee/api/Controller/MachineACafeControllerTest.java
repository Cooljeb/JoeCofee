package com.joe.coffee.api.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.joe.coffee.api.Dto.In.MachineACafeDtoIn;
import com.joe.coffee.api.Dto.Out.MachineACafeDtoOut;
import com.joe.coffee.api.Service.Interfaces.MachineACafeService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MachineACafeController.class)
class MachineACafeControllerTest {

    private static final String BASE_PATH = "/api/machines-a-cafe";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MachineACafeService machineACafeService;

    @Test
    void getAllMachinesACafeReturnsMachines() throws Exception {
        when(machineACafeService.getAllMachineACafe()).thenReturn(List.of(machineDto()));

        mockMvc.perform(get(BASE_PATH))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nomCommercial").value("Magnifica S"));
    }

    @Test
    void getMachineACafeByIdReturnsMachine() throws Exception {
        when(machineACafeService.getMachineACafeById(1)).thenReturn(machineDto());

        mockMvc.perform(get(BASE_PATH + "/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.referenceCommerciale").value("ECAM22.110.B"));
    }

    @Test
    void getMachineACafeByNameReturnsMachine() throws Exception {
        when(machineACafeService.getMachineACafeByName("Magnifica S")).thenReturn(machineDto());

        mockMvc.perform(get(BASE_PATH + "/name/{name}", "Magnifica S"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.marque").value("De'Longhi"));
    }

    @Test
    void createMachineACafeReturnsCreatedMachine() throws Exception {
        MachineACafeDtoIn input = machineInput();
        when(machineACafeService.createMachineACafe(any(MachineACafeDtoIn.class))).thenReturn(machineDto());

        mockMvc.perform(post(BASE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void createMachineACafeRejectsInvalidPayload() throws Exception {
        MachineACafeDtoIn input = new MachineACafeDtoIn("", "", "", null);

        mockMvc.perform(post(BASE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.nomCommercial").exists())
                .andExpect(jsonPath("$.errors.marqueId").exists());
    }

    @Test
    void updateMachineACafeReturnsUpdatedMachine() throws Exception {
        MachineACafeDtoIn input = machineInput();
        when(machineACafeService.updateMachineACafe(eq(1), any(MachineACafeDtoIn.class))).thenReturn(machineDto());

        mockMvc.perform(put(BASE_PATH + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomCommercial").value("Magnifica S"));
    }

    @Test
    void deleteMachineACafeReturnsNoContent() throws Exception {
        mockMvc.perform(delete(BASE_PATH + "/1"))
                .andExpect(status().isNoContent());

        verify(machineACafeService).deleteMachineACafe(1);
    }

    private MachineACafeDtoIn machineInput() {
        return new MachineACafeDtoIn(
                "Magnifica S",
                "ECAM22.110.B",
                "Machine automatique avec broyeur intégré",
                1
        );
    }

    private MachineACafeDtoOut machineDto() {
        return new MachineACafeDtoOut(
                1,
                "Magnifica S",
                "ECAM22.110.B",
                "Machine automatique avec broyeur intégré",
                "De'Longhi"
        );
    }
}
