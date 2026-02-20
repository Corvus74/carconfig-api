package com.applicationdemo.carconfig.repositories.pool;

import com.applicationdemo.carconfig.domain.base.CarRim;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Sql("/sql/data-car-rim.sql")
class CarRimRepositoryTest {

    @Autowired
    private CarRimRepository carRimRepository;

    @Test
    void findByOrderNumberAndNotDeleted_found() {
        Optional<CarRim> result = carRimRepository.findByOrderNumberAndNotDeleted("R001");
        assertTrue(result.isPresent());
        assertEquals("S18A", result.get().getModel());
    }

    @Test
    void findByOrderNumberAndNotDeleted_notFound_deleted() {
        Optional<CarRim> result = carRimRepository.findByOrderNumberAndNotDeleted("R003");
        assertFalse(result.isPresent());
    }

    @Test
    void findByOrderNumberAndNotDeleted_notFound_nonExistent() {
        Optional<CarRim> result = carRimRepository.findByOrderNumberAndNotDeleted("R999");
        assertFalse(result.isPresent());
    }

    @Test
    void findByCarRimsByProductIdAndNotDeleted_foundOne() {
        List<CarRim> result = carRimRepository.findByCarRimsByProductIdAndNotDeleted("P-R01");
        assertEquals(1, result.size());
        assertEquals("S18A", result.get(0).getModel());
    }

    @Test
    void findByCarRimsByProductIdAndNotDeleted_foundMultiple() {
        List<CarRim> result = carRimRepository.findByCarRimsByProductIdAndNotDeleted("P-R02");
        assertEquals(2, result.size());
    }

    @Test
    void findByCarRimsByProductIdAndNotDeleted_notFound() {
        List<CarRim> result = carRimRepository.findByCarRimsByProductIdAndNotDeleted("P-R99");
        assertTrue(result.isEmpty());
    }

    @Test
    void findByCarRimsByProductIdAndNotDeleted_notFound_deleted() {
        List<CarRim> result = carRimRepository.findByCarRimsByProductIdAndNotDeleted("P-R03");
        assertTrue(result.isEmpty());
    }
}
