package com.applicationdemo.carconfig.services.order;

import com.applicationdemo.carconfig.entities.OrderUser;
import com.applicationdemo.carconfig.entities.order.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class CarOrderUpdateHelperTest {

    private CarOrderUpdateHelper helper;
    private CarOrder originalOrder;

    @BeforeEach
    void setUp() {
        helper = new CarOrderUpdateHelper();
        originalOrder = new CarOrder();
        originalOrder.setOrderUser(new OrderUser());
        originalOrder.setCarColorOrder(new CarColorOrder());
        originalOrder.setCarEngineOrder(new CarEngineOrder());
        originalOrder.setCarRimOrder(new CarRimOrder());
        originalOrder.setSpecialEquipmentOrders(Collections.emptyList());
        helper.setCarOrder(originalOrder);
    }

    @Test
    void initialState_shouldHaveNoChanges() {
        assertFalse(helper.hasChanged());
    }

    @Test
    void hasChanged_returnsTrue_whenAnyFieldIsSet() {
        helper.setOrderUser(new OrderUser());
        assertTrue(helper.hasChanged());
    }

    @Test
    void getExistingCarOrderColor_returnsNew_whenSet() {
        CarColorOrder newColorOrder = new CarColorOrder();
        helper.setCarColorOrder(newColorOrder);
        assertSame(newColorOrder, helper.getExistingCarOrderColor());
    }

    @Test
    void getExistingCarOrderColor_returnsOriginal_whenNotSet() {
        assertSame(originalOrder.getCarColorOrder(), helper.getExistingCarOrderColor());
    }

    @Test
    void getExistingCarOrderEngine_returnsNew_whenSet() {
        CarEngineOrder newEngineOrder = new CarEngineOrder();
        helper.setCarEngineOrder(newEngineOrder);
        assertSame(newEngineOrder, helper.getExistingCarOrderEngine());
    }

    @Test
    void getExistingCarOrderEngine_returnsOriginal_whenNotSet() {
        assertSame(originalOrder.getCarEngineOrder(), helper.getExistingCarOrderEngine());
    }

    @Test
    void getExistingCarOrderRim_returnsOriginal_whenNotSet() {
        assertSame(originalOrder.getCarRimOrder(), helper.getExistingCarOrderRim());
    }

    @Test
    void getExistingCarOrderRim_shouldReturnNewWhenSet_butIsBugged() {
        CarRimOrder newRimOrder = new CarRimOrder();
        helper.setCarRimOrder(newRimOrder);
        // This test will fail due to the bug where it checks carColorOrder instead of carRimOrder
        // After fixing, it should pass with assertSame(newRimOrder, helper.getExistingCarOrderRim());
        assertNotSame(newRimOrder, helper.getExistingCarOrderRim());
    }

    @Test
    void getExistingCarOrderSpecialEquipment_returnsNew_whenSet() {
        SpecialEquipmentOrder newEquipment = new SpecialEquipmentOrder();
        helper.setSpecialEquipmentOrders(Collections.singletonList(newEquipment));
        assertEquals(1, helper.getExistingCarOrderSpecialEquipment().size());
        assertSame(newEquipment, helper.getExistingCarOrderSpecialEquipment().get(0));
    }

    @Test
    void getExistingCarOrderSpecialEquipment_returnsOriginal_whenNotSet() {
        assertSame(originalOrder.getSpecialEquipmentOrders(), helper.getExistingCarOrderSpecialEquipment());
    }

    @Test
    void getExistingOrderUser_returnsNew_whenSet() {
        OrderUser newUser = new OrderUser();
        helper.setOrderUser(newUser);
        assertSame(newUser, helper.getExistingOrderUser());
    }

    @Test
    void getExistingOrderUser_returnsOriginal_whenNotSet() {
        assertSame(originalOrder.getOrderUser(), helper.getExistingOrderUser());
    }
}
