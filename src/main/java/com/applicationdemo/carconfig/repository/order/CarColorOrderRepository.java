package com.applicationdemo.carconfig.repository.order;

import com.applicationdemo.carconfig.entities.order.CarColorOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarColorOrderRepository extends JpaRepository<CarColorOrder, Long> {
}
