package com.computacenter.carconfig.controller;

import com.computacenter.carconfig.dto.*;
import com.computacenter.carconfig.dto.base.CarColorDto;
import com.computacenter.carconfig.enums.TransferStatus;
import com.computacenter.carconfig.exceptions.ItemAddException;
import com.computacenter.carconfig.mapper.CarColorsMapper;
import com.computacenter.carconfig.services.CarColorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/pool/car-color")
public class CarColorsController {

    private final CarColorService carColorService;
    private final CarColorsMapper carColorsMapper;

    @GetMapping(path = "/all", produces = "application/json")
    public List<CarColorDto> getAllCarColors() {
        log.debug("Getting all car color information");
        return carColorService.getAllCarColors().stream().map(carColorsMapper::toDto).toList();
    }

    @PostMapping(path = "/add", produces = "application/json", consumes = "application/json")
    public ResponseDto addCarColor(@RequestBody CarColorDto carColorDto) {
        log.debug("Adding new car color: {}", carColorDto.getName());
        try {
            carColorService.addCarColor(carColorsMapper.toEntity(carColorDto));
            return new ResponseDto("Car color added successfully", TransferStatus.SUCCESS);
        } catch (ItemAddException e) {
            log.error("Failed to add car color: {}", e.getMessage());
            return new ResponseDto("Failed to add car color", TransferStatus.FAILURE, e.getMessage());
        }
    }

    @PostMapping(path = "/add/all", produces = "application/json", consumes = "application/json")
    public ResponseDto addAllCarColors(@RequestBody List<CarColorDto> carColorDtoList) {
        log.debug("Adding {} car colors", carColorDtoList.size());
        try {
            carColorService.addAllCarColors(carColorDtoList.stream().map(carColorsMapper::toEntity).toList());
            return new ResponseDto("All car colors added successfully", TransferStatus.SUCCESS);
        } catch (ItemAddException e) {
            log.error("Failed to add all car colors: {}", e.getMessage());
            return new ResponseDto("Failed to add one or more car colors", TransferStatus.FAILURE, e.getMessage());
        }
    }

}
