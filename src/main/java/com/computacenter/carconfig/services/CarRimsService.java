package com.computacenter.carconfig.services;

import com.computacenter.carconfig.entities.pool.CarRim;
import com.computacenter.carconfig.exceptions.ItemAddException;
import com.computacenter.carconfig.repository.pool.CarRimRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CarRimsService {

    private final CarRimRepository carRimRepository;

    /**
     * Retrieves all car rims from the database.
     * @return A list of all CarRims entities.
     */
    public List<CarRim> getAllCarRims() {
        log.debug("Fetching all car rims from the database.");
        return carRimRepository.findAll();
    }

    /**
     * Adds a single car rim to the database, ensuring no duplicates.
     *
     * @param carRim The CarEngine entity to be added.
     * @throws ItemAddException if a car color with the same name already exists.
     */

    public void addCarRim(CarRim carRim) {
        Optional<CarRim> existingRim = carRimRepository.findByModel(carRim.getModel());
        if (existingRim.isPresent()) {
            String errorMessage = "Car rim model '" + carRim.getModel() + "' already exists.";
            log.warn(errorMessage);
            throw new ItemAddException(errorMessage);
        }
        log.info("Adding new car rim: {}", carRim.getModel());
        carRimRepository.save(carRim);
    }

    /**
     * Adds a list of car rims to the database in a single batch.
     * @param carRimsList The list of CarColors entities to be added.
     * @throws ItemAddException if any of the colors cannot be added.
     */
    public void addAllCarRims(List<CarRim> carRimsList) {
        log.info("Adding a batch of {} car rims.", carRimsList.size());
        carRimsList.forEach(this::addCarRim);
    }
}