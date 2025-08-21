package com.computacenter.carconfig.services;

import com.computacenter.carconfig.entities.pool.CarColors;
import com.computacenter.carconfig.exceptions.ItemAddException;
import com.computacenter.carconfig.repository.pool.CarColorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CarColorService {

    private final CarColorRepository carColorRepository;

    /**
     * Retrieves all car colors from the database.
     * @return A list of all CarColors entities.
     */
    public List<CarColors> getAllCarColors() {
        log.debug("Fetching all car colors from the database.");
        return carColorRepository.findAll();
    }

    /**
     * Adds a single car color to the database, ensuring no duplicates.
     * @param carColors The CarColors entity to be added.
     * @throws ItemAddException if a car color with the same name already exists.
     */
    public void addCarColor(CarColors carColors) {
        Optional<CarColors> existingColor = carColorRepository.findByName(carColors.getName());
        if (existingColor.isPresent()) {
            String errorMessage = "Car color '" + carColors.getName() + "' already exists.";
            log.warn(errorMessage);
            throw new ItemAddException(errorMessage);
        }

        log.info("Adding new car color: {}", carColors.getName());
        carColorRepository.save(carColors);
    }

    /**
     * Adds a list of car colors to the database in a single batch.
     * @param carPaintings The list of CarColors entities to be added.
     * @throws ItemAddException if any of the colors cannot be added.
     */
    public void addAllCarColors(List<CarColors> carPaintings) {
        log.info("Adding a batch of {} car colors.", carPaintings.size());
        carPaintings.forEach(this::addCarColor);
    }
}
