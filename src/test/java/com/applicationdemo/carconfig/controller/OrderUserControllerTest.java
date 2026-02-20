package com.applicationdemo.carconfig.controller;

import com.applicationdemo.carconfig.dto.OrderUserDto;
import com.applicationdemo.carconfig.services.OrderUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(OrderUserController.class)
class OrderUserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrderUserService orderUserService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getUser_found() throws Exception {
        OrderUserDto orderUserDto = new OrderUserDto("testuser", "test@example.com");
        when(orderUserService.getOrderUserDtoByEmail("test@example.com")).thenReturn(Optional.of(orderUserDto));

        mockMvc.perform(get("/user/get/{email}", "test@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName").value("testuser"))
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }

    @Test
    void getUser_notFound() throws Exception {
        when(orderUserService.getOrderUserDtoByEmail("test@example.com")).thenReturn(Optional.empty());

        mockMvc.perform(get("/user/get/{email}", "test@example.com"))
                .andExpect(status().isNotFound());
    }

    @Test
    void setUser_success() throws Exception {
        OrderUserDto orderUserDto = new OrderUserDto("testuser", "test@example.com");

        mockMvc.perform(post("/user/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderUserDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text").value("User saved successfully"))
                .andExpect(jsonPath("$.status").value("SUCCESS"));
    }

    @Test
    void setUser_failure() throws Exception {
        OrderUserDto orderUserDto = new OrderUserDto("testuser", "test@example.com");
        doThrow(new RuntimeException("Save failed")).when(orderUserService).addUser(any(OrderUserDto.class));

        mockMvc.perform(post("/user/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderUserDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text").value("Failed to save user"))
                .andExpect(jsonPath("$.status").value("FAILURE"))
                .andExpect(jsonPath("$.errorMessage").value("Save failed"));
    }
}
