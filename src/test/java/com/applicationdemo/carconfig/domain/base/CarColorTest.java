package com.applicationdemo.carconfig.domain.base;

import com.applicationdemo.carconfig.enums.MaterialType;
import com.applicationdemo.carconfig.enums.PaintingType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarColorTest {

    @Test
    void testGettersAndSetters() {
        CarColor carColor = new CarColor();

        carColor.setId(1L);
        carColor.setOrderNumber("C001");
        carColor.setColorName("Alpine White");
        carColor.setDescription("Standard solid white");
        carColor.setProductId("P-C01");
        carColor.setMaterialType(MaterialType.GLOSSY);
        carColor.setPaintingType(PaintingType.SPECIAL);
        carColor.setColorCodeHex("#FFFFFF");
        carColor.setPrice(500);

        assertEquals(1L, carColor.getId());
        assertEquals("C001", carColor.getOrderNumber());
        assertEquals("Alpine White", carColor.getColorName());
        assertEquals("Standard solid white", carColor.getDescription());
        assertEquals("P-C01", carColor.getProductId());
        assertEquals(MaterialType.GLOSSY, carColor.getMaterialType());
        assertEquals(PaintingType.SPECIAL, carColor.getPaintingType());
        assertEquals("#FFFFFF", carColor.getColorCodeHex());
        assertEquals(500, carColor.getPrice());
    }

    @Test
    void testEqualsAndHashCode() {
        CarColor color1 = new CarColor();
        color1.setId(1L);
        color1.setProductId("P-C01");

        CarColor color2 = new CarColor();
        color2.setId(1L);
        color2.setProductId("P-C01");

        CarColor color3 = new CarColor();
        color3.setId(2L);
        color3.setProductId("P-C02");

        // Test for equality
        assertEquals(color1, color2);
        assertNotEquals(color1, color3);

        // Test for hash code consistency
        assertEquals(color1.hashCode(), color2.hashCode());
        assertNotEquals(color1.hashCode(), color3.hashCode());

        // Test with null
        assertNotEquals(null, color1);
    }
}
