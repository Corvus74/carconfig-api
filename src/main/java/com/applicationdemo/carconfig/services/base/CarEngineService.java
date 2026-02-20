package com.applicationdemo.carconfig.services.base;

import com.applicationdemo.carconfig.dto.load.CarEngineLoadDto;
import com.applicationdemo.carconfig.dto.web.CarEngineDto;
import com.applicationdemo.carconfig.domain.base.CarEngine;
import com.applicationdemo.carconfig.exceptions.ItemAddException;
import com.applicationdemo.carconfig.exceptions.OrderException;
import com.applicationdemo.carconfig.mapper.web.CarEngineMapper;
import com.applicationdemo.carconfig.mapper.base.CarEngineBaseMapper;
import com.applicationdemo.carconfig.repositories.pool.CarEngineRepository;
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
    private final CarEngineMapper carEngineMapper;
    private final CarEngineBaseMapper carEngineBaseMapper;

    /**
     * Retrieves all car engines from the database.
     *
     * @return A list of all CarEngine entities.
     */
    public List<CarEngineDto> getAllCarEnginesWeb() {
        log.debug("Fetching all car engines for web from the database.");
        return carEngineRepository.findAll().stream().map(carEngineMapper::toDto).toList();
    }

    /**
     * Retrieves all car engines from the database.
     *
     * @return A list of all CarEngine entities.
     */
    public List<CarEngineLoadDto> getAllCarEngineLoad() {
        log.debug("Fetching all car engines from the database.");
        return carEngineRepository.findAll().stream().map(carEngineBaseMapper::toDto).toList();
    }

    /**
     * Adds a single car engine to the database, ensuring no duplicates.
     *
     * @param carEngineLoadDto The CarEngine entity to be added.
     * @throws ItemAddException if a car color with the same name already exists.
     */

    public void addCarEngine(CarEngineLoadDto carEngineLoadDto) {
        Optional<CarEngine> existingEngine = carEngineRepository.findByOrderNumberAndNotDeleted(carEngineLoadDto.getOrderNumber());
        if (existingEngine.isPresent()) {
            String errorMessage = "Car engine model '" + carEngineLoadDto.getOrderNumber() + "' and ordernumber '" + carEngineLoadDto.getOrderNumber() + "' already exists.";
            log.warn(errorMessage);
            throw new ItemAddException(errorMessage);
        }
        log.info("Adding new car engine: {}", carEngineLoadDto.getOrderNumber());
        var carEngine = carEngineBaseMapper.toEntity(carEngineLoadDto);
        carEngineRepository.save(carEngine);
    }

    /**
     * Adds a list of car engines to the database in a single batch.
     *
     * @param carEnginesList The list of CarColor entities to be added.
     * @throws ItemAddException if any of the colors cannot be added.
     */
    public void addAllCarEngines(List<CarEngineLoadDto> carEnginesList) {
        log.info("Adding a batch of {} car engines.", carEnginesList.size());
        carEnginesList.forEach(carEngineLoadDto -> {
            try {
                addCarEngine(carEngineLoadDto);
            } catch (ItemAddException e) {
                log.warn("{}--> skip engin", e.getMessage());
            }
        });
    }
    /**
     * Get the car engines by the product id
     *
     * @param productId The productId of the car engine.
     */
    public CarEngine getCarEngineByProductId(String productId) {
        log.debug("Fetching a car engine by the product id: {}", productId);
        List<CarEngine> existingCarEngines = carEngineRepository.findByCarEnginesByProductIdAndNotDeleted(productId);
        if (existingCarEngines.isEmpty()) {
            String errorMessage = "Car engine with productNumber'" + productId + "' does not exists.";
            log.warn(errorMessage);
            throw new OrderException(errorMessage);
        }
        if (existingCarEngines.size() > 1) {
            String errorMessage = "Multiple car engines with the same product id: '" + productId + "' please check the color productid ";
            log.warn(errorMessage);
            throw new OrderException(errorMessage);
        }
        return existingCarEngines.getFirst();
    }
}
