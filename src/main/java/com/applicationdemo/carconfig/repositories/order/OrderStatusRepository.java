package com.applicationdemo.carconfig.repositories.order;

import com.applicationdemo.carconfig.domain.order.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderStatusRepository extends JpaRepository<OrderStatus, Long> {
}
