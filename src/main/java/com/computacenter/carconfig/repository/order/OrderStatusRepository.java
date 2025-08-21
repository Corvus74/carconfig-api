package com.computacenter.carconfig.repository.order;

import com.computacenter.carconfig.entities.order.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderStatusRepository extends JpaRepository<OrderStatus,Integer> {
}
