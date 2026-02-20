package com.applicationdemo.carconfig.repositories.pool;

import com.applicationdemo.carconfig.domain.base.CarRim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRimRepository  extends JpaRepository<CarRim, Long> {
    /** Retrieve a rim by its orderNumber when active (not deleted). */
    @Query("select c from CarRim c where c.orderNumber = ?1 and (upper(c.deleteFlag) = 'N' or c.deleteFlag is null)")
    Optional<CarRim> findByOrderNumberAndNotDeleted(String orderNumber);

    /** Returns active rims matching the productId. */
    @Query("select c from CarRim c where c.productId = ?1 and (upper(c.deleteFlag) = 'N' or c.deleteFlag is null)")
    List<CarRim> findByCarRimsByProductIdAndNotDeleted(String productId);
}
