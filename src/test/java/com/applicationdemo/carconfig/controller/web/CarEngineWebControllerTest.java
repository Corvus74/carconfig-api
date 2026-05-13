package com.applicationdemo.carconfig.controller.web;

import com.applicationdemo.carconfig.dto.web.CarEngineDto;
import com.applicationdemo.carconfig.security.JwtService;
import com.applicationdemo.carconfig.services.base.CarEngineService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
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
@WebMvcTest(CarEngineWebController.class)
@AutoConfigureMockMvc(addFilters = false)
class CarEngineWebControllerTest {

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private UserDetailsService userDetailsService;

    @MockitoBean
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CarEngineService carEngineService;

    @Test
    void getAllCarEngines() throws Exception {
        CarEngineDto carEngineDto = new CarEngineDto();
        carEngineDto.setModel("V8");
        List<CarEngineDto> carEngineDtos = Collections.singletonList(carEngineDto);
        when(carEngineService.getAllCarEnginesWeb()).thenReturn(carEngineDtos);

        mockMvc.perform(get("/web/car-engine/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].model").value("V8"));
    }
}
