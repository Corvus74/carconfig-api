package com.applicationdemo.carconfig.entities.order;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarConfigSpecialEquipmentsGroupTest {

    @Test
    void testGettersAndSetters() {
        CarConfigSpecialEquipmentsGroup group = new CarConfigSpecialEquipmentsGroup();
        CarConfigSpecialEquipmentsGroupId id = new CarConfigSpecialEquipmentsGroupId(1L, 2L);
        CarOrder carOrder = new CarOrder();
        SpecialEquipmentOrder specialEquipmentOrder = new SpecialEquipmentOrder();

        group.setGroupId(id);
        group.setCarOrder(carOrder);
        group.setSpecialEquipmentOrderId(specialEquipmentOrder);

        assertSame(id, group.getGroupId());
        assertSame(carOrder, group.getCarOrder());
        assertSame(specialEquipmentOrder, group.getSpecialEquipmentOrderId());
    }

    @Test
    void testEqualsAndHashCode() {
        CarConfigSpecialEquipmentsGroupId id1 = new CarConfigSpecialEquipmentsGroupId(1L, 2L);
        CarConfigSpecialEquipmentsGroupId id2 = new CarConfigSpecialEquipmentsGroupId(3L, 4L);

        CarConfigSpecialEquipmentsGroup group1 = new CarConfigSpecialEquipmentsGroup();
        group1.setGroupId(id1);

        CarConfigSpecialEquipmentsGroup group2 = new CarConfigSpecialEquipmentsGroup();
        group2.setGroupId(id1);

        CarConfigSpecialEquipmentsGroup group3 = new CarConfigSpecialEquipmentsGroup();
        group3.setGroupId(id2);

        // Test for equality
        assertEquals(group1, group2);
        assertNotEquals(group1, group3);

        // Test for hash code consistency
        assertEquals(group1.hashCode(), group2.hashCode());
        assertNotEquals(group1.hashCode(), group3.hashCode());

        // Test with null
        assertNotEquals(null, group1);
    }
}
