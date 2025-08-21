package com.computacenter.carconfig.controller;

import com.computacenter.carconfig.dto.ResponseDto;
import com.computacenter.carconfig.dto.base.SpecialEquipmentDto;
import com.computacenter.carconfig.enums.TransferStatus;
import com.computacenter.carconfig.exceptions.ItemAddException;
import com.computacenter.carconfig.mapper.SpecialEquipmentMapper;
import com.computacenter.carconfig.services.SpecialEquipmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/specialEquipment")
public class SpecialEquipmentController {

    private final SpecialEquipmentService specialEquipmentService;
    private final SpecialEquipmentMapper specialEquipmentMapper;

    @GetMapping(path = "/all", produces = "application/json")
    public List<SpecialEquipmentDto> getAllSpecialEquipment() {
        log.debug("Getting all special equipment information");
        return specialEquipmentService.getAllSpecialEquipments().stream().map(specialEquipmentMapper::toDto).toList();
    }

    @PostMapping(path = "/add", produces = "application/json", consumes = "application/json")
    public ResponseDto addSpecialEquipment(@RequestBody SpecialEquipmentDto specialEquipmentDto) {
        log.debug("Adding new special equipment: {}", specialEquipmentDto.getName());
        try {
            specialEquipmentService.addSpecialEquipment(specialEquipmentMapper.toEntity(specialEquipmentDto));
            return new ResponseDto("Special equipment added successfully", TransferStatus.SUCCESS);
        } catch (ItemAddException e) {
            log.error("Failed to add special equipment: {}", e.getMessage());
            return new ResponseDto("Failed to add special equipment", TransferStatus.FAILURE, e.getMessage());
        }
    }

    @PostMapping(path = "/add/all", produces = "application/json", consumes = "application/json")
    public ResponseDto addAllSpecialEquipment(@RequestBody List<SpecialEquipmentDto> specialEquipmentDtoList) {
        log.debug("Adding {} special equipment items", specialEquipmentDtoList.size());
        try {
            specialEquipmentService.addAllSpecialEquipment(specialEquipmentDtoList.stream().map(specialEquipmentMapper::toEntity).toList());
            return new ResponseDto("All special equipment added successfully", TransferStatus.SUCCESS);
        } catch (ItemAddException e) {
            log.error("Failed to add all special equipment: {}", e.getMessage());
            return new ResponseDto("Failed to add one or more special equipment items", TransferStatus.FAILURE, e.getMessage());
        }
    }
}

