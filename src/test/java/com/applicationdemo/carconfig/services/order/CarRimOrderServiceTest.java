package com.applicationdemo.carconfig.services.order;

import com.applicationdemo.carconfig.domain.OrderUser;
import com.applicationdemo.carconfig.domain.base.CarRim;
import com.applicationdemo.carconfig.domain.order.CarRimOrder;
import com.applicationdemo.carconfig.domain.order.OrderStatus;
import com.applicationdemo.carconfig.repositories.order.CarRimOrderRepository;
import com.applicationdemo.carconfig.services.base.CarRimService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarRimOrderServiceTest {

    @Mock
    private CarRimOrderRepository carRimOrderRepository;
    @Mock
    private CarRimService carRimService;
    @Mock
    private OrderStatusService orderStatusService;

    @InjectMocks
    private CarRimOrderService carRimOrderService;

    @Test
    void createCarRimOrderProductIdAndOrderUser() {
        String productId = "P1";
        OrderUser user = new OrderUser();
        user.setUserName("testuser");
        CarRim carRim = new CarRim();
        CarRimOrder savedOrder = new CarRimOrder();

        when(carRimService.getCarRimByProductId(productId)).thenReturn(carRim);
        when(orderStatusService.createNewOrderStatusWithUser(user)).thenReturn(new OrderStatus());
        when(carRimOrderRepository.save(any(CarRimOrder.class))).thenReturn(savedOrder);

        CarRimOrder result = carRimOrderService.createCarRimOrderProductIdAndOrderUser(productId, user);

        assertNotNull(result);
        verify(carRimOrderRepository).save(any(CarRimOrder.class));
    }

    @Test
    void invalidateCarRimIfProductIdDiffers_noChange() {
        CarRim carRim = new CarRim();
        carRim.setProductId("P1");
        CarRimOrder existingOrder = new CarRimOrder();
        existingOrder.setCarRim(carRim);

        boolean result = carRimOrderService.invalidateCarRimIfProductIdDiffers(existingOrder, "P1");

        assertFalse(result);
        assertNull(existingOrder.getDeleteFlag());
        verify(carRimOrderRepository, never()).save(any(CarRimOrder.class));
    }

    @Test
    void invalidateCarRimIfProductIdDiffers_change() {
        CarRim carRim = new CarRim();
        carRim.setProductId("P1");
        CarRimOrder existingOrder = new CarRimOrder();
        existingOrder.setCarRim(carRim);

        boolean result = carRimOrderService.invalidateCarRimIfProductIdDiffers(existingOrder, "P2");

        assertTrue(result);
        assertEquals("Y", existingOrder.getDeleteFlag());
        verify(carRimOrderRepository).save(existingOrder);
    }
}
