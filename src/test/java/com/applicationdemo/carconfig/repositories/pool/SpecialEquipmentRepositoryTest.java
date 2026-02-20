package com.applicationdemo.carconfig.repositories.pool;

import com.applicationdemo.carconfig.domain.base.SpecialEquipment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Sql("/sql/data-special-equipment.sql")
class SpecialEquipmentRepositoryTest {

    @Autowired
    private SpecialEquipmentRepository specialEquipmentRepository;

    @Test
    void findByOrderNumberAndNotDeleted_found() {
        Optional<SpecialEquipment> result = specialEquipmentRepository.findByOrderNumberAndNotDeleted("SE001");
        assertTrue(result.isPresent());
        assertEquals("Heated Seats", result.get().getEquipmentName());
    }

    @Test
    void findByOrderNumberAndNotDeleted_notFound_deleted() {
        Optional<SpecialEquipment> result = specialEquipmentRepository.findByOrderNumberAndNotDeleted("SE003");
        assertFalse(result.isPresent());
    }

    @Test
    void findByOrderNumberAndNotDeleted_notFound_nonExistent() {
        Optional<SpecialEquipment> result = specialEquipmentRepository.findByOrderNumberAndNotDeleted("SE999");
        assertFalse(result.isPresent());
    }

    @Test
    void findBySpecialEquipmentByProductIdAndNotDeleted_foundOne() {
        List<SpecialEquipment> result = specialEquipmentRepository.findBySpecialEquipmentByProductIdAndNotDeleted("P-SE01");
        assertEquals(1, result.size());
        assertEquals("Heated Seats", result.get(0).getEquipmentName());
    }

    @Test
    void findBySpecialEquipmentByProductIdAndNotDeleted_foundMultiple() {
        List<SpecialEquipment> result = specialEquipmentRepository.findBySpecialEquipmentByProductIdAndNotDeleted("P-SE02");
        assertEquals(2, result.size());
    }

    @Test
    void findBySpecialEquipmentByProductIdAndNotDeleted_notFound() {
        List<SpecialEquipment> result = specialEquipmentRepository.findBySpecialEquipmentByProductIdAndNotDeleted("P-SE99");
        assertTrue(result.isEmpty());
    }

    @Test
    void findBySpecialEquipmentByProductIdAndNotDeleted_notFound_deleted() {
        List<SpecialEquipment> result = specialEquipmentRepository.findBySpecialEquipmentByProductIdAndNotDeleted("P-SE03");
        assertTrue(result.isEmpty());
    }
}
