package com.applicationdemo.carconfig.controller.web;

import com.applicationdemo.carconfig.dto.web.ProductInfoDetailDto;
import com.applicationdemo.carconfig.dto.web.ProductInfoDto;
import com.applicationdemo.carconfig.security.JwtService;
import com.applicationdemo.carconfig.services.ProductInfoService;

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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(ProductInfoWebController.class)
@AutoConfigureMockMvc(addFilters = false)
class ProductInfoWebControllerTest {

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private UserDetailsService userDetailsService;

    @MockitoBean
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductInfoService productInfoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllProductByProductIds() throws Exception {
        ProductInfoDto productInfoDto = new ProductInfoDto();
        ProductInfoDetailDto productInfoDetailDto = new ProductInfoDetailDto();

        when(productInfoService.getProductDetailsByConfiguration(any(ProductInfoDto.class))).thenReturn(productInfoDetailDto);

        mockMvc.perform(post("/web/productInfo/getInfo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productInfoDto)))
                .andExpect(status().isOk());
    }
}
