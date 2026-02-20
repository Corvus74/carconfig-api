package com.applicationdemo.carconfig.controller.base;

import com.applicationdemo.carconfig.dto.load.CarColorLoadDto;
import com.applicationdemo.carconfig.exceptions.ItemAddException;
import com.applicationdemo.carconfig.services.base.CarColorService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

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
@WebMvcTest(CarColorBaseController.class)
class CarColorBaseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CarColorService carColorService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllCarColorsLoad() throws Exception {
        CarColorLoadDto carColorLoadDto = new CarColorLoadDto();
        carColorLoadDto.setColorName("Red");
        List<CarColorLoadDto> carColorLoadDtos = Collections.singletonList(carColorLoadDto);
        when(carColorService.getAllCarColorLoad()).thenReturn(carColorLoadDtos);

        mockMvc.perform(get("/load/car-color/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].colorName").value("Red"));
    }

    @Test
    void addCarColor_success() throws Exception {
        CarColorLoadDto carColorLoadDto = new CarColorLoadDto();
        carColorLoadDto.setColorName("Red");

        mockMvc.perform(post("/load/car-color/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(carColorLoadDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text").value("Car color added successfully"))
                .andExpect(jsonPath("$.status").value("SUCCESS"));
    }

    @Test
    void addCarColor_failure() throws Exception {
        CarColorLoadDto carColorLoadDto = new CarColorLoadDto();
        carColorLoadDto.setColorName("Red");
        doThrow(new ItemAddException("Color already exists")).when(carColorService).addCarColor(any(CarColorLoadDto.class));

        mockMvc.perform(post("/load/car-color/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(carColorLoadDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text").value("Failed to add car color"))
                .andExpect(jsonPath("$.status").value("FAILURE"))
                .andExpect(jsonPath("$.errorMessage").value("Color already exists"));
    }

    @Test
    void addAllCarColors_success() throws Exception {
        CarColorLoadDto carColorLoadDto = new CarColorLoadDto();
        carColorLoadDto.setColorName("Red");
        List<CarColorLoadDto> carColorLoadDtos = Collections.singletonList(carColorLoadDto);

        mockMvc.perform(post("/load/car-color/add/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(carColorLoadDtos)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text").value("All car colors added successfully"))
                .andExpect(jsonPath("$.status").value("SUCCESS"));
    }

    @Test
    void addAllCarColors_failure() throws Exception {
        CarColorLoadDto carColorLoadDto = new CarColorLoadDto();
        carColorLoadDto.setColorName("Red");
        List<CarColorLoadDto> carColorLoadDtos = Collections.singletonList(carColorLoadDto);
        doThrow(new ItemAddException("Duplicate color")).when(carColorService).addAllCarColors(any());

        mockMvc.perform(post("/load/car-color/add/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(carColorLoadDtos)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text").value("Failed to add one or more car colors"))
                .andExpect(jsonPath("$.status").value("FAILURE"))
                .andExpect(jsonPath("$.errorMessage").value("Duplicate color"));
    }
}
