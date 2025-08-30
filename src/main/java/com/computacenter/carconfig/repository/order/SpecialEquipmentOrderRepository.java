package com.computacenter.carconfig.repository.order;

import com.computacenter.carconfig.entities.order.SpecialEquipmentOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecialEquipmentOrderRepository extends JpaRepository<SpecialEquipmentOrder, Long>{

}
