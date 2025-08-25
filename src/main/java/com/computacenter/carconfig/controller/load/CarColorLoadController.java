package com.computacenter.carconfig.controller.load;

import com.computacenter.carconfig.dto.ResponseDto;
import com.computacenter.carconfig.dto.load.CarColorLoadDto;
import com.computacenter.carconfig.enums.TransferStatus;
import com.computacenter.carconfig.exceptions.ItemAddException;

import com.computacenter.carconfig.services.pool.CarColorService;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Hidden
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/load/car-color")
public class CarColorLoadController {

    private final CarColorService carColorService;

    @GetMapping(path = "/all", produces = "application/json")
    public List<CarColorLoadDto> getAllCarColorsLoad() {
        log.debug("Getting all car color information");
        return carColorService.getAllCarColorLoad();
    }

    @PostMapping(path = "/add", produces = "application/json", consumes = "application/json")
    public ResponseDto addCarColor(@RequestBody CarColorLoadDto carColorLoadDto) {
        log.debug("Adding new car color: {}", carColorLoadDto.getColorName());
        try {
            carColorService.addCarColor(carColorLoadDto);
            return new ResponseDto("Car color added successfully", TransferStatus.SUCCESS);
        } catch (ItemAddException e) {
            log.error("Failed to add car color: {}", e.getMessage());
            return new ResponseDto("Failed to add car color", TransferStatus.FAILURE, e.getMessage());
        }
    }

    @PostMapping(path = "/add/all", produces = "application/json", consumes = "application/json")
    public ResponseDto addAllCarColors(@RequestBody List<CarColorLoadDto> carColorLoadDtoList) {
        log.debug("Adding {} car colors", carColorLoadDtoList.size());
        try {
            carColorService.addAllCarColors(carColorLoadDtoList);
            return new ResponseDto("All car colors added successfully", TransferStatus.SUCCESS);
        } catch (ItemAddException e) {
            log.error("Failed to add all car colors: {}", e.getMessage());
            return new ResponseDto("Failed to add one or more car colors", TransferStatus.FAILURE, e.getMessage());
        }
    }

}
