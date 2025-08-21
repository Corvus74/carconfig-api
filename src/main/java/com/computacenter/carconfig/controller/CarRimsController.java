package com.computacenter.carconfig.controller;

import com.computacenter.carconfig.dto.*;
import com.computacenter.carconfig.dto.base.CarRimDto;
import com.computacenter.carconfig.enums.TransferStatus;
import com.computacenter.carconfig.exceptions.ItemAddException;
import com.computacenter.carconfig.mapper.CarColorsMapper;
import com.computacenter.carconfig.mapper.CarRimMapper;
import com.computacenter.carconfig.services.CarRimsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/pool/car-rims")
public class CarRimsController {

    private final CarRimsService carRimsService;
    private final CarRimMapper carRimMapper;
    private final CarColorsMapper carColorMapper;


        @GetMapping(path = "/all", produces = "application/json")
        public List<CarRimDto> getAllCarRims() {
            log.debug("Getting all car rim information");
            return carRimsService.getAllCarRims().stream().map(carRimMapper::toDto).toList();
        }

        @PostMapping(path = "/add", produces = "application/json", consumes = "application/json")
        public ResponseDto addCarRim(@RequestBody CarRimDto carRimsDto) {
            log.debug("Adding new car rim: {}", carRimsDto.getModel());
            try {
                carRimsService.addCarRim(carRimMapper.toEntity(carRimsDto));
                return new ResponseDto("Car rim added successfully", TransferStatus.SUCCESS);
            } catch (ItemAddException e) {
                log.error("Failed to add car rim: {}", e.getMessage());
                return new ResponseDto("Failed to add car rim", TransferStatus.FAILURE, e.getMessage());
            }
        }

        @PostMapping(path = "/add/all", produces = "application/json", consumes = "application/json")
        public ResponseDto addAllCarRims(@RequestBody List<CarRimDto> carRimsDtoList) {
            log.debug("Adding {} car rims", carRimsDtoList.size());
            try {
                carRimsService.addAllCarRims(carRimsDtoList.stream().map(carRimMapper::toEntity).toList());
                return new ResponseDto("All car rims added successfully", TransferStatus.SUCCESS);
            } catch (ItemAddException e) {
                log.error("Failed to add all car rims: {}", e.getMessage());
                return new ResponseDto("Failed to add one or more car rims", TransferStatus.FAILURE, e.getMessage());
            }
        }
}
