package com.applicationdemo.carconfig.services.order;

import com.applicationdemo.carconfig.domain.order.*;
import com.applicationdemo.carconfig.dto.order.CarOrderDto;
import com.applicationdemo.carconfig.dto.order.CarOrderUpdateDto;
import com.applicationdemo.carconfig.domain.OrderUser;
import com.applicationdemo.carconfig.exceptions.OrderException;
import com.applicationdemo.carconfig.mapper.order.CarOrderMapper;
import com.applicationdemo.carconfig.repositories.order.OrderRepository;
import com.applicationdemo.carconfig.services.OrderUserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CarOrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderUserService orderUserService;

    @Mock
    private CarOrderMapper orderMapper;

    @Mock
    private CarColorOrderService carColorOrderService;

    @Mock
    private CarEngineOrderService carEngineOrderService;

    @Mock
    private CarRimOrderService carRimOrderService;

    @Mock
    private SpecialEquipmentOrderService specialEquipmentOrderService;

    @Mock
    private OrderStatusService orderStatusService;

    @InjectMocks
    private CarOrderService carOrderService;

    @Test
    void createOrderByIds_success() {
        CarOrderUpdateDto dto = new CarOrderUpdateDto();
        dto.setUserMail("test@test.com");
        String newOrderId = UUID.randomUUID().toString();

        when(orderUserService.getUserIfExistsIfNotCreateUnknownUser(anyString())).thenReturn(new OrderUser());
        when(orderStatusService.createNewOrderStatusWithUser(any(OrderUser.class))).thenReturn(new OrderStatus());
        when(carColorOrderService.createCarColorsOrderByProductIdAndUser(any(), any())).thenReturn(new CarColorOrder());
        when(carEngineOrderService.createCarEngineOrderByProductIdAndUser(any(), any())).thenReturn(new CarEngineOrder());
        when(carRimOrderService.createCarRimOrderProductIdAndOrderUser(any(), any())).thenReturn(new CarRimOrder());
        when(specialEquipmentOrderService.createSpecialEquipmentsOrdersByProductIdsAndUser(any(), any())).thenReturn(Collections.emptyList());

        CarOrder savedOrder = new CarOrder();
        savedOrder.setCarOrderId(newOrderId);
        when(orderRepository.save(any(CarOrder.class))).thenReturn(savedOrder);

        String result = carOrderService.createOrderByIds(dto);

        assertEquals(newOrderId, result);
    }

    @Test
    void getOrderById_found() {
        String orderId = "123";
        CarOrder order = new CarOrder();
        CarOrderDto dto = new CarOrderDto();

        when(orderRepository.findByCarOrderId(orderId)).thenReturn(Optional.of(order));
        when(orderMapper.toDto(order)).thenReturn(dto);

        Optional<CarOrderDto> result = carOrderService.getOrderById(orderId);

        assertTrue(result.isPresent());
        assertEquals(dto, result.get());
    }

    @Test
    void getOrderById_notFound() {
        String orderId = "123";
        when(orderRepository.findByCarOrderId(orderId)).thenReturn(Optional.empty());

        Optional<CarOrderDto> result = carOrderService.getOrderById(orderId);

        assertFalse(result.isPresent());
    }

    @Test
    void updateOrder_notFound() {
        CarOrderUpdateDto dto = new CarOrderUpdateDto();
        dto.setCarOrderId("123");
        when(orderRepository.findByCarOrderId("123")).thenReturn(Optional.empty());

        assertThrows(OrderException.class, () -> carOrderService.updateOrder(dto));
    }


    @Test
    void deleteOrder_success() {
        String orderId = "123";
        CarOrder order = new CarOrder();
        when(orderRepository.findByCarOrderId(orderId)).thenReturn(Optional.of(order));

        carOrderService.deleteOrder(orderId);

        verify(orderRepository).delete(order);
    }

    @Test
    void deleteOrder_notFound() {
        String orderId = "123";
        when(orderRepository.findByCarOrderId(orderId)).thenReturn(Optional.empty());

        assertThrows(OrderException.class, () -> carOrderService.deleteOrder(orderId));
    }
}
