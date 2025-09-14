package com.applicationdemo.carconfig.controller.web;

import com.applicationdemo.carconfig.dto.web.ProductInfoDetailDto;
import com.applicationdemo.carconfig.dto.web.ProductInfoDto;
import com.applicationdemo.carconfig.services.ProductInfoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(ProductInfoWebController.class)
class ProductInfoWebControllerTest {

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
