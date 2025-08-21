package com.computacenter.carconfig.repository.pool;

import com.computacenter.carconfig.entities.pool.CarRim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarRimRepository  extends JpaRepository<CarRim,Integer> {
    Optional<CarRim> findByModel(String model);
}
