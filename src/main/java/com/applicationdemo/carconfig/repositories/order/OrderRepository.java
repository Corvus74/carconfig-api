package com.applicationdemo.carconfig.repositories.order;

import com.applicationdemo.carconfig.domain.order.CarOrder;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<CarOrder, Long> {

    /**
     * Finds an order by the public carOrderId field.
     * Read-only to ensure participation in transactions without accidental flush.
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    Optional<CarOrder> findByCarOrderId(String orderId);

    /** Checks if an order exists for the given public carOrderId. */
    boolean existsByCarOrderId(String orderId);
}
