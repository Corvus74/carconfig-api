package com.computacenter.carconfig.services;

import com.computacenter.carconfig.entities.pool.CarEngine;
import com.computacenter.carconfig.exceptions.ItemAddException;
import com.computacenter.carconfig.repository.pool.CarEngineRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CarEngineService {

    private final CarEngineRepository carEngineRepository;

    /**
     * Retrieves all car engines from the database.
     * @return A list of all CarEngine entities.
     */
    public List<CarEngine> getAllCarEngines() {
        log.debug("Fetching all car engines from the database.");
        return carEngineRepository.findAll();
    }

    /**
     * Adds a single car engine to the database, ensuring no duplicates.
     *
     * @param carEngine The CarEngine entity to be added.
     * @throws ItemAddException if a car color with the same name already exists.
     */

    public void addCarEngine(CarEngine carEngine) {
        Optional<CarEngine> existingEngine = carEngineRepository.findByModel(carEngine.getModel());
        if (existingEngine.isPresent()) {
            String errorMessage = "Car engine model '" + carEngine.getModel() + "' already exists.";
            log.warn(errorMessage);
            throw new ItemAddException(errorMessage);
        }
        log.info("Adding new car engine: {}", carEngine.getModel());
        carEngineRepository.save(carEngine);
    }

    /**
     * Adds a list of car engines to the database in a single batch.
     *
     * @param carEnginesList The list of CarColors entities to be added.
     * @throws ItemAddException if any of the colors cannot be added.
     */
    public void addAllCarEngines(List<CarEngine> carEnginesList) {
        log.info("Adding a batch of {} car engines.", carEnginesList.size());
        carEnginesList.forEach(this::addCarEngine);
    }
}
