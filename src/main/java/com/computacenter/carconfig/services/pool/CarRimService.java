package com.computacenter.carconfig.services.pool;

import com.computacenter.carconfig.dto.load.CarRimLoadDto;
import com.computacenter.carconfig.dto.web.CarRimDto;
import com.computacenter.carconfig.entities.base.CarRim;
import com.computacenter.carconfig.exceptions.ItemAddException;
import com.computacenter.carconfig.exceptions.OrderException;
import com.computacenter.carconfig.mapper.CarRimMapper;
import com.computacenter.carconfig.mapper.load.CarRimLoadMapper;
import com.computacenter.carconfig.repository.pool.CarRimRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CarRimService {

    private final CarRimRepository carRimRepository;
    private final CarRimMapper carRimMapper;
    private final CarRimLoadMapper carRimLoadMapper;


    /**
     * Retrieves all car rims from the database.
     *
     * @return A list of all CarRims entities.
     */
    public List<CarRimDto> getAllCarRimsWeb() {
        log.debug("Fetching all car rims from the database.");
        return carRimRepository.findAll().stream().map(carRimMapper::toDto).toList();
    }

    /**
     * Retrieves all car rims from the database.
     *
     * @return A list of all CarRims entities.
     */
    public List<CarRimLoadDto> getAllCarRimLoad() {
        log.debug("Fetching all car rims from the database.");
        return carRimRepository.findAll().stream().map(carRimLoadMapper::toDto).toList();
    }

    /**
     * Adds a single car rim to the database, ensuring no duplicates.
     *
     * @param carRimLoadDto The CarEngine entity to be added.
     * @throws ItemAddException if a car color with the same name already exists.
     */

    public void addCarRimLoad(CarRimLoadDto carRimLoadDto) {
        Optional<CarRim> existingRim = carRimRepository.findByOrderNumberAndNotDeleted(carRimLoadDto.getModel());
        if (existingRim.isPresent()) {
            String errorMessage = "Car rim model '" + carRimLoadDto.getModel() + "' and ordernumber '" + carRimLoadDto.getOrderNumber() + "' already exists.";
            log.warn(errorMessage);
            throw new ItemAddException(errorMessage);
        }
        log.info("Adding new car rim: {}", carRimLoadDto.getModel());
        var carRim = carRimLoadMapper.toEntity(carRimLoadDto);
        carRimRepository.save(carRim);
    }

    /**
     * Adds a list of car rims to the database in a single batch.
     *
     * @param carRimsDtoLoadList The list of CarColor entities to be added.
     * @throws ItemAddException if any of the colors cannot be added.
     */
    public void addAllCarRims(List<CarRimLoadDto> carRimsDtoLoadList) {
        log.info("Adding a batch of {} car rims.", carRimsDtoLoadList.size());
        carRimsDtoLoadList.forEach(carEngineLoadDto -> {
            try {
                addCarRimLoad(carEngineLoadDto);
            } catch (ItemAddException e) {
                log.warn("{}--> skip engin", e.getMessage());
            }
        });
    }

    /**
     * Get the car rims by the product id
     *
     * @param productId The productId of the car rims.
     */
    public CarRim getCarRimByProductId(String productId) {
        log.debug("Fetching a car rim by the product id: {}", productId);
        List<CarRim> existingCarRims = carRimRepository.findByCarRimsByProductIdAndNotDeleted(productId);
        if (existingCarRims.isEmpty()) {
            String errorMessage = "Car rim with productNumber'" + productId + "' does not exists.";
            log.warn(errorMessage);
            throw new OrderException(errorMessage);
        }
        if (existingCarRims.size() > 1) {
            String errorMessage = "Multiple car rim with the same product id: '" + productId + "' please check the color productid ";
            log.warn(errorMessage);
            throw new OrderException(errorMessage);
        }
        return existingCarRims.getFirst();
    }
}