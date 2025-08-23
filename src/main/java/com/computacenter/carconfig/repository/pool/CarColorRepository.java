package com.computacenter.carconfig.repository.pool;

import com.computacenter.carconfig.entities.base.CarColor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarColorRepository extends JpaRepository<CarColor, Integer> {
    @Query("select c from CarColor c where c.orderNumber = ?1 and c.deleteFlag != 'Y' ")
    Optional<CarColor> findByOrderNumberAndNotDeleted(String name);

    @Query("select c from CarColor c where c.productId = ?1 and c.deleteFlag != 'Y' ")
    List<CarColor> findByCarColorByProductIdAndNotDeleted(String productId);
}
