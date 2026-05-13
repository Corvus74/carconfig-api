package com.applicationdemo.carconfig.domain.base;

import com.applicationdemo.carconfig.enums.FuelType;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CarEngineTest {

    @Test
    void testGettersAndSetters() {
        CarEngine carEngine = new CarEngine();

        carEngine.setId(1L);
        carEngine.setOrderNumber("E001");
        carEngine.setDescription("2.0L 4-cylinder petrol engine");
        carEngine.setFuelType(FuelType.GASOLINE);
        carEngine.setEngineType("I4");
        carEngine.setProductId("P-E01");
        carEngine.setModel("B48");
        carEngine.setPrice(8000);
        carEngine.setCarName("330i");
        carEngine.setDisplacementL(new BigDecimal("2.0"));
        carEngine.setCylinders(4);
        carEngine.setHorsepowerKw(new BigDecimal("190"));
        carEngine.setTorqueNm(new BigDecimal("400"));
        carEngine.setDrivetrain("RWD");
        carEngine.setCo2(new BigDecimal("140"));

        assertEquals(1L, carEngine.getId());
        assertEquals("E001", carEngine.getOrderNumber());
        assertEquals("2.0L 4-cylinder petrol engine", carEngine.getDescription());
        assertEquals(FuelType.GASOLINE, carEngine.getFuelType());
        assertEquals("I4", carEngine.getEngineType());
        assertEquals("P-E01", carEngine.getProductId());
        assertEquals("B48", carEngine.getModel());
        assertEquals(8000, carEngine.getPrice());
        assertEquals("330i", carEngine.getCarName());
        assertEquals(new BigDecimal("2.0"), carEngine.getDisplacementL());
        assertEquals(4, carEngine.getCylinders());
        assertEquals(new BigDecimal("190"), carEngine.getHorsepowerKw());
        assertEquals(new BigDecimal("400"), carEngine.getTorqueNm());
        assertEquals("RWD", carEngine.getDrivetrain());
        assertEquals(new BigDecimal("140"), carEngine.getCo2());
    }

    @Test
    void testEqualsAndHashCode() {
        CarEngine engine1 = new CarEngine();
        engine1.setId(1L);
        engine1.setProductId("P-E01");

        CarEngine engine2 = new CarEngine();
        engine2.setId(1L);
        engine2.setProductId("P-E01");

        CarEngine engine3 = new CarEngine();
        engine3.setId(2L);
        engine3.setProductId("P-E02");

        // Test for equality
        assertEquals(engine1, engine2);
        assertNotEquals(engine1, engine3);

        // Test for hash code consistency
        assertEquals(engine1.hashCode(), engine2.hashCode());
        assertNotEquals(engine1.hashCode(), engine3.hashCode());

        // Test with null
        assertNotEquals(null, engine1);
    }
}
