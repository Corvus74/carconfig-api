package com.applicationdemo.carconfig.domain.order;

import com.applicationdemo.carconfig.domain.base.CarRim;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarRimOrderTest {

    @Test
    void testGettersAndSetters() {
        CarRimOrder carRimOrder = new CarRimOrder();

        carRimOrder.setId(1L);
        carRimOrder.setCarRimOrderId("cro-1");
        carRimOrder.setCarRim(new CarRim());
        carRimOrder.setOrderStatus(new OrderStatus());
        carRimOrder.setDeleteFlag("N");

        assertEquals(1L, carRimOrder.getId());
        assertEquals("cro-1", carRimOrder.getCarRimOrderId());
        assertNotNull(carRimOrder.getCarRim());
        assertNotNull(carRimOrder.getOrderStatus());
        assertEquals("N", carRimOrder.getDeleteFlag());
    }

    @Test
    void testEqualsAndHashCode() {
        CarRimOrder order1 = new CarRimOrder();
        order1.setId(1L);
        order1.setCarRimOrderId("cro-1");

        CarRimOrder order2 = new CarRimOrder();
        order2.setId(1L);
        order2.setCarRimOrderId("cro-1");

        CarRimOrder order3 = new CarRimOrder();
        order3.setId(2L);
        order3.setCarRimOrderId("cro-2");

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
