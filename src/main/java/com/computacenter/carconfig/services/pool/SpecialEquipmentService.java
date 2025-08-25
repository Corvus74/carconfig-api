package com.computacenter.carconfig.services.pool;

import com.computacenter.carconfig.dto.load.SpecialEquipmentLoadDto;
import com.computacenter.carconfig.dto.web.SpecialEquipmentDto;
import com.computacenter.carconfig.entities.base.SpecialEquipment;
import com.computacenter.carconfig.exceptions.ItemAddException;
import com.computacenter.carconfig.exceptions.OrderException;
import com.computacenter.carconfig.mapper.SpecialEquipmentMapper;
import com.computacenter.carconfig.mapper.load.SpecialEquipmentLoadMapper;
import com.computacenter.carconfig.repository.pool.SpecialEquipmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SpecialEquipmentService {


    private final SpecialEquipmentRepository specialEquipmentRepository;
    private final SpecialEquipmentMapper specialEquipmentMapper;
    private final SpecialEquipmentLoadMapper specialEquipmentLoadMapper;

    /**
     * Retrieves all car colors from the database.
     * @return A list of all CarColor entities.
     */
    public List<SpecialEquipmentDto> getAllSpecialEquipmentsWeb() {
        log.debug("Fetching all special equipment from the database.");
        return specialEquipmentRepository.findAll().stream().map(specialEquipmentMapper::toDto).toList();
    }
    /**
     * Retrieves all car colors from the database.
     * @return A list of all CarColor entities.
     */
    public List<SpecialEquipmentLoadDto> getAllSpecialEquipmentsLoad() {
        log.debug("Fetching all special equipment from the database.");
        return specialEquipmentRepository.findAll().stream().map(specialEquipmentLoadMapper::toDto).toList();
    }
    /**
     * Adds a single car engine to the database, ensuring no duplicates.
     *
     * @param specialEquipmentLoadDto The SpecialEquipment entity to be added.
     * @throws ItemAddException if a SpecialEquipment with the same name already exists.
     */

    public void addSpecialEquipment(SpecialEquipmentLoadDto specialEquipmentLoadDto) {
        Optional<SpecialEquipment> existingEquipment = specialEquipmentRepository.findByOrderNumberAndNotDeleted(specialEquipmentLoadDto.getOrderNumber());
        if (existingEquipment.isPresent()) {
            String errorMessage = "Special equipment name '" + specialEquipmentLoadDto.getEquipmentName() + "' and ordernumber '" + specialEquipmentLoadDto.getOrderNumber() +"' already exists.";
            log.warn(errorMessage);
            throw new ItemAddException(errorMessage);
        }
        log.info("Adding new special equipment: {}", specialEquipmentLoadDto.getEquipmentName());
        var specialEquipment = specialEquipmentLoadMapper.toEntity(specialEquipmentLoadDto);
        specialEquipmentRepository.save(specialEquipment);
    }

    public void addAllSpecialEquipment(List<SpecialEquipmentLoadDto> specialEquipmentLoadDtos) {
        log.info("Adding a batch of {} special equipment items.", specialEquipmentLoadDtos.size());
        specialEquipmentLoadDtos.forEach(specialEquipmentLoadDto -> {
            try {
                addSpecialEquipment(specialEquipmentLoadDto);
            } catch (ItemAddException e) {
                log.warn("{}--> skip engin", e.getMessage());
            }
        });
    }

    /**
     * Get the car rims by the product id
     *
     * @param productId The productId of the specialEquipment.
     */
    public SpecialEquipment getSpecialEquipmentByProductId(String productId) {
        log.debug("Fetching a special equipment by the product id: {}", productId);
        List<SpecialEquipment> existingSpecialEquipments = specialEquipmentRepository.findBySpecialEquipmentByProductIdAndNotDeleted(productId);
        if (existingSpecialEquipments.isEmpty()) {
            String errorMessage = "Special equipment with productNumber'" + productId + "' does not exists.";
            log.warn(errorMessage);
            throw new OrderException(errorMessage);
        }
        if (existingSpecialEquipments.size() > 1) {
            String errorMessage = "Multiple special equipment with the same product id: '" + productId + "' please check the color productid ";
            log.warn(errorMessage);
            throw new OrderException(errorMessage);
        }
        return existingSpecialEquipments.getFirst();
    }

}

