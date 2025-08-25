package com.computacenter.carconfig.controller.web;

import com.computacenter.carconfig.dto.web.CarRimDto;
import com.computacenter.carconfig.services.pool.CarRimService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/web/car-rims")
public class CarRimsWebController {

    private final CarRimService carRimService;


        @GetMapping(path = "/all", produces = "application/json")
        public List<CarRimDto> getAllCarRims() {
            log.debug("Getting all car rim information");
            return carRimService.getAllCarRimsWeb();
        }

}
