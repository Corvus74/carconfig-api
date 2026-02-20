package com.applicationdemo.carconfig.repositories.order;

import com.applicationdemo.carconfig.domain.order.SpecialEquipmentOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecialEquipmentOrderRepository extends JpaRepository<SpecialEquipmentOrder, Long>{

}
