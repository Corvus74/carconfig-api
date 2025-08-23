package com.computacenter.carconfig.repository.order;

import com.computacenter.carconfig.entities.base.SpecialEquipment;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecialEquipmentOrderRepository extends JpaSpecificationExecutor<SpecialEquipment> {
}
