package com.applicationdemo.carconfig.controller.base;

import com.applicationdemo.carconfig.dto.ResponseDto;
import com.applicationdemo.carconfig.dto.load.BaseConfigLoadDto;
import com.applicationdemo.carconfig.enums.TransferStatus;
import com.applicationdemo.carconfig.exceptions.ItemAddException;
import com.applicationdemo.carconfig.services.base.BaseConfigService;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Hidden
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/load/config")
public class ConfigBaseExternalController {

    private final BaseConfigService baseConfigService;

    @GetMapping(produces = "application/json")
    public BaseConfigLoadDto getBaseConfiguration() {
        log.info("Fetching all base configuration data from sub-controllers.");
        return baseConfigService.getBaseConfiguration();
    }

    @PostMapping(path = "/add", produces = "application/json", consumes = "application/json")
    public ResponseDto addBaseConfiguration(@RequestBody BaseConfigLoadDto baseConfigLoadDto) {
        log.info("Starting batch add for all base configuration components.");
        try {
            return baseConfigService.addBaseConfiguration(baseConfigLoadDto);
        } catch (ItemAddException e) {
            log.error("Failed to add all base configuration components: {}", e.getMessage());
            return new ResponseDto("Failed to add one or more base configuration components", TransferStatus.FAILURE, e.getMessage());
        } catch (Exception e) {
            log.error("An unexpected error occurred during base configuration import: {}", e.getMessage());
            return new ResponseDto("An unexpected error occurred", TransferStatus.FAILURE, e.getMessage());
        }
    }
}
