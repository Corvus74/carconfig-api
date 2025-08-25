package com.computacenter.carconfig.services.pool;

import com.computacenter.carconfig.dto.ResponseDto;
import com.computacenter.carconfig.dto.load.BaseConfigLoadDto;
import com.computacenter.carconfig.dto.web.BaseConfigDto;
import com.computacenter.carconfig.enums.TransferStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
@Service
@RequiredArgsConstructor
public class BaseConfigService {
    private final CarColorService carColorService;
    private final CarEngineService carEngineService;
    private final CarRimService carRimService;
    private final SpecialEquipmentService specialEquipmentService;

    public BaseConfigLoadDto getBaseConfiguration() {
        BaseConfigLoadDto configDto = new BaseConfigLoadDto();
        configDto.setCarColorLoadDtos(carColorService.getAllCarColorLoad());
        configDto.setCarEngineLoadDtos(carEngineService.getAllCarEngineLoad());
        configDto.setCarRimLoadDtos(carRimService.getAllCarRimLoad());
        configDto.setSpecialEquipmentLoadDtos(specialEquipmentService.getAllSpecialEquipmentsLoad());

        return configDto;
    }
    public BaseConfigDto getBaseConfigurationWeb() {
        BaseConfigDto configDto = new BaseConfigDto();
        configDto.setCarColors(carColorService.getAllCarColorsWeb());
        configDto.setCarEngines(carEngineService.getAllCarEnginesWeb());
        configDto.setCarRims(carRimService.getAllCarRimsWeb());
        configDto.setSpecialEquipment(specialEquipmentService.getAllSpecialEquipmentsWeb());

        return configDto;
    }
    public ResponseDto addBaseConfiguration(@RequestBody BaseConfigLoadDto baseConfigLoadDto) {
            // Call the batch add methods on each sub-controller
            carColorService.addAllCarColors(baseConfigLoadDto.getCarColorLoadDtos());
            carEngineService.addAllCarEngines(baseConfigLoadDto.getCarEngineLoadDtos());
            carRimService.addAllCarRims(baseConfigLoadDto.getCarRimLoadDtos());
            specialEquipmentService.addAllSpecialEquipment(baseConfigLoadDto.getSpecialEquipmentLoadDtos());
            log.info("Successfully added all base configuration components.");
            return new ResponseDto("All base configuration components added successfully", TransferStatus.SUCCESS);

    }
}
