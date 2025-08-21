package com.computacenter.carconfig.controller;

import com.computacenter.carconfig.dto.*;
import com.computacenter.carconfig.dto.base.BaseConfigDto;
import com.computacenter.carconfig.enums.TransferStatus;
import com.computacenter.carconfig.exceptions.ItemAddException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RequestMapping("/pool/config")
public class PoolController {

    private final CarColorsController carColorsController;
    private final CarEngineController carEnginesController;
    private final CarRimsController carRimsController;
    private final SpecialEquipmentController specialEquipmentController;

    @GetMapping(produces = "application/json")
    public BaseConfigDto getBaseConfiguration() {
        log.info("Fetching all base configuration data from sub-controllers.");

        BaseConfigDto configDto = new BaseConfigDto();
        configDto.setCarColors(carColorsController.getAllCarColors());
        configDto.setCarEngines(carEnginesController.getAllCarEngines());
        configDto.setCarRims(carRimsController.getAllCarRims());
        configDto.setSpecialEquipment(specialEquipmentController.getAllSpecialEquipment());

        return configDto;
    }

    @PostMapping(path = "/add", produces = "application/json", consumes = "application/json")
    public ResponseDto addBaseConfiguration(@RequestBody BaseConfigDto baseConfigDto) {
        log.info("Starting batch add for all base configuration components.");

        try {
            // Call the batch add methods on each sub-controller
            carColorsController.addAllCarColors(baseConfigDto.getCarColors());
            carEnginesController.addAllCarEngines(baseConfigDto.getCarEngines());
            carRimsController.addAllCarRims(baseConfigDto.getCarRims());
            specialEquipmentController.addAllSpecialEquipment(baseConfigDto.getSpecialEquipment());

            log.info("Successfully added all base configuration components.");
            return new ResponseDto("All base configuration components added successfully", TransferStatus.SUCCESS);
        } catch (ItemAddException e) {
            log.error("Failed to add all base configuration components: {}", e.getMessage());
            return new ResponseDto("Failed to add one or more base configuration components", TransferStatus.FAILURE, e.getMessage());
        } catch (Exception e) {
            log.error("An unexpected error occurred during base configuration import: {}", e.getMessage());
            return new ResponseDto("An unexpected error occurred", TransferStatus.FAILURE, e.getMessage());
        }
    }
}
