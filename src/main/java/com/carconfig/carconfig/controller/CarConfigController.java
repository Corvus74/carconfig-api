package com.carconfig.carconfig.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CarConfigController {

    @GetMapping("/carconfig")
    public String carConfig() {
        return "CarConfig";
    }
}
