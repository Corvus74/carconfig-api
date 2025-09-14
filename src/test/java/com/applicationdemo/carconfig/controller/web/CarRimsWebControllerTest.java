package com.applicationdemo.carconfig.controller.web;

import com.applicationdemo.carconfig.dto.web.CarRimDto;
import com.applicationdemo.carconfig.services.base.CarRimService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(CarRimsWebController.class)
class CarRimsWebControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CarRimService carRimService;

    @Test
    void getAllCarRims() throws Exception {
        CarRimDto carRimDto = new CarRimDto();
        carRimDto.setModel("ModelY");
        List<CarRimDto> carRimDtos = Collections.singletonList(carRimDto);
        when(carRimService.getAllCarRimsWeb()).thenReturn(carRimDtos);

        mockMvc.perform(get("/web/car-rims/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].model").value("ModelY"));
    }
}
