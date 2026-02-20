package com.applicationdemo.carconfig.repositories.order;

import com.applicationdemo.carconfig.domain.order.CarOrder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@DataJpaTest
@Sql(scripts = {
    "/sql/data-order-user.sql",
    "/sql/data-order-status.sql",
    "/sql/data-car-color.sql",
    "/sql/data-car-engine.sql",
    "/sql/data-car-rim.sql",
    "/sql/data-special-equipment.sql",
    "/sql/data-car-color-order.sql",
    "/sql/data-car-engine-order.sql",
    "/sql/data-car-rim-order.sql",
    "/sql/data-special-equipment-order.sql",
    "/sql/data-car-order.sql"
})
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Test
    void findByCarOrderId_found() {
        Optional<CarOrder> result = orderRepository.findByCarOrderId("order-1");
        assertTrue(result.isPresent());
        assertEquals("A standard order", result.get().getDescription());
        // Check that related entities are loaded
        assertNotNull(result.get().getOrderUser());
        assertEquals("testuser", result.get().getOrderUser().getUserName());
    }

    @Test
    void findByCarOrderId_foundWithSpecialEquipment() {
        Optional<CarOrder> result = orderRepository.findByCarOrderId("order-2");
        assertTrue(result.isPresent());
        assertEquals("A second order with equipment", result.get().getDescription());
        // Eager fetch is not default for ManyToMany, but the test will load it if accessed in a transaction
        assertEquals(2, result.get().getSpecialEquipmentOrders().size());
    }

    @Test
    void findByCarOrderId_foundButDeleted() {
        // The query does not filter by delete_flag, so it should find it.
        Optional<CarOrder> result = orderRepository.findByCarOrderId("order-3");
        assertTrue(result.isPresent());
        assertEquals("Y", result.get().getDeleteFlag());
    }

    @Test
    void findByCarOrderId_notFound() {
        Optional<CarOrder> result = orderRepository.findByCarOrderId("order-999");
        assertFalse(result.isPresent());
    }

    @Test
    void existsByCarOrderId_true() {
        boolean result = orderRepository.existsByCarOrderId("order-1");
        assertTrue(result);
    }

    @Test
    void existsByCarOrderId_true_deleted() {
        boolean result = orderRepository.existsByCarOrderId("order-3");
        assertTrue(result);
    }

    @Test
    void existsByCarOrderId_false() {
        boolean result = orderRepository.existsByCarOrderId("order-999");
        assertFalse(result);
    }
}
