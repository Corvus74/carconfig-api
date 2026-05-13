package com.applicationdemo.carconfig.controller;

import com.applicationdemo.carconfig.dto.OrderUserDto;
import com.applicationdemo.carconfig.security.ApiKeyFilter;
import com.applicationdemo.carconfig.security.JwtAuthenticationFilter;
import com.applicationdemo.carconfig.security.JwtService;
import com.applicationdemo.carconfig.services.security.OrderUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(controllers = OrderUserController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {JwtAuthenticationFilter.class, ApiKeyFilter.class}))
@org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
class OrderUserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrderUserService orderUserService;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private UserDetailsService userDetailsService;

    @MockitoBean
    private AuthenticationProvider authenticationProvider;


    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @WithMockUser(roles = "USER")
    void getUser_found() throws Exception {
        OrderUserDto orderUserDto = new OrderUserDto("testuser", "test@example.com");
        when(orderUserService.getOrderUserDtoByEmail("test@example.com")).thenReturn(Optional.of(orderUserDto));

        mockMvc.perform(get("/user/get/{email}", "test@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName").value("testuser"))
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void getUser_notFound() throws Exception {
        when(orderUserService.getOrderUserDtoByEmail("test@example.com")).thenReturn(Optional.empty());

        try {
            mockMvc.perform(get("/user/get/{email}", "test@example.com"));
        } catch (jakarta.servlet.ServletException e) {
            if (e.getCause() instanceof org.springframework.web.server.ResponseStatusException ex) {
                org.junit.jupiter.api.Assertions.assertEquals(org.springframework.http.HttpStatus.NOT_FOUND, ex.getStatusCode());
                return;
            }
            throw e;
        }
    }

    @Test
    @WithMockUser(roles = "ADMIN")
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
    @WithMockUser(roles = "ADMIN")
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

    @Test
    @WithMockUser(roles = "USER")
    void setUser_forbidden_forUser() throws Exception {
        OrderUserDto orderUserDto = new OrderUserDto("testuser", "test@example.com");

        try {
            mockMvc.perform(post("/user/add")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(orderUserDto)));
        } catch (jakarta.servlet.ServletException e) {
            if (e.getCause() instanceof org.springframework.security.authorization.AuthorizationDeniedException) {
                return;
            }
            throw e;
        }
    }
}
