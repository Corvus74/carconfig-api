package com.applicationdemo.carconfig.repositories.order;

import com.applicationdemo.carconfig.domain.order.CarEngineOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarEngineOrderRepository extends JpaRepository<CarEngineOrder, Long> {
}
