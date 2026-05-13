package com.applicationdemo.carconfig.domain.order;

import com.applicationdemo.carconfig.domain.base.SpecialEquipment;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpecialEquipmentOrderTest {

    @Test
    void testGettersAndSetters() {
        SpecialEquipmentOrder specialEquipmentOrder = new SpecialEquipmentOrder();

        specialEquipmentOrder.setId(1L);
        specialEquipmentOrder.setSpecialEquipmentOrderId("seo-1");
        specialEquipmentOrder.setSpecialEquipment(new SpecialEquipment());
        specialEquipmentOrder.setOrderStatus(new OrderStatus());

        assertEquals(1L, specialEquipmentOrder.getId());
        assertEquals("seo-1", specialEquipmentOrder.getSpecialEquipmentOrderId());
        assertNotNull(specialEquipmentOrder.getSpecialEquipment());
        assertNotNull(specialEquipmentOrder.getOrderStatus());
    }

    @Test
    void testEqualsAndHashCode() {
        SpecialEquipmentOrder order1 = new SpecialEquipmentOrder();
        order1.setId(1L);
        order1.setSpecialEquipmentOrderId("seo-1");

        SpecialEquipmentOrder order2 = new SpecialEquipmentOrder();
        order2.setId(1L);
        order2.setSpecialEquipmentOrderId("seo-1");

        SpecialEquipmentOrder order3 = new SpecialEquipmentOrder();
        order3.setId(2L);
        order3.setSpecialEquipmentOrderId("seo-2");

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
