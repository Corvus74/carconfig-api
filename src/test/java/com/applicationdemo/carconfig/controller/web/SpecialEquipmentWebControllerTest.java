package com.applicationdemo.carconfig.controller.web;

import com.applicationdemo.carconfig.dto.web.SpecialEquipmentDto;
import com.applicationdemo.carconfig.security.JwtService;
import com.applicationdemo.carconfig.services.base.SpecialEquipmentService;
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
@WebMvcTest(SpecialEquipmentWebController.class)
@AutoConfigureMockMvc(addFilters = false)
class SpecialEquipmentWebControllerTest {

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private UserDetailsService userDetailsService;

    @MockitoBean
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SpecialEquipmentService specialEquipmentService;

    @Test
    void getAllSpecialEquipment() throws Exception {
        SpecialEquipmentDto specialEquipmentDto = new SpecialEquipmentDto();
        specialEquipmentDto.setEquipmentName("Sunroof");
        List<SpecialEquipmentDto> specialEquipmentDtos = Collections.singletonList(specialEquipmentDto);
        when(specialEquipmentService.getAllSpecialEquipmentsWeb()).thenReturn(specialEquipmentDtos);

        mockMvc.perform(get("/web/specialEquipment/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].equipmentName").value("Sunroof"));
    }
}
