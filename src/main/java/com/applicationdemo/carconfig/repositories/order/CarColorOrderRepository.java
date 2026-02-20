package com.applicationdemo.carconfig.repositories.order;

import com.applicationdemo.carconfig.domain.order.CarColorOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarColorOrderRepository extends JpaRepository<CarColorOrder, Long> {
}
