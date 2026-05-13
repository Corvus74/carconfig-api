package com.applicationdemo.carconfig.domain.order;

import com.applicationdemo.carconfig.domain.base.CarColor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarColorOrderTest {

    @Test
    void testGettersAndSetters() {
        CarColorOrder carColorOrder = new CarColorOrder();

        carColorOrder.setId(1L);
        carColorOrder.setCarColorOrderId("cco-1");
        carColorOrder.setCarColor(new CarColor());
        carColorOrder.setOrderStatus(new OrderStatus());

        assertEquals(1L, carColorOrder.getId());
        assertEquals("cco-1", carColorOrder.getCarColorOrderId());
        assertNotNull(carColorOrder.getCarColor());
        assertNotNull(carColorOrder.getOrderStatus());
    }

    @Test
    void testEqualsAndHashCode() {
        CarColorOrder order1 = new CarColorOrder();
        order1.setId(1L);
        order1.setCarColorOrderId("cco-1");

        CarColorOrder order2 = new CarColorOrder();
        order2.setId(1L);
        order2.setCarColorOrderId("cco-1");

        CarColorOrder order3 = new CarColorOrder();
        order3.setId(2L);
        order3.setCarColorOrderId("cco-2");

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
