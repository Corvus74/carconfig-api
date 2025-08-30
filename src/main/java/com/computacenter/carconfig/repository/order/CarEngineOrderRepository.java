package com.computacenter.carconfig.repository.order;

import com.computacenter.carconfig.entities.order.CarEngineOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarEngineOrderRepository extends JpaRepository<CarEngineOrder, Long> {
}
