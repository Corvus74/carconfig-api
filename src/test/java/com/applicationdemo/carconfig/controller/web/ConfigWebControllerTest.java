package com.applicationdemo.carconfig.controller.web;

import com.applicationdemo.carconfig.dto.web.BaseConfigDto;
import com.applicationdemo.carconfig.security.JwtService;
import com.applicationdemo.carconfig.services.base.BaseConfigService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(ConfigWebController.class)
@AutoConfigureMockMvc(addFilters = false)
class ConfigWebControllerTest {

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

    @Test
    void getBaseConfiguration() throws Exception {
        BaseConfigDto baseConfigDto = new BaseConfigDto();
        when(baseConfigService.getBaseConfigurationWeb()).thenReturn(baseConfigDto);

        mockMvc.perform(get("/web/config/init"))
                .andExpect(status().isOk());
    }
}
