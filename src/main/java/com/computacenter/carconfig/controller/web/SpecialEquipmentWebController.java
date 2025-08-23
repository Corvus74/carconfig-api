package com.computacenter.carconfig.controller.web;

import com.computacenter.carconfig.dto.web.SpecialEquipmentDto;
import com.computacenter.carconfig.mapper.SpecialEquipmentMapper;
import com.computacenter.carconfig.services.SpecialEquipmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/web/specialEquipment")
public class SpecialEquipmentWebController {

    private final SpecialEquipmentService specialEquipmentService;

    @GetMapping(path = "/all", produces = "application/json")
    public List<SpecialEquipmentDto> getAllSpecialEquipment() {
        log.debug("Getting all special equipment information");
        return specialEquipmentService.getAllSpecialEquipmentsWeb();
    }

}

