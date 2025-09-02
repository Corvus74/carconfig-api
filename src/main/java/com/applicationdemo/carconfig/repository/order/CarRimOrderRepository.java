package com.applicationdemo.carconfig.repository.order;

import com.applicationdemo.carconfig.entities.order.CarRimOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRimOrderRepository extends JpaRepository<CarRimOrder, Long> {
}
