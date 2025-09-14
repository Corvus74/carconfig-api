package com.applicationdemo.carconfig.services.base;

import com.applicationdemo.carconfig.dto.ResponseDto;
import com.applicationdemo.carconfig.dto.load.BaseConfigLoadDto;
import com.applicationdemo.carconfig.dto.web.BaseConfigDto;
import com.applicationdemo.carconfig.enums.TransferStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BaseConfigServiceTest {

    @Mock
    private CarColorService carColorService;
    @Mock
    private CarEngineService carEngineService;
    @Mock
    private CarRimService carRimService;
    @Mock
    private SpecialEquipmentService specialEquipmentService;

    @InjectMocks
    private BaseConfigService baseConfigService;

    @Test
    void getBaseConfiguration() {
        when(carColorService.getAllCarColorLoad()).thenReturn(Collections.emptyList());
        when(carEngineService.getAllCarEngineLoad()).thenReturn(Collections.emptyList());
        when(carRimService.getAllCarRimLoad()).thenReturn(Collections.emptyList());
        when(specialEquipmentService.getAllSpecialEquipmentsLoad()).thenReturn(Collections.emptyList());

        BaseConfigLoadDto result = baseConfigService.getBaseConfiguration();

        assertEquals(0, result.getCarColorLoadDtos().size());
        assertEquals(0, result.getCarEngineLoadDtos().size());
        assertEquals(0, result.getCarRimLoadDtos().size());
        assertEquals(0, result.getSpecialEquipmentLoadDtos().size());
    }

    @Test
    void getBaseConfigurationWeb() {
        when(carColorService.getAllCarColorsWeb()).thenReturn(Collections.emptyList());
        when(carEngineService.getAllCarEnginesWeb()).thenReturn(Collections.emptyList());
        when(carRimService.getAllCarRimsWeb()).thenReturn(Collections.emptyList());
        when(specialEquipmentService.getAllSpecialEquipmentsWeb()).thenReturn(Collections.emptyList());

        BaseConfigDto result = baseConfigService.getBaseConfigurationWeb();

        assertEquals(0, result.getCarColors().size());
        assertEquals(0, result.getCarEngines().size());
        assertEquals(0, result.getCarRims().size());
        assertEquals(0, result.getSpecialEquipment().size());
    }

    @Test
    void addBaseConfiguration() {
        BaseConfigLoadDto baseConfigLoadDto = new BaseConfigLoadDto();
        baseConfigLoadDto.setCarColorLoadDtos(Collections.emptyList());
        baseConfigLoadDto.setCarEngineLoadDtos(Collections.emptyList());
        baseConfigLoadDto.setCarRimLoadDtos(Collections.emptyList());
        baseConfigLoadDto.setSpecialEquipmentLoadDtos(Collections.emptyList());

        ResponseDto result = baseConfigService.addBaseConfiguration(baseConfigLoadDto);

        verify(carColorService).addAllCarColors(baseConfigLoadDto.getCarColorLoadDtos());
        verify(carEngineService).addAllCarEngines(baseConfigLoadDto.getCarEngineLoadDtos());
        verify(carRimService).addAllCarRims(baseConfigLoadDto.getCarRimLoadDtos());
        verify(specialEquipmentService).addAllSpecialEquipment(baseConfigLoadDto.getSpecialEquipmentLoadDtos());

        assertEquals(TransferStatus.SUCCESS, result.getStatus());
        assertEquals("All base configuration components added successfully", result.getText());
    }
}
