package com.applicationdemo.carconfig.repositories.pool;

import com.applicationdemo.carconfig.domain.base.CarRim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRimRepository  extends JpaRepository<CarRim, Long> {

    Optional<CarRim> findByOrderNumber(String orderNumber);
    List<CarRim> findByProductId(String productId);
}
