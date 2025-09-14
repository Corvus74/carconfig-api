package com.applicationdemo.carconfig.entities.order;

import com.applicationdemo.carconfig.entities.OrderUser;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class CarOrderTest {

    @Test
    void testGettersAndSetters() {
        CarOrder carOrder = new CarOrder();

        carOrder.setId(1L);
        carOrder.setCarOrderId("order-1");
        carOrder.setOrderUser(new OrderUser());
        carOrder.setCarEngineOrder(new CarEngineOrder());
        carOrder.setCarRimOrder(new CarRimOrder());
        carOrder.setCarColorOrder(new CarColorOrder());
        carOrder.setOrderStatus(new OrderStatus());
        carOrder.setDescription("A standard order");
        carOrder.setTotalPrice(50000);
        carOrder.setSpecialEquipmentOrders(Collections.emptyList());
        carOrder.setDeleteFlag("N");

        assertEquals(1L, carOrder.getId());
        assertEquals("order-1", carOrder.getCarOrderId());
        assertNotNull(carOrder.getOrderUser());
        assertNotNull(carOrder.getCarEngineOrder());
        assertNotNull(carOrder.getCarRimOrder());
        assertNotNull(carOrder.getCarColorOrder());
        assertNotNull(carOrder.getOrderStatus());
        assertEquals("A standard order", carOrder.getDescription());
        assertEquals(50000, carOrder.getTotalPrice());
        assertTrue(carOrder.getSpecialEquipmentOrders().isEmpty());
        assertEquals("N", carOrder.getDeleteFlag());
    }

    @Test
    void testEqualsAndHashCode() {
        CarOrder order1 = new CarOrder();
        order1.setId(1L);
        order1.setCarOrderId("order-1");

        CarOrder order2 = new CarOrder();
        order2.setId(1L);
        order2.setCarOrderId("order-1");

        CarOrder order3 = new CarOrder();
        order3.setId(2L);
        order3.setCarOrderId("order-2");

        // Test for equality
        assertEquals(order1, order2);
        assertNotEquals(order1, order3);

        // Test for hash code consistency
        assertEquals(order1.hashCode(), order2.hashCode());
        assertNotEquals(order1.hashCode(), order3.hashCode());

        // Test with null
        assertNotEquals(null, order1);
    }
}
