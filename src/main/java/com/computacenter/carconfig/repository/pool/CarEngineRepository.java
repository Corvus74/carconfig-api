package com.computacenter.carconfig.repository.pool;

import com.computacenter.carconfig.entities.pool.CarEngine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarEngineRepository extends JpaRepository<CarEngine,Integer> {
    Optional<CarEngine> findByModel(String model);
}
