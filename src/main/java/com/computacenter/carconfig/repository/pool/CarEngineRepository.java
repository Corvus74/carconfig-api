package com.computacenter.carconfig.repository.pool;

import com.computacenter.carconfig.entities.base.CarEngine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarEngineRepository extends JpaRepository<CarEngine,Integer> {
    @Query("select c from CarEngine c where c.orderNumber = ?1 and upper(c.deleteFlag) != 'Y'")
    Optional<CarEngine> findByOrderNumberAndNotDeleted(String orderNumber);

    @Query("select c from CarEngine c where c.productId = ?1 and upper(c.deleteFlag) != 'Y'")
    List<CarEngine> findByCarEnginesByProductIdAndNotDeleted(String productId);
}
