package com.applicationdemo.carconfig.controller.base;

import com.applicationdemo.carconfig.dto.load.SpecialEquipmentLoadDto;
import com.applicationdemo.carconfig.exceptions.ItemAddException;
import com.applicationdemo.carconfig.services.base.SpecialEquipmentService;
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
@WebMvcTest(SpecialEquipmentBaseController.class)
class SpecialEquipmentBaseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SpecialEquipmentService specialEquipmentService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllSpecialEquipment() throws Exception {
        SpecialEquipmentLoadDto specialEquipmentLoadDto = new SpecialEquipmentLoadDto();
        specialEquipmentLoadDto.setEquipmentName("Sunroof");
        List<SpecialEquipmentLoadDto> specialEquipmentLoadDtos = Collections.singletonList(specialEquipmentLoadDto);
        when(specialEquipmentService.getAllSpecialEquipmentsLoad()).thenReturn(specialEquipmentLoadDtos);

        mockMvc.perform(get("/load/specialEquipment/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].equipmentName").value("Sunroof"));
    }

    @Test
    void addSpecialEquipment_success() throws Exception {
        SpecialEquipmentLoadDto specialEquipmentLoadDto = new SpecialEquipmentLoadDto();
        specialEquipmentLoadDto.setEquipmentName("Sunroof");

        mockMvc.perform(post("/load/specialEquipment/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(specialEquipmentLoadDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text").value("Special equipment added successfully"))
                .andExpect(jsonPath("$.status").value("SUCCESS"));
    }

    @Test
    void addSpecialEquipment_failure() throws Exception {
        SpecialEquipmentLoadDto specialEquipmentLoadDto = new SpecialEquipmentLoadDto();
        specialEquipmentLoadDto.setEquipmentName("Sunroof");
        doThrow(new ItemAddException("Equipment already exists")).when(specialEquipmentService).addSpecialEquipment(any(SpecialEquipmentLoadDto.class));

        mockMvc.perform(post("/load/specialEquipment/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(specialEquipmentLoadDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text").value("Failed to add special equipment"))
                .andExpect(jsonPath("$.status").value("FAILURE"))
                .andExpect(jsonPath("$.errorMessage").value("Equipment already exists"));
    }

    @Test
    void addAllSpecialEquipment_success() throws Exception {
        SpecialEquipmentLoadDto specialEquipmentLoadDto = new SpecialEquipmentLoadDto();
        specialEquipmentLoadDto.setEquipmentName("Sunroof");
        List<SpecialEquipmentLoadDto> specialEquipmentLoadDtos = Collections.singletonList(specialEquipmentLoadDto);

        mockMvc.perform(post("/load/specialEquipment/add/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(specialEquipmentLoadDtos)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text").value("All special equipment added successfully"))
                .andExpect(jsonPath("$.status").value("SUCCESS"));
    }

    @Test
    void addAllSpecialEquipment_failure() throws Exception {
        SpecialEquipmentLoadDto specialEquipmentLoadDto = new SpecialEquipmentLoadDto();
        specialEquipmentLoadDto.setEquipmentName("Sunroof");
        List<SpecialEquipmentLoadDto> specialEquipmentLoadDtos = Collections.singletonList(specialEquipmentLoadDto);
        doThrow(new ItemAddException("Duplicate equipment")).when(specialEquipmentService).addAllSpecialEquipment(any());

        mockMvc.perform(post("/load/specialEquipment/add/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(specialEquipmentLoadDtos)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text").value("Failed to add one or more special equipment items"))
                .andExpect(jsonPath("$.status").value("FAILURE"))
                .andExpect(jsonPath("$.errorMessage").value("Duplicate equipment"));
    }
}
