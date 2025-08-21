package com.computacenter.carconfig.services;

import com.computacenter.carconfig.entities.pool.SpecialEquipment;
import com.computacenter.carconfig.exceptions.ItemAddException;
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

    /**
     * Retrieves all car colors from the database.
     * @return A list of all CarColors entities.
     */
    public List<SpecialEquipment> getAllSpecialEquipments() {
        log.debug("Fetching all special equipment from the database.");
        return specialEquipmentRepository.findAll();
    }
    /**
     * Adds a single car engine to the database, ensuring no duplicates.
     *
     * @param specialEquipment The SpecialEquipment entity to be added.
     * @throws ItemAddException if a SpecialEquipment with the same name already exists.
     */

    public void addSpecialEquipment(SpecialEquipment specialEquipment) {
        Optional<SpecialEquipment> existingEquipment = specialEquipmentRepository.findByName(specialEquipment.getName());
        if (existingEquipment.isPresent()) {
            String errorMessage = "Special equipment name '" + specialEquipment.getName() + "' already exists.";
            log.warn(errorMessage);
            throw new ItemAddException(errorMessage);
        }
        log.info("Adding new special equipment: {}", specialEquipment.getName());
        specialEquipmentRepository.save(specialEquipment);
    }

    public void addAllSpecialEquipment(List<SpecialEquipment> specialEquipmentList) {
        log.info("Adding a batch of {} special equipment items.", specialEquipmentList.size());
        specialEquipmentList.forEach(this::addSpecialEquipment);
    }

}

