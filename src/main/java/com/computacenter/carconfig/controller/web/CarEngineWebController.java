package com.computacenter.carconfig.controller.web;

import com.computacenter.carconfig.dto.web.CarEngineDto;
import com.computacenter.carconfig.services.CarEngineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/web/car-engine")
public class CarEngineWebController {

    private final CarEngineService carEngineService;


    @GetMapping(path = "/all", produces = "application/json")
    public List<CarEngineDto> getAllCarEngines() {
        log.debug("Getting all car engine information");
        return carEngineService.getAllCarEnginesWeb();
    }

}
