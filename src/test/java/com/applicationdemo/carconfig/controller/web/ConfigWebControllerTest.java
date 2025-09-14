package com.applicationdemo.carconfig.controller.web;

import com.applicationdemo.carconfig.dto.web.BaseConfigDto;
import com.applicationdemo.carconfig.services.base.BaseConfigService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(ConfigWebController.class)
class ConfigWebControllerTest {

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
