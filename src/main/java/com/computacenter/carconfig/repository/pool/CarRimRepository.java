package com.computacenter.carconfig.repository.pool;

import com.computacenter.carconfig.entities.base.CarRim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRimRepository  extends JpaRepository<CarRim,Integer> {
    @Query("select c from CarRim c where c.orderNumber = ?1 and upper(c.deleteFlag) != 'Y'")
    Optional<CarRim> findByOrderNumberAndNotDeleted(String model);

    @Query("select c from CarRim c where c.productId = ?1 and upper(c.deleteFlag) != 'Y'")
    List<CarRim> findByCarRimsByProductIdAndNotDeleted(String productId);
}
