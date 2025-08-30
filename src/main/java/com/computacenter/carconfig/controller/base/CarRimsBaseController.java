package com.computacenter.carconfig.controller.base;

import com.computacenter.carconfig.dto.ResponseDto;
import com.computacenter.carconfig.dto.load.CarRimLoadDto;
import com.computacenter.carconfig.enums.TransferStatus;
import com.computacenter.carconfig.exceptions.ItemAddException;
import com.computacenter.carconfig.services.base.CarRimService;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Hidden
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/load/car-rims")
public class CarRimsBaseController {

    private final CarRimService carRimService;

        @GetMapping(path = "/all", produces = "application/json")
        public List<CarRimLoadDto> getAllCarRims() {
            log.debug("Getting all car rim information");
            return carRimService.getAllCarRimLoad();
        }

        @PostMapping(path = "/add", produces = "application/json", consumes = "application/json")
        public ResponseDto addCarRim(@RequestBody CarRimLoadDto carRimLoadDto) {
            log.debug("Adding new car rim: {}", carRimLoadDto.getModel());
            try {
                carRimService.addCarRimLoad(carRimLoadDto);
                return new ResponseDto("Car rim added successfully", TransferStatus.SUCCESS);
            } catch (ItemAddException e) {
                log.error("Failed to add car rim: {}", e.getMessage());
                return new ResponseDto("Failed to add car rim", TransferStatus.FAILURE, e.getMessage());
            }
        }

        @PostMapping(path = "/add/all", produces = "application/json", consumes = "application/json")
        public ResponseDto addAllCarRims(@RequestBody List<CarRimLoadDto> carRimLoadDtos) {
            log.debug("Adding {} car rims", carRimLoadDtos.size());
            try {
                carRimService.addAllCarRims(carRimLoadDtos);
                return new ResponseDto("All car rims added successfully", TransferStatus.SUCCESS);
            } catch (ItemAddException e) {
                log.error("Failed to add all car rims: {}", e.getMessage());
                return new ResponseDto("Failed to add one or more car rims", TransferStatus.FAILURE, e.getMessage());
            }
        }
}
