package com.applicationdemo.carconfig.domain.order;

import com.applicationdemo.carconfig.domain.base.CarEngine;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarEngineOrderTest {

    @Test
    void testGettersAndSetters() {
        CarEngineOrder carEngineOrder = new CarEngineOrder();

        carEngineOrder.setId(1L);
        carEngineOrder.setCarEngineOrderId("ceo-1");
        carEngineOrder.setCarEngine(new CarEngine());
        carEngineOrder.setOrderStatus(new OrderStatus());

        assertEquals(1L, carEngineOrder.getId());
        assertEquals("ceo-1", carEngineOrder.getCarEngineOrderId());
        assertNotNull(carEngineOrder.getCarEngine());
        assertNotNull(carEngineOrder.getOrderStatus());
    }

    @Test
    void testEqualsAndHashCode() {
        CarEngineOrder order1 = new CarEngineOrder();
        order1.setId(1L);
        order1.setCarEngineOrderId("ceo-1");

        CarEngineOrder order2 = new CarEngineOrder();
        order2.setId(1L);
        order2.setCarEngineOrderId("ceo-1");

        CarEngineOrder order3 = new CarEngineOrder();
        order3.setId(2L);
        order3.setCarEngineOrderId("ceo-2");

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
