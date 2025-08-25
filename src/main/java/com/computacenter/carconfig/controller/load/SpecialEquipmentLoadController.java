package com.computacenter.carconfig.controller.load;

import com.computacenter.carconfig.dto.ResponseDto;
import com.computacenter.carconfig.dto.load.SpecialEquipmentLoadDto;
import com.computacenter.carconfig.enums.TransferStatus;
import com.computacenter.carconfig.exceptions.ItemAddException;
import com.computacenter.carconfig.services.pool.SpecialEquipmentService;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Hidden
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/load/specialEquipment")
public class SpecialEquipmentLoadController {

    private final SpecialEquipmentService specialEquipmentService;

    @GetMapping(path = "/all", produces = "application/json")
    public List<SpecialEquipmentLoadDto> getAllSpecialEquipment() {
        log.debug("Getting all special equipment information");
        return specialEquipmentService.getAllSpecialEquipmentsLoad();
    }

    @PostMapping(path = "/add", produces = "application/json", consumes = "application/json")
    public ResponseDto addSpecialEquipment(@RequestBody SpecialEquipmentLoadDto specialEquipmentLoadDto) {
        log.debug("Adding new special equipment: {}", specialEquipmentLoadDto.getEquipmentName());
        try {
            specialEquipmentService.addSpecialEquipment(specialEquipmentLoadDto);
            return new ResponseDto("Special equipment added successfully", TransferStatus.SUCCESS);
        } catch (ItemAddException e) {
            log.error("Failed to add special equipment: {}", e.getMessage());
            return new ResponseDto("Failed to add special equipment", TransferStatus.FAILURE, e.getMessage());
        }
    }

    @PostMapping(path = "/add/all", produces = "application/json", consumes = "application/json")
    public ResponseDto addAllSpecialEquipment(@RequestBody List<SpecialEquipmentLoadDto> specialEquipmentDtoList) {
        log.debug("Adding {} special equipment items", specialEquipmentDtoList.size());
        try {
            specialEquipmentService.addAllSpecialEquipment(specialEquipmentDtoList);
            return new ResponseDto("All special equipment added successfully", TransferStatus.SUCCESS);
        } catch (ItemAddException e) {
            log.error("Failed to add all special equipment: {}", e.getMessage());
            return new ResponseDto("Failed to add one or more special equipment items", TransferStatus.FAILURE, e.getMessage());
        }
    }
}

