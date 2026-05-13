package com.applicationdemo.carconfig.services.order;

import com.applicationdemo.carconfig.domain.user.OrderUser;
import com.applicationdemo.carconfig.domain.base.CarColor;
import com.applicationdemo.carconfig.domain.order.CarColorOrder;
import com.applicationdemo.carconfig.domain.order.OrderStatus;
import com.applicationdemo.carconfig.enums.OrderStatusEnum;
import com.applicationdemo.carconfig.repositories.order.CarColorOrderRepository;
import com.applicationdemo.carconfig.services.base.CarColorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarColorOrderServiceTest {

    @Mock
    private CarColorOrderRepository carColorOrderRepository;
    @Mock
    private CarColorService carColorService;
    @Mock
    private OrderStatusService orderStatusService;

    @InjectMocks
    private CarColorOrderService carColorOrderService;

    @Test
    void createCarColorsOrderByProductIdAndUser() {
        String productId = "P1";
        OrderUser user = new OrderUser();
        user.setUserName("testuser");
        CarColor carColor = new CarColor();
        CarColorOrder savedOrder = new CarColorOrder();

        when(carColorService.getColorByProductId(productId)).thenReturn(carColor);
        when(orderStatusService.createNewOrderStatusWithUser(user)).thenReturn(new OrderStatus());
        when(carColorOrderRepository.save(any(CarColorOrder.class))).thenReturn(savedOrder);

        CarColorOrder result = carColorOrderService.createCarColorsOrderByProductIdAndUser(productId, user);

        assertNotNull(result);
        verify(carColorOrderRepository).save(any(CarColorOrder.class));
    }

    @Test
    void invalidateCarColorIfProductIdDiffers_noChange() {
        CarColor carColor = new CarColor();
        carColor.setProductId("P1");
        CarColorOrder existingOrder = new CarColorOrder();
        existingOrder.setCarColor(carColor);
        var existingOrderStatus = new OrderStatus();
        existingOrderStatus.setCurrentStatus(OrderStatusEnum.PENDING);
        existingOrder.setOrderStatus(existingOrderStatus);

        boolean result = carColorOrderService.invalidateCarColorIfProductIdDiffers(existingOrder, "P1");

        assertFalse(result);
        assertEquals(OrderStatusEnum.PENDING, existingOrder.getOrderStatus().getCurrentStatus());
        verify(carColorOrderRepository, never()).save(any(CarColorOrder.class));
    }

    @Test
    void invalidateCarColorIfProductIdDiffers_change() {
        CarColor carColor = new CarColor();
        carColor.setProductId("P1");
        CarColorOrder existingOrder = new CarColorOrder();
        existingOrder.setCarColor(carColor);
        existingOrder.setOrderStatus(new OrderStatus());

        boolean result = carColorOrderService.invalidateCarColorIfProductIdDiffers(existingOrder, "P2");

        assertTrue(result);
        assertEquals(OrderStatusEnum.CANCELLED, existingOrder.getOrderStatus().getCurrentStatus());
        verify(carColorOrderRepository).save(existingOrder);
    }
}
