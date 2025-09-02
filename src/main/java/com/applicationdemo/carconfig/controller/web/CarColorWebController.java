package com.applicationdemo.carconfig.controller.web;

import com.applicationdemo.carconfig.dto.web.CarColorDto;
import com.applicationdemo.carconfig.services.base.CarColorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/web/car-color")
public class CarColorWebController {

    private final CarColorService carColorService;

    @GetMapping(path = "/all", produces = "application/json")
    public List<CarColorDto> getAllCarColors() {
        log.debug("Getting all car color information");
        return carColorService.getAllCarColorsWeb();
    }
}
