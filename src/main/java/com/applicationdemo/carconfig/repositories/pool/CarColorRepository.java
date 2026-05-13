package com.applicationdemo.carconfig.repositories.pool;

import com.applicationdemo.carconfig.domain.base.CarColor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarColorRepository extends JpaRepository<CarColor, Long> {

    Optional<CarColor> findByOrderNumber(String orderNumber);

    List<CarColor> findByProductId(String productId);

}
