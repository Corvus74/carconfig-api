package com.computacenter.carconfig.repository.order;

import com.computacenter.carconfig.entities.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order,Integer> {
    Optional<Order> findByOrderId(String orderId);

    boolean existsByOrderId(String orderId);
}
