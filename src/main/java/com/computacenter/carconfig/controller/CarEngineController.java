package com.computacenter.carconfig.controller;

import com.computacenter.carconfig.dto.*;
import com.computacenter.carconfig.dto.base.CarEngineDto;
import com.computacenter.carconfig.enums.TransferStatus;
import com.computacenter.carconfig.exceptions.ItemAddException;
import com.computacenter.carconfig.mapper.CarEngineMapper;
import com.computacenter.carconfig.services.CarEngineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/pool/car-engine")
public class CarEngineController {

    private final CarEngineService carEngineService;
    private final CarEngineMapper carEngineMapper;

    @GetMapping(path = "/all", produces = "application/json")
    public List<CarEngineDto> getAllCarEngines() {
        log.debug("Getting all car engine information");
        return carEngineService.getAllCarEngines().stream().map(carEngineMapper::toDto).toList();
    }

    @PostMapping(path = "/add", produces = "application/json", consumes = "application/json")
    public ResponseDto addCarEngine(@RequestBody CarEngineDto carEngineDto) {
        log.debug("Adding new car engine: {}", carEngineDto.getModel());
        try {
            carEngineService.addCarEngine(carEngineMapper.toEntity(carEngineDto));
            return new ResponseDto("Car engine added successfully", TransferStatus.SUCCESS);
        } catch (ItemAddException e) {
            log.error("Failed to add car engine: {}", e.getMessage());
            return new ResponseDto("Failed to add car engine", TransferStatus.FAILURE, e.getMessage());
        }
    }

    @PostMapping(path = "/add/all", produces = "application/json", consumes = "application/json")
    public ResponseDto addAllCarEngines(@RequestBody List<CarEngineDto> carEnginesDtoList) {
        log.debug("Adding {} car engines", carEnginesDtoList.size());
        try {
            carEngineService.addAllCarEngines(carEnginesDtoList.stream().map(carEngineMapper::toEntity).toList());
            return new ResponseDto("All car engines added successfully", TransferStatus.SUCCESS);
        } catch (ItemAddException e) {
            log.error("Failed to add all car engines: {}", e.getMessage());
            return new ResponseDto("Failed to add one or more car engines", TransferStatus.FAILURE, e.getMessage());
        }
    }


}
