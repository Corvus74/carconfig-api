package com.computacenter.carconfig.repository.pool;

import com.computacenter.carconfig.entities.pool.SpecialEquipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpecialEquipmentRepository extends JpaRepository<SpecialEquipment,Integer>{
    Optional<SpecialEquipment> findByName(String name);
}
