package com.applicationdemo.carconfig.controller.base;

import com.applicationdemo.carconfig.dto.ResponseDto;
import com.applicationdemo.carconfig.dto.load.BaseConfigLoadDto;
import com.applicationdemo.carconfig.enums.TransferStatus;
import com.applicationdemo.carconfig.exceptions.ItemAddException;
import com.applicationdemo.carconfig.security.JwtService;
import com.applicationdemo.carconfig.services.base.BaseConfigService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(ConfigBaseExternalController.class)
@AutoConfigureMockMvc(addFilters = false)
class ConfigBaseExternalControllerTest {

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private UserDetailsService userDetailsService;

    @MockitoBean
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BaseConfigService baseConfigService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getBaseConfiguration() throws Exception {
        BaseConfigLoadDto baseConfigLoadDto = new BaseConfigLoadDto();
        when(baseConfigService.getBaseConfiguration()).thenReturn(baseConfigLoadDto);

        mockMvc.perform(get("/load/config"))
                .andExpect(status().isOk());
    }

    @Test
    void addBaseConfiguration_success() throws Exception {
        BaseConfigLoadDto baseConfigLoadDto = new BaseConfigLoadDto();
        ResponseDto responseDto = new ResponseDto("All components added", TransferStatus.SUCCESS);
        when(baseConfigService.addBaseConfiguration(any(BaseConfigLoadDto.class))).thenReturn(responseDto);

        mockMvc.perform(post("/load/config/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(baseConfigLoadDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text").value("All components added"))
                .andExpect(jsonPath("$.status").value("SUCCESS"));
    }

    @Test
    void addBaseConfiguration_itemAddException() throws Exception {
        BaseConfigLoadDto baseConfigLoadDto = new BaseConfigLoadDto();
        when(baseConfigService.addBaseConfiguration(any(BaseConfigLoadDto.class))).thenThrow(new ItemAddException("Duplicate item"));

        mockMvc.perform(post("/load/config/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(baseConfigLoadDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text").value("Failed to add one or more base configuration components"))
                .andExpect(jsonPath("$.status").value("FAILURE"))
                .andExpect(jsonPath("$.errorMessage").value("Duplicate item"));
    }

    @Test
    void addBaseConfiguration_genericException() throws Exception {
        BaseConfigLoadDto baseConfigLoadDto = new BaseConfigLoadDto();
        when(baseConfigService.addBaseConfiguration(any(BaseConfigLoadDto.class))).thenThrow(new RuntimeException("Unexpected error"));

        mockMvc.perform(post("/load/config/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(baseConfigLoadDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text").value("An unexpected error occurred"))
                .andExpect(jsonPath("$.status").value("FAILURE"))
                .andExpect(jsonPath("$.errorMessage").value("Unexpected error"));
    }
}
