package com.applicationdemo.carconfig.services.base;

import com.applicationdemo.carconfig.dto.load.CarColorLoadDto;
import com.applicationdemo.carconfig.dto.web.CarColorDto;
import com.applicationdemo.carconfig.domain.base.CarColor;
import com.applicationdemo.carconfig.exceptions.ItemAddException;
import com.applicationdemo.carconfig.exceptions.OrderException;
import com.applicationdemo.carconfig.mapper.web.CarColorMapper;
import com.applicationdemo.carconfig.mapper.base.CarColorBaseMapper;
import com.applicationdemo.carconfig.repositories.pool.CarColorRepository;
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
    private final CarColorBaseMapper carColorBaseMapper;

    /**
     * Retrieves all car colors from the database.
     *
     * @return A list of all CarColor entities.
     */
    public List<CarColorLoadDto> getAllCarColorLoad() {
        log.debug("Fetching all full car colors for web from the database.");
        List<CarColor> carColors = carColorRepository.findAll();
        return carColors.stream().map(carColorBaseMapper::toDto).toList();
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

        Optional<CarColor> existingColor = carColorRepository.findByOrderNumber(carColorLoadDto.getOrderNumber());
        if (existingColor.isPresent()) {
            String errorMessage = "Car color with orderNumber'" + carColorLoadDto.getOrderNumber() + "' already exists.";
            log.warn(errorMessage);
            throw new ItemAddException(errorMessage);
        }
        CarColor carColor = carColorBaseMapper.toEntity(carColorLoadDto);
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
        List<CarColor> existingColors = carColorRepository.findByProductId(productId);
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
