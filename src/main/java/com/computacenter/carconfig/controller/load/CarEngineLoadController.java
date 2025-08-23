package com.computacenter.carconfig.controller.load;

import com.computacenter.carconfig.dto.ResponseDto;
import com.computacenter.carconfig.dto.load.CarEngineLoadDto;
import com.computacenter.carconfig.enums.TransferStatus;
import com.computacenter.carconfig.exceptions.ItemAddException;
import com.computacenter.carconfig.services.CarEngineService;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Hidden
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/load/car-engine")
public class CarEngineLoadController {

    private final CarEngineService carEngineService;


    @GetMapping(path = "/all", produces = "application/json")
    public List<CarEngineLoadDto> getAllCarEngines() {
        log.debug("Getting all car engine information");
        return carEngineService.getAllCarEngineLoad();
    }

    @PostMapping(path = "/add", produces = "application/json", consumes = "application/json")
    public ResponseDto addCarEngine(@RequestBody CarEngineLoadDto carEngineLoadDto) {
        log.debug("Adding new car engine: {}", carEngineLoadDto.getModel());
        try {

            carEngineService.addCarEngine(carEngineLoadDto);
            return new ResponseDto("Car engine added successfully", TransferStatus.SUCCESS);
        } catch (ItemAddException e) {
            log.error("Failed to add car engine: {}", e.getMessage());
            return new ResponseDto("Failed to add car engine", TransferStatus.FAILURE, e.getMessage());
        }
    }

    @PostMapping(path = "/add/all", produces = "application/json", consumes = "application/json")
    public ResponseDto addAllCarEngines(@RequestBody List<CarEngineLoadDto> carEnginesDtoList) {
        log.debug("Adding {} car engines", carEnginesDtoList.size());
        try {
            carEngineService.addAllCarEngines(carEnginesDtoList);
            return new ResponseDto("All car engines added successfully", TransferStatus.SUCCESS);
        } catch (ItemAddException e) {
            log.error("Failed to add all car engines: {}", e.getMessage());
            return new ResponseDto("Failed to add one or more car engines", TransferStatus.FAILURE, e.getMessage());
        }
    }


}
