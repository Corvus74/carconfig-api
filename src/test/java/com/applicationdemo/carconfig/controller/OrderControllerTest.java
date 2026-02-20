package com.applicationdemo.carconfig.controller;

import com.applicationdemo.carconfig.dto.order.CarOrderDto;
import com.applicationdemo.carconfig.dto.order.CarOrderUpdateDto;
import com.applicationdemo.carconfig.exceptions.OrderException;
import com.applicationdemo.carconfig.services.order.CarOrderService;

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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CarOrderService carOrderService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createOrder_success() throws Exception {
        CarOrderUpdateDto carOrderUpdateDto = new CarOrderUpdateDto();
        carOrderUpdateDto.setUserMail("test@test.com");
        when(carOrderService.createOrderByIds(any(CarOrderUpdateDto.class))).thenReturn("12345");

        mockMvc.perform(post("/order/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(carOrderUpdateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text").value("CarOrder created successfully!"))
                .andExpect(jsonPath("$.orderId").value("12345"))
                .andExpect(jsonPath("$.status").value("SUCCESS"));
    }

    @Test
    void createOrder_failure() throws Exception {
        CarOrderUpdateDto carOrderUpdateDto = new CarOrderUpdateDto();
        carOrderUpdateDto.setUserMail("test@test.com");
        when(carOrderService.createOrderByIds(any(CarOrderUpdateDto.class))).thenThrow(new OrderException("Order creation failed"));

        mockMvc.perform(post("/order/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(carOrderUpdateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text").value("Failed to create order"))
                .andExpect(jsonPath("$.status").value("CANCELED"))
                .andExpect(jsonPath("$.errorMessage").value("Order creation failed"));
    }

    @Test
    void getOrderByOrderId_found() throws Exception {
        CarOrderDto carOrderDto = new CarOrderDto();
        carOrderDto.setCarOrderId("12345");
        when(carOrderService.getOrderById("12345")).thenReturn(Optional.of(carOrderDto));

        mockMvc.perform(get("/order/byId/{orderId}", "12345"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.carOrderId").value("12345"));
    }

    @Test
    void getOrderByOrderId_notFound() throws Exception {
        when(carOrderService.getOrderById("12345")).thenReturn(Optional.empty());

        mockMvc.perform(get("/order/byId/{orderId}", "12345"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.carOrderId").doesNotExist());
    }

    @Test
    void updateOrder_success() throws Exception {
        CarOrderUpdateDto carOrderUpdateDto = new CarOrderUpdateDto();
        carOrderUpdateDto.setCarOrderId("12345");
        when(carOrderService.updateOrder(any(CarOrderUpdateDto.class))).thenReturn("12345");

        mockMvc.perform(put("/order/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(carOrderUpdateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text").value("CarOrder updated successfully!"))
                .andExpect(jsonPath("$.orderId").value("12345"))
                .andExpect(jsonPath("$.status").value("SUCCESS"));
    }

    @Test
    void updateOrder_failure() throws Exception {
        CarOrderUpdateDto carOrderUpdateDto = new CarOrderUpdateDto();
        carOrderUpdateDto.setCarOrderId("12345");
        when(carOrderService.updateOrder(any(CarOrderUpdateDto.class))).thenThrow(new RuntimeException("Update failed"));

        mockMvc.perform(put("/order/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(carOrderUpdateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text").value("Failed to update order"))
                .andExpect(jsonPath("$.status").value("CANCELED"))
                .andExpect(jsonPath("$.errorMessage").value("Update failed"));
    }

    @Test
    void deleteOrder_success() throws Exception {
        mockMvc.perform(delete("/order/delete/{orderId}", "12345"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text").value("CarOrder deleted successfully!"))
                .andExpect(jsonPath("$.status").value("SUCCESS"));
    }

    @Test
    void deleteOrder_failure() throws Exception {
        doThrow(new RuntimeException("Delete failed")).when(carOrderService).deleteOrder("12345");

        mockMvc.perform(delete("/order/delete/{orderId}", "12345"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text").value("Failed to delete order"))
                .andExpect(jsonPath("$.status").value("CANCELED"))
                .andExpect(jsonPath("$.errorMessage").value("Delete failed"));
    }
}
