package com.applicationdemo.carconfig.services;

import com.applicationdemo.carconfig.dto.web.ProductInfoDetailDto;
import com.applicationdemo.carconfig.dto.web.ProductInfoDto;
import com.applicationdemo.carconfig.entities.base.SpecialEquipment;
import com.applicationdemo.carconfig.mapper.web.CarColorMapper;
import com.applicationdemo.carconfig.mapper.web.CarEngineMapper;
import com.applicationdemo.carconfig.mapper.web.CarRimMapper;
import com.applicationdemo.carconfig.mapper.web.SpecialEquipmentMapper;
import com.applicationdemo.carconfig.services.base.CarColorService;
import com.applicationdemo.carconfig.services.base.CarEngineService;
import com.applicationdemo.carconfig.services.base.CarRimService;
import com.applicationdemo.carconfig.services.base.SpecialEquipmentService;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductInfoService {
    private final CarEngineService carEngineService;
    private final CarColorService carColorService;
    private final CarRimService carRimService;
    private final SpecialEquipmentService specialEquipmentService;

    private final CarEngineMapper carEnginemapper;
    private final CarColorMapper carColorMapper;
    private final CarRimMapper carRimMapper;
    private final SpecialEquipmentMapper specialEquipmentMapper;
    public ProductInfoDetailDto getProductDetailsByConfiguration(ProductInfoDto productInfoDto) {
        log.debug("Getting all product information based on criteria: {}", productInfoDto);

        var productDetails = new ProductInfoDetailDto();

        // Fetch and map Car Engine if product ID is provided
        if (Objects.nonNull(productInfoDto.getCarEngineProductId())) {
            log.debug("Fetching car engine with product ID: {}", productInfoDto.getCarEngineProductId());
            var engine = carEngineService.getCarEngineByProductId(productInfoDto.getCarEngineProductId());
            productDetails.setCarEngine(carEnginemapper.toDto(engine));
        }

        // Fetch and map Car Color if product ID is provided
        if (Objects.nonNull(productInfoDto.getCarColorProductId())) {
            log.debug("Fetching car color with product ID: {}", productInfoDto.getCarColorProductId());
            var color = carColorService.getColorByProductId(productInfoDto.getCarColorProductId());
            productDetails.setCarColor(carColorMapper.toDto(color));
        }

        // Fetch and map Car Rims if product ID is provided
        if (Objects.nonNull(productInfoDto.getCarRimsProductId())) {
            log.debug("Fetching car rims with product ID: {}", productInfoDto.getCarRimsProductId());
            var rims = carRimService.getCarRimByProductId(productInfoDto.getCarRimsProductId());
            productDetails.setCarRim(carRimMapper.toDto(rims));
        }

        // Fetch and map Special Equipment if product IDs are provided
        List<String> specialEquipmentIds = productInfoDto.getSpecialEquipmentProductIds();
        if (Objects.nonNull(specialEquipmentIds) && !specialEquipmentIds.isEmpty()) {
            log.debug("Fetching special equipment with product IDs: {}", specialEquipmentIds);
            var equipments = specialEquipmentIds.stream()
                    .filter(specialEquipmentId -> !StringUtils.isEmpty(specialEquipmentId) && !specialEquipmentId.equals("none")).map(specialEquipmentService::getSpecialEquipmentByProductId).collect(Collectors.toCollection(ArrayList::new));
            var equipmentDtos = equipments.stream()
                    .map(specialEquipmentMapper::toDto)
                    .toList();
            productDetails.setSpecialEquipment(equipmentDtos);
        } else {
            // Ensure the list is not null for the response DTO
            productDetails.setSpecialEquipment(Collections.emptyList());
        }

        log.info("Successfully aggregated product details for the given configuration.");
        return productDetails;
    }
}
