package com.computacenter.carconfig.repository.pool;

import com.computacenter.carconfig.entities.base.CarColor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarColorRepository extends JpaRepository<CarColor, Integer> {
    /**
     * Retrieve a color by its orderNumber when it is not marked as deleted.
     * The data model uses deleteFlag = 'N' (or null) to indicate active rows.
     */
    @Query("select c from CarColor c where c.orderNumber = ?1 and (upper(c.deleteFlag) = 'N' or c.deleteFlag is null)")
    Optional<CarColor> findByOrderNumberAndNotDeleted(String orderNumber);

    /** Returns active colors by a productId. Should return either 0 or 1 row; multiple rows are considered invalid. */
    @Query("select c from CarColor c where c.productId = ?1 and(upper(c.deleteFlag) = 'N' or c.deleteFlag is null)")
    List<CarColor> findByCarColorByProductIdAndNotDeleted(String productId);
}
