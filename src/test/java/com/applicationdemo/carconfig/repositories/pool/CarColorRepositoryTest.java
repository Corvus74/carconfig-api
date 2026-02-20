package com.applicationdemo.carconfig.repositories.pool;

import com.applicationdemo.carconfig.domain.base.CarColor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@DataJpaTest
@Sql("/sql/data-car-color.sql")
class CarColorRepositoryTest {

    @Autowired
    private CarColorRepository carColorRepository;

    @Test
    void findByOrderNumberAndNotDeleted_found() {
        Optional<CarColor> result = carColorRepository.findByOrderNumberAndNotDeleted("C001");
        assertTrue(result.isPresent());
        assertEquals("Alpine White", result.get().getColorName());
    }

    @Test
    void findByOrderNumberAndNotDeleted_notFound_deleted() {
        Optional<CarColor> result = carColorRepository.findByOrderNumberAndNotDeleted("C003");
        assertFalse(result.isPresent());
    }

    @Test
    void findByOrderNumberAndNotDeleted_notFound_nonExistent() {
        Optional<CarColor> result = carColorRepository.findByOrderNumberAndNotDeleted("C999");
        assertFalse(result.isPresent());
    }

    @Test
    void findByCarColorByProductIdAndNotDeleted_foundOne() {
        List<CarColor> result = carColorRepository.findByCarColorByProductIdAndNotDeleted("P-C01");
        assertEquals(1, result.size());
        assertEquals("Alpine White", result.get(0).getColorName());
    }

    @Test
    void findByCarColorByProductIdAndNotDeleted_foundMultiple() {
        // Two colors, 'Black Sapphire' and 'Glacier Silver', share the same product ID 'P-C02'
        List<CarColor> result = carColorRepository.findByCarColorByProductIdAndNotDeleted("P-C02");
        assertEquals(2, result.size());
    }

    @Test
    void findByCarColorByProductIdAndNotDeleted_notFound() {
        List<CarColor> result = carColorRepository.findByCarColorByProductIdAndNotDeleted("P-C99");
        assertTrue(result.isEmpty());
    }

    @Test
    void findByCarColorByProductIdAndNotDeleted_notFound_deleted() {
        // 'Melbourne Red' with product ID 'P-C03' is marked as deleted
        List<CarColor> result = carColorRepository.findByCarColorByProductIdAndNotDeleted("P-C03");
        assertTrue(result.isEmpty());
    }
}
