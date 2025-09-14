package com.applicationdemo.carconfig.services;

import com.applicationdemo.carconfig.dto.web.*;
import com.applicationdemo.carconfig.entities.base.CarColor;
import com.applicationdemo.carconfig.entities.base.CarEngine;
import com.applicationdemo.carconfig.entities.base.CarRim;
import com.applicationdemo.carconfig.entities.base.SpecialEquipment;
import com.applicationdemo.carconfig.mapper.web.CarColorMapper;
import com.applicationdemo.carconfig.mapper.web.CarEngineMapper;
import com.applicationdemo.carconfig.mapper.web.CarRimMapper;
import com.applicationdemo.carconfig.mapper.web.SpecialEquipmentMapper;
import com.applicationdemo.carconfig.services.base.CarColorService;
import com.applicationdemo.carconfig.services.base.CarEngineService;
import com.applicationdemo.carconfig.services.base.CarRimService;
import com.applicationdemo.carconfig.services.base.SpecialEquipmentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductInfoServiceTest {

    @Mock
    private CarEngineService carEngineService;
    @Mock
    private CarColorService carColorService;
    @Mock
    private CarRimService carRimService;
    @Mock
    private SpecialEquipmentService specialEquipmentService;

    @Mock
    private CarEngineMapper carEngineMapper;
    @Mock
    private CarColorMapper carColorMapper;
    @Mock
    private CarRimMapper carRimMapper;
    @Mock
    private SpecialEquipmentMapper specialEquipmentMapper;

    @InjectMocks
    private ProductInfoService productInfoService;

    @Test
    void getProductDetailsByConfiguration_full() {
        // Corrected the order of arguments to match the ProductInfoDto constructor
        ProductInfoDto productInfoDto = new ProductInfoDto("E1", "R1", "C1", List.of("SE1"));

        // Mocks for entities
        CarEngine engine = new CarEngine();
        CarColor color = new CarColor();
        CarRim rim = new CarRim();
        SpecialEquipment equipment = new SpecialEquipment();

        // Mocks for DTOs
        CarEngineDto engineDto = new CarEngineDto();
        CarColorDto colorDto = new CarColorDto();
        CarRimDto rimDto = new CarRimDto();
        SpecialEquipmentDto equipmentDto = new SpecialEquipmentDto();

        // Stubbing service calls
        when(carEngineService.getCarEngineByProductId("E1")).thenReturn(engine);
        when(carColorService.getColorByProductId("C1")).thenReturn(color);
        when(carRimService.getCarRimByProductId("R1")).thenReturn(rim);
        when(specialEquipmentService.getSpecialEquipmentByProductId("SE1")).thenReturn(equipment);

        // Stubbing mapper calls
        when(carEngineMapper.toDto(engine)).thenReturn(engineDto);
        when(carColorMapper.toDto(color)).thenReturn(colorDto);
        when(carRimMapper.toDto(rim)).thenReturn(rimDto);
        when(specialEquipmentMapper.toDto(equipment)).thenReturn(equipmentDto);

        ProductInfoDetailDto result = productInfoService.getProductDetailsByConfiguration(productInfoDto);

        assertNotNull(result);
        assertEquals(engineDto, result.getCarEngine());
        assertEquals(colorDto, result.getCarColor());
        assertEquals(rimDto, result.getCarRim());
        assertEquals(1, result.getSpecialEquipment().size());
        assertEquals(equipmentDto, result.getSpecialEquipment().get(0));
    }

    @Test
    void getProductDetailsByConfiguration_emptyIds() {
        ProductInfoDto productInfoDto = new ProductInfoDto(null, null, null, Collections.emptyList());

        ProductInfoDetailDto result = productInfoService.getProductDetailsByConfiguration(productInfoDto);

        assertNotNull(result);
        assertNull(result.getCarEngine());
        assertNull(result.getCarColor());
        assertNull(result.getCarRim());
        assertTrue(result.getSpecialEquipment().isEmpty());

        // Verify no services were called
        verifyNoInteractions(carEngineService, carColorService, carRimService, specialEquipmentService);
    }

    @Test
    void getProductDetailsByConfiguration_specialEquipmentFiltering() {
        ProductInfoDto productInfoDto = new ProductInfoDto(null, null, null, List.of("SE1", "", "none", "", "SE2"));

        SpecialEquipment equipment1 = new SpecialEquipment();
        SpecialEquipment equipment2 = new SpecialEquipment();
        SpecialEquipmentDto equipmentDto1 = new SpecialEquipmentDto();
        SpecialEquipmentDto equipmentDto2 = new SpecialEquipmentDto();

        when(specialEquipmentService.getSpecialEquipmentByProductId("SE1")).thenReturn(equipment1);
        when(specialEquipmentService.getSpecialEquipmentByProductId("SE2")).thenReturn(equipment2);
        when(specialEquipmentMapper.toDto(equipment1)).thenReturn(equipmentDto1);
        when(specialEquipmentMapper.toDto(equipment2)).thenReturn(equipmentDto2);

        ProductInfoDetailDto result = productInfoService.getProductDetailsByConfiguration(productInfoDto);

        assertNotNull(result);
        assertEquals(2, result.getSpecialEquipment().size());
        assertTrue(result.getSpecialEquipment().containsAll(List.of(equipmentDto1, equipmentDto2)));

        // Verify service was called only for valid IDs
        verify(specialEquipmentService, times(1)).getSpecialEquipmentByProductId("SE1");
        verify(specialEquipmentService, times(1)).getSpecialEquipmentByProductId("SE2");
        verify(specialEquipmentService, never()).getSpecialEquipmentByProductId("");
        verify(specialEquipmentService, never()).getSpecialEquipmentByProductId("none");
        verify(specialEquipmentService, never()).getSpecialEquipmentByProductId(null);
    }
}
