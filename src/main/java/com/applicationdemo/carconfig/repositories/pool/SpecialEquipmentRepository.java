package com.applicationdemo.carconfig.repositories.pool;

import com.applicationdemo.carconfig.domain.base.SpecialEquipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpecialEquipmentRepository extends JpaRepository<SpecialEquipment, Long>{

    Optional<SpecialEquipment> findByOrderNumber(String orderNumber);

    List<SpecialEquipment> findByProductId(String productId);
}
