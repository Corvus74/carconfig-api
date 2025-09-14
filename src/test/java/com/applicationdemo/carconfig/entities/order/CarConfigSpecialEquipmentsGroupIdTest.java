package com.applicationdemo.carconfig.entities.order;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarConfigSpecialEquipmentsGroupIdTest {

    @Test
    void testNoArgsConstructor() {
        CarConfigSpecialEquipmentsGroupId id = new CarConfigSpecialEquipmentsGroupId();
        assertNull(id.getCarOrderId());
        assertNull(id.getSpecialEquipmentOrderId());
    }

    @Test
    void testAllArgsConstructor() {
        CarConfigSpecialEquipmentsGroupId id = new CarConfigSpecialEquipmentsGroupId(1L, 2L);
        assertEquals(1L, id.getCarOrderId());
        assertEquals(2L, id.getSpecialEquipmentOrderId());
    }

    @Test
    void testGettersAndSetters() {
        CarConfigSpecialEquipmentsGroupId id = new CarConfigSpecialEquipmentsGroupId();
        id.setCarOrderId(1L);
        id.setSpecialEquipmentOrderId(2L);

        assertEquals(1L, id.getCarOrderId());
        assertEquals(2L, id.getSpecialEquipmentOrderId());
    }

    @Test
    void testEqualsAndHashCode() {
        CarConfigSpecialEquipmentsGroupId id1 = new CarConfigSpecialEquipmentsGroupId(1L, 2L);
        CarConfigSpecialEquipmentsGroupId id2 = new CarConfigSpecialEquipmentsGroupId(1L, 2L);
        CarConfigSpecialEquipmentsGroupId id3 = new CarConfigSpecialEquipmentsGroupId(3L, 4L);

        // Test for equality
        assertEquals(id1, id2);
        assertNotEquals(id1, id3);

        // Test for hash code consistency
        assertEquals(id1.hashCode(), id2.hashCode());
        assertNotEquals(id1.hashCode(), id3.hashCode());

        // Test with null
        assertNotEquals(null, id1);
    }
}
