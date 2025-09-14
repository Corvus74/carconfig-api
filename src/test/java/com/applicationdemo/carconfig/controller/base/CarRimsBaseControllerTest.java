package com.applicationdemo.carconfig.controller.base;

import com.applicationdemo.carconfig.dto.load.CarRimLoadDto;
import com.applicationdemo.carconfig.exceptions.ItemAddException;
import com.applicationdemo.carconfig.services.base.CarRimService;
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
@WebMvcTest(CarRimsBaseController.class)
class CarRimsBaseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CarRimService carRimService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllCarRims() throws Exception {
        CarRimLoadDto carRimLoadDto = new CarRimLoadDto();
        carRimLoadDto.setModel("ModelX");
        List<CarRimLoadDto> carRimLoadDtos = Collections.singletonList(carRimLoadDto);
        when(carRimService.getAllCarRimLoad()).thenReturn(carRimLoadDtos);

        mockMvc.perform(get("/load/car-rims/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].model").value("ModelX"));
    }

    @Test
    void addCarRim_success() throws Exception {
        CarRimLoadDto carRimLoadDto = new CarRimLoadDto();
        carRimLoadDto.setModel("ModelX");

        mockMvc.perform(post("/load/car-rims/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(carRimLoadDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text").value("Car rim added successfully"))
                .andExpect(jsonPath("$.status").value("SUCCESS"));
    }

    @Test
    void addCarRim_failure() throws Exception {
        CarRimLoadDto carRimLoadDto = new CarRimLoadDto();
        carRimLoadDto.setModel("ModelX");
        doThrow(new ItemAddException("Rim already exists")).when(carRimService).addCarRimLoad(any(CarRimLoadDto.class));

        mockMvc.perform(post("/load/car-rims/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(carRimLoadDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text").value("Failed to add car rim"))
                .andExpect(jsonPath("$.status").value("FAILURE"))
                .andExpect(jsonPath("$.errorMessage").value("Rim already exists"));
    }

    @Test
    void addAllCarRims_success() throws Exception {
        CarRimLoadDto carRimLoadDto = new CarRimLoadDto();
        carRimLoadDto.setModel("ModelX");
        List<CarRimLoadDto> carRimLoadDtos = Collections.singletonList(carRimLoadDto);

        mockMvc.perform(post("/load/car-rims/add/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(carRimLoadDtos)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text").value("All car rims added successfully"))
                .andExpect(jsonPath("$.status").value("SUCCESS"));
    }

    @Test
    void addAllCarRims_failure() throws Exception {
        CarRimLoadDto carRimLoadDto = new CarRimLoadDto();
        carRimLoadDto.setModel("ModelX");
        List<CarRimLoadDto> carRimLoadDtos = Collections.singletonList(carRimLoadDto);
        doThrow(new ItemAddException("Duplicate rim")).when(carRimService).addAllCarRims(any());

        mockMvc.perform(post("/load/car-rims/add/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(carRimLoadDtos)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text").value("Failed to add one or more car rims"))
                .andExpect(jsonPath("$.status").value("FAILURE"))
                .andExpect(jsonPath("$.errorMessage").value("Duplicate rim"));
    }
}
