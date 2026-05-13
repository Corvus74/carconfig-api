package com.applicationdemo.carconfig.services.order;

import com.applicationdemo.carconfig.domain.user.OrderUser;
import com.applicationdemo.carconfig.domain.base.SpecialEquipment;
import com.applicationdemo.carconfig.domain.order.OrderStatus;
import com.applicationdemo.carconfig.domain.order.SpecialEquipmentOrder;
import com.applicationdemo.carconfig.enums.OrderStatusEnum;
import com.applicationdemo.carconfig.repositories.order.SpecialEquipmentOrderRepository;
import com.applicationdemo.carconfig.services.base.SpecialEquipmentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SpecialEquipmentOrderServiceTest {

    @Mock
    private SpecialEquipmentService specialEquipmentService;
    @Mock
    private OrderStatusService orderStatusService;
    @Mock
    private SpecialEquipmentOrderRepository specialEquipmentOrderRepository;

    @InjectMocks
    private SpecialEquipmentOrderService specialEquipmentOrderService;

    @Test
    void createSpecialEquipmentsOrdersByProductIdsAndUser() {
        String productId = "P1";
        OrderUser user = new OrderUser();
        SpecialEquipment equipment = new SpecialEquipment();
        SpecialEquipmentOrder savedOrder = new SpecialEquipmentOrder();

        when(specialEquipmentService.getSpecialEquipmentByProductId(productId)).thenReturn(equipment);
        when(orderStatusService.createNewOrderStatusWithUser(user)).thenReturn(new OrderStatus());
        when(specialEquipmentOrderRepository.save(any(SpecialEquipmentOrder.class))).thenReturn(savedOrder);

        List<SpecialEquipmentOrder> result = specialEquipmentOrderService.createSpecialEquipmentsOrdersByProductIdsAndUser(List.of(productId), user);

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(specialEquipmentOrderRepository).save(any(SpecialEquipmentOrder.class));
    }

    @Test
    void invalidateSpecialEquipmentIfProductIdDiffers_noChange() {
        SpecialEquipment equipment = new SpecialEquipment();
        equipment.setProductId("P1");
        SpecialEquipmentOrder existingOrder = new SpecialEquipmentOrder();
        existingOrder.setSpecialEquipment(equipment);

        boolean result = specialEquipmentOrderService.invalidateSpecialEquipmentIfProductIdDiffers(List.of(existingOrder), List.of("P1"));

        assertFalse(result);
        verify(specialEquipmentOrderRepository, never()).save(any());
    }

    @Test
    void invalidateSpecialEquipmentIfProductIdDiffers_idRemoved() {
        SpecialEquipment equipment1 = new SpecialEquipment();
        equipment1.setProductId("P1");
        SpecialEquipmentOrder existingOrder1 = new SpecialEquipmentOrder();
        existingOrder1.setSpecialEquipment(equipment1);
        existingOrder1.setOrderStatus(new OrderStatus());

        SpecialEquipment equipment2 = new SpecialEquipment();
        equipment2.setProductId("P2");
        SpecialEquipmentOrder existingOrder2 = new SpecialEquipmentOrder();
        existingOrder2.setSpecialEquipment(equipment2);
        existingOrder2.setOrderStatus(new OrderStatus());

        boolean result = specialEquipmentOrderService.invalidateSpecialEquipmentIfProductIdDiffers(List.of(existingOrder1, existingOrder2), List.of("P1"));

        assertTrue(result);
        verify(specialEquipmentOrderRepository, times(1)).save(existingOrder2);
    }

    @Test
    void updateSpecialEquipmentsOrdersByProductIdsAndUser() {
        // This test covers the logic as written, even if it seems counter-intuitive.
        // It creates new orders for items that are in the old list but not the new one.
        SpecialEquipment equipment1 = new SpecialEquipment();
        equipment1.setProductId("P1");
        SpecialEquipmentOrder existingOrder1 = new SpecialEquipmentOrder();
        existingOrder1.setSpecialEquipment(equipment1);

        List<SpecialEquipmentOrder> existingOrders = new ArrayList<>();
        existingOrders.add(existingOrder1);

        List<String> newProductIds = List.of("P2"); // New list does not contain P1
        OrderUser user = new OrderUser();

        // Mocks for creating a new order for P1
        when(specialEquipmentService.getSpecialEquipmentByProductId("P1")).thenReturn(equipment1);
        when(orderStatusService.createNewOrderStatusWithUser(user)).thenReturn(new OrderStatus());
        when(specialEquipmentOrderRepository.save(any(SpecialEquipmentOrder.class))).thenReturn(new SpecialEquipmentOrder());

        List<SpecialEquipmentOrder> result = specialEquipmentOrderService.updateSpecialEquipmentsOrdersByProductIdsAndUser(existingOrders, newProductIds, user);

        assertEquals(1, result.size());
        verify(specialEquipmentService).getSpecialEquipmentByProductId("P1");
        verify(specialEquipmentOrderRepository).save(any(SpecialEquipmentOrder.class));
    }
}
