package com.applicationdemo.carconfig.controller.base;

import com.applicationdemo.carconfig.dto.load.CarEngineLoadDto;
import com.applicationdemo.carconfig.exceptions.ItemAddException;
import com.applicationdemo.carconfig.services.base.CarEngineService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(CarEngineBaseController.class)
class CarEngineBaseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CarEngineService carEngineService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllCarEngines() throws Exception {
        CarEngineLoadDto carEngineLoadDto = new CarEngineLoadDto();
        carEngineLoadDto.setModel("V8");
        List<CarEngineLoadDto> carEngineLoadDtos = Collections.singletonList(carEngineLoadDto);
        when(carEngineService.getAllCarEngineLoad()).thenReturn(carEngineLoadDtos);

        mockMvc.perform(get("/load/car-engine/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].model").value("V8"));
    }

    @Test
    void addCarEngine_success() throws Exception {
        CarEngineLoadDto carEngineLoadDto = new CarEngineLoadDto();
        carEngineLoadDto.setModel("V8");

        mockMvc.perform(post("/load/car-engine/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(carEngineLoadDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text").value("Car engine added successfully"))
                .andExpect(jsonPath("$.status").value("SUCCESS"));
    }

    @Test
    void addCarEngine_failure() throws Exception {
        CarEngineLoadDto carEngineLoadDto = new CarEngineLoadDto();
        carEngineLoadDto.setModel("V8");
        doThrow(new ItemAddException("Engine already exists")).when(carEngineService).addCarEngine(any(CarEngineLoadDto.class));

        mockMvc.perform(post("/load/car-engine/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(carEngineLoadDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text").value("Failed to add car engine"))
                .andExpect(jsonPath("$.status").value("FAILURE"))
                .andExpect(jsonPath("$.errorMessage").value("Engine already exists"));
    }

    @Test
    void addAllCarEngines_success() throws Exception {
        CarEngineLoadDto carEngineLoadDto = new CarEngineLoadDto();
        carEngineLoadDto.setModel("V8");
        List<CarEngineLoadDto> carEngineLoadDtos = Collections.singletonList(carEngineLoadDto);

        mockMvc.perform(post("/load/car-engine/add/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(carEngineLoadDtos)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text").value("All car engines added successfully"))
                .andExpect(jsonPath("$.status").value("SUCCESS"));
    }

    @Test
    void addAllCarEngines_failure() throws Exception {
        CarEngineLoadDto carEngineLoadDto = new CarEngineLoadDto();
        carEngineLoadDto.setModel("V8");
        List<CarEngineLoadDto> carEngineLoadDtos = Collections.singletonList(carEngineLoadDto);
        doThrow(new ItemAddException("Duplicate engine")).when(carEngineService).addAllCarEngines(any());

        mockMvc.perform(post("/load/car-engine/add/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(carEngineLoadDtos)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text").value("Failed to add one or more car engines"))
                .andExpect(jsonPath("$.status").value("FAILURE"))
                .andExpect(jsonPath("$.errorMessage").value("Duplicate engine"));
    }
}
