package com.applicationdemo.carconfig.repository.pool;

import com.applicationdemo.carconfig.entities.base.CarEngine;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Sql("/sql/data-car-engine.sql")
class CarEngineRepositoryTest {

    @Autowired
    private CarEngineRepository carEngineRepository;

    @Test
    void findByOrderNumberAndNotDeleted_found() {
        Optional<CarEngine> result = carEngineRepository.findByOrderNumberAndNotDeleted("E001");
        assertTrue(result.isPresent());
        assertEquals("B48", result.get().getModel());
    }

    @Test
    void findByOrderNumberAndNotDeleted_notFound_deleted() {
        Optional<CarEngine> result = carEngineRepository.findByOrderNumberAndNotDeleted("E003");
        assertFalse(result.isPresent());
    }

    @Test
    void findByOrderNumberAndNotDeleted_notFound_nonExistent() {
        Optional<CarEngine> result = carEngineRepository.findByOrderNumberAndNotDeleted("E999");
        assertFalse(result.isPresent());
    }

    @Test
    void findByCarEnginesByProductIdAndNotDeleted_foundOne() {
        List<CarEngine> result = carEngineRepository.findByCarEnginesByProductIdAndNotDeleted("P-E01");
        assertEquals(1, result.size());
        assertEquals("B48", result.get(0).getModel());
    }

    @Test
    void findByCarEnginesByProductIdAndNotDeleted_foundMultiple() {
        List<CarEngine> result = carEngineRepository.findByCarEnginesByProductIdAndNotDeleted("P-E02");
        assertEquals(2, result.size());
    }

    @Test
    void findByCarEnginesByProductIdAndNotDeleted_notFound() {
        List<CarEngine> result = carEngineRepository.findByCarEnginesByProductIdAndNotDeleted("P-E99");
        assertTrue(result.isEmpty());
    }

    @Test
    void findByCarEnginesByProductIdAndNotDeleted_notFound_deleted() {
        List<CarEngine> result = carEngineRepository.findByCarEnginesByProductIdAndNotDeleted("P-E03");
        assertTrue(result.isEmpty());
    }
}
