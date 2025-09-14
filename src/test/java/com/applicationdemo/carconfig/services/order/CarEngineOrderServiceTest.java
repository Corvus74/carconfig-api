package com.applicationdemo.carconfig.services.order;

import com.applicationdemo.carconfig.entities.OrderUser;
import com.applicationdemo.carconfig.entities.base.CarEngine;
import com.applicationdemo.carconfig.entities.order.CarEngineOrder;
import com.applicationdemo.carconfig.entities.order.OrderStatus;
import com.applicationdemo.carconfig.repository.order.CarEngineOrderRepository;
import com.applicationdemo.carconfig.services.base.CarEngineService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarEngineOrderServiceTest {

    @Mock
    private CarEngineOrderRepository carEngineOrderRepository;
    @Mock
    private CarEngineService carEngineService;
    @Mock
    private OrderStatusService orderStatusService;

    @InjectMocks
    private CarEngineOrderService carEngineOrderService;

    @Test
    void createCarEngineOrderByProductIdAndUser() {
        String productId = "P1";
        OrderUser user = new OrderUser();
        user.setUserName("testuser");
        CarEngine carEngine = new CarEngine();
        CarEngineOrder savedOrder = new CarEngineOrder();

        when(carEngineService.getCarEngineByProductId(productId)).thenReturn(carEngine);
        when(orderStatusService.createNewOrderStatusWithUser(user)).thenReturn(new OrderStatus());
        when(carEngineOrderRepository.save(any(CarEngineOrder.class))).thenReturn(savedOrder);

        CarEngineOrder result = carEngineOrderService.createCarEngineOrderByProductIdAndUser(productId, user);

        assertNotNull(result);
        verify(carEngineOrderRepository).save(any(CarEngineOrder.class));
    }

    @Test
    void invalidateCarEngineIfProductIdDiffers_noChange() {
        CarEngine carEngine = new CarEngine();
        carEngine.setProductId("P1");
        CarEngineOrder existingOrder = new CarEngineOrder();
        existingOrder.setCarEngine(carEngine);

        boolean result = carEngineOrderService.invalidateCarEngineIfProductIdDiffers(existingOrder, "P1");

        assertFalse(result);
        assertNull(existingOrder.getDeleteFlag());
        verify(carEngineOrderRepository, never()).save(any(CarEngineOrder.class));
    }

    @Test
    void invalidateCarEngineIfProductIdDiffers_change() {
        CarEngine carEngine = new CarEngine();
        carEngine.setProductId("P1");
        CarEngineOrder existingOrder = new CarEngineOrder();
        existingOrder.setCarEngine(carEngine);

        boolean result = carEngineOrderService.invalidateCarEngineIfProductIdDiffers(existingOrder, "P2");

        assertTrue(result);
        assertEquals("Y", existingOrder.getDeleteFlag());
        verify(carEngineOrderRepository).save(existingOrder);
    }
}
