package com.computacenter.carconfig.controller.web;

import com.computacenter.carconfig.dto.web.BaseConfigDto;
import com.computacenter.carconfig.services.base.BaseConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/web/config")
public class ConfigWebController {

    private final BaseConfigService baseConfigService;

    @GetMapping(path = "/init", produces = "application/json")
    public BaseConfigDto getBaseConfiguration() {
        log.info("Fetching all base configuration data from sub-controllers.");
        return baseConfigService.getBaseConfigurationWeb();
    }

}
