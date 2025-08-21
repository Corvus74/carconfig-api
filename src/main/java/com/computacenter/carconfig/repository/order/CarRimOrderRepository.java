package com.computacenter.carconfig.repository.order;

import com.computacenter.carconfig.entities.order.CarRimOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRimOrderRepository extends JpaRepository<CarRimOrder,Integer> {
}
