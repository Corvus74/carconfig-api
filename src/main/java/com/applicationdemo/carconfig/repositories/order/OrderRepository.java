package com.applicationdemo.carconfig.repositories.order;

import com.applicationdemo.carconfig.domain.order.CarOrder;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<CarOrder, Long> {

    @Transactional
    Optional<CarOrder> findByCarOrderId(String orderId);

    boolean existsByCarOrderId(String orderId);
}
