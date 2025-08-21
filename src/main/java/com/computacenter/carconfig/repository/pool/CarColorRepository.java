package com.computacenter.carconfig.repository.pool;

import com.computacenter.carconfig.entities.pool.CarColors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarColorRepository extends JpaRepository<CarColors, Integer> {
    Optional<CarColors> findByName(String name);
}
