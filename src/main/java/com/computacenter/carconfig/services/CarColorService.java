package com.computacenter.carconfig.services;

import com.computacenter.carconfig.dto.load.CarColorLoadDto;
import com.computacenter.carconfig.dto.web.CarColorDto;
import com.computacenter.carconfig.entities.base.CarColor;
import com.computacenter.carconfig.exceptions.ItemAddException;
import com.computacenter.carconfig.exceptions.OrderException;
import com.computacenter.carconfig.mapper.CarColorMapper;
import com.computacenter.carconfig.mapper.load.CarColorLoadMapper;
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
    private final CarColorMapper carColorMapper;
    private final CarColorLoadMapper carColorLoadMapper;

    /**
     * Retrieves all car colors from the database.
     *
     * @return A list of all CarColor entities.
     */
    public List<CarColorLoadDto> getAllCarColorLoad() {
        log.debug("Fetching all full car colors from the database.");
        List<CarColor> carColors = carColorRepository.findAll();
        return carColors.stream().map(carColorLoadMapper::toDto).toList();
    }

    /**
     * Retrieves all car colors from the database.
     *
     * @return A list of all CarColor entities.
     */
    public List<CarColorDto> getAllCarColorsWeb() {
        log.debug("Fetching all car colors from the database.");
        return carColorRepository.findAll().stream().map(carColorMapper::toDto).toList();
    }

    /**
     * Adds a single car color to the database, ensuring no duplicates.
     *
     * @param carColorLoadDto The CarColor entity to be added.
     * @throws ItemAddException if a car color with the same name already exists.
     */
    public void addCarColor(CarColorLoadDto carColorLoadDto) {

        Optional<CarColor> existingColor = carColorRepository.findByOrderNumberAndNotDeleted(carColorLoadDto.getOrderNumber());
        if (existingColor.isPresent()) {
            String errorMessage = "Car color with orderNumber'" + carColorLoadDto.getOrderNumber() + "' already exists.";
            log.warn(errorMessage);
            throw new ItemAddException(errorMessage);
        }
        CarColor carColor = carColorLoadMapper.toEntity(carColorLoadDto);
        log.info("Adding new car color: {}", carColor.getColorName());
        carColorRepository.save(carColor);
    }

    /**
     * Adds a list of car colors to the database in a single batch.
     *
     * @param carColorDtos The list of CarColor entities to be added.
     * @throws ItemAddException if any of the colors cannot be added.
     */
    public void addAllCarColors(List<CarColorLoadDto> carColorDtos) {
        log.info("Adding a batch of {} car colors.", carColorDtos.size());
        carColorDtos.forEach(carColorDto -> {
            try {
                addCarColor(carColorDto);
            } catch (ItemAddException e) {
                log.warn("{}--> skip color", e.getMessage());
            }
        });
    }
    /**
     * Get the color by the product id
     *
     * @param productId The productId of the color.
     */
    public CarColor getColorByProductId(String productId) {
        log.debug("Fetching a car color by the product id: {}", productId);
        List<CarColor> existingColors = carColorRepository.findByCarColorByProductIdAndNotDeleted(productId);
        if (existingColors.isEmpty()) {
            String errorMessage = "Car color with productNumber'" + productId + "' does not exists.";
            log.warn(errorMessage);
            throw new OrderException(errorMessage);
        }
        if (existingColors.size() > 1) {
            String errorMessage = "Multiple car colors with the same product id: '" + productId + "' please check the color productid ";
            log.warn(errorMessage);
            throw new OrderException(errorMessage);
        }
        return existingColors.getFirst();
    }

}
