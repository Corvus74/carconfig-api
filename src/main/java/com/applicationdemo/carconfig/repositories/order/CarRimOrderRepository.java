package com.applicationdemo.carconfig.repositories.order;

import com.applicationdemo.carconfig.domain.order.CarRimOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRimOrderRepository extends JpaRepository<CarRimOrder, Long> {
}
