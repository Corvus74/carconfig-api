package com.applicationdemo.carconfig.repository.pool;

import com.applicationdemo.carconfig.entities.base.CarEngine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarEngineRepository extends JpaRepository<CarEngine, Long> {
    /** Retrieve an engine by its orderNumber when active (not deleted). */
    @Query("select c from CarEngine c where c.orderNumber = ?1 and (upper(c.deleteFlag) = 'N' or c.deleteFlag is null)")
    Optional<CarEngine> findByOrderNumberAndNotDeleted(String orderNumber);

    /** Returns active engines matching the productId. */
    @Query("select c from CarEngine c where c.productId = ?1 and (upper(c.deleteFlag) = 'N' or c.deleteFlag is null)")
    List<CarEngine> findByCarEnginesByProductIdAndNotDeleted(String productId);
}
