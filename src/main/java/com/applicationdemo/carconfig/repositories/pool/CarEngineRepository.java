package com.applicationdemo.carconfig.repositories.pool;

import com.applicationdemo.carconfig.domain.base.CarEngine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarEngineRepository extends JpaRepository<CarEngine, Long> {


    Optional<CarEngine> findByOrderNumber(String orderNumber);

    List<CarEngine> findByProductId(String productId);
}
