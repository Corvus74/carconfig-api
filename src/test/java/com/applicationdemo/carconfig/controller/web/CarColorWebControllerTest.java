package com.applicationdemo.carconfig.controller.web;

import com.applicationdemo.carconfig.dto.web.CarColorDto;
import com.applicationdemo.carconfig.services.base.CarColorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
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
@WebMvcTest(CarColorWebController.class)
class CarColorWebControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CarColorService carColorService;

    @Test
    void getAllCarColors() throws Exception {
        CarColorDto carColorDto = new CarColorDto();
        carColorDto.setColorName("Red");
        List<CarColorDto> carColorDtos = Collections.singletonList(carColorDto);
        when(carColorService.getAllCarColorsWeb()).thenReturn(carColorDtos);

        mockMvc.perform(get("/web/car-color/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].colorName").value("Red"));
    }
}
