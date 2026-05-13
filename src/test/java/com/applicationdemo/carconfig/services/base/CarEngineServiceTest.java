package com.applicationdemo.carconfig.services.base;

import com.applicationdemo.carconfig.dto.load.CarEngineLoadDto;
import com.applicationdemo.carconfig.dto.web.CarEngineDto;
import com.applicationdemo.carconfig.domain.base.CarEngine;
import com.applicationdemo.carconfig.exceptions.ItemAddException;
import com.applicationdemo.carconfig.exceptions.OrderException;
import com.applicationdemo.carconfig.mapper.base.CarEngineBaseMapper;
import com.applicationdemo.carconfig.mapper.web.CarEngineMapper;
import com.applicationdemo.carconfig.repositories.pool.CarEngineRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarEngineServiceTest {

    @Mock
    private CarEngineRepository carEngineRepository;
    @Mock
    private CarEngineMapper carEngineMapper;
    @Mock
    private CarEngineBaseMapper carEngineBaseMapper;

    @InjectMocks
    private CarEngineService carEngineService;

    @Test
    void getAllCarEnginesWeb() {
        when(carEngineRepository.findAll()).thenReturn(List.of(new CarEngine()));
        when(carEngineMapper.toDto(any(CarEngine.class))).thenReturn(new CarEngineDto());

        List<CarEngineDto> result = carEngineService.getAllCarEnginesWeb();

        assertEquals(1, result.size());
        verify(carEngineRepository).findAll();
        verify(carEngineMapper).toDto(any(CarEngine.class));
    }

    @Test
    void getAllCarEngineLoad() {
        when(carEngineRepository.findAll()).thenReturn(List.of(new CarEngine()));
        when(carEngineBaseMapper.toDto(any(CarEngine.class))).thenReturn(new CarEngineLoadDto());

        List<CarEngineLoadDto> result = carEngineService.getAllCarEngineLoad();

        assertEquals(1, result.size());
        verify(carEngineRepository).findAll();
        verify(carEngineBaseMapper).toDto(any(CarEngine.class));
    }

    @Test
    void addCarEngine_success() {
        CarEngineLoadDto dto = new CarEngineLoadDto();
        dto.setOrderNumber("123");
        when(carEngineRepository.findByOrderNumber("123")).thenReturn(Optional.empty());
        when(carEngineBaseMapper.toEntity(dto)).thenReturn(new CarEngine());

        assertDoesNotThrow(() -> carEngineService.addCarEngine(dto));
        verify(carEngineRepository).save(any(CarEngine.class));
    }

    @Test
    void addCarEngine_alreadyExists() {
        CarEngineLoadDto dto = new CarEngineLoadDto();
        dto.setOrderNumber("123");
        when(carEngineRepository.findByOrderNumber("123")).thenReturn(Optional.of(new CarEngine()));

        assertThrows(ItemAddException.class, () -> carEngineService.addCarEngine(dto));
        verify(carEngineRepository, never()).save(any(CarEngine.class));
    }

    @Test
    void addAllCarEngines() {
        CarEngineLoadDto dto1 = new CarEngineLoadDto();
        dto1.setOrderNumber("123"); // new
        CarEngineLoadDto dto2 = new CarEngineLoadDto();
        dto2.setOrderNumber("456"); // existing

        when(carEngineRepository.findByOrderNumber("123")).thenReturn(Optional.empty());
        when(carEngineRepository.findByOrderNumber("456")).thenReturn(Optional.of(new CarEngine()));
        when(carEngineBaseMapper.toEntity(dto1)).thenReturn(new CarEngine());

        carEngineService.addAllCarEngines(List.of(dto1, dto2));

        verify(carEngineRepository, times(1)).save(any(CarEngine.class));
    }

    @Test
    void getCarEngineByProductId_success() {
        CarEngine engine = new CarEngine();
        when(carEngineRepository.findByProductId("P1")).thenReturn(List.of(engine));

        CarEngine result = carEngineService.getCarEngineByProductId("P1");

        assertEquals(engine, result);
    }

    @Test
    void getCarEngineByProductId_notFound() {
        when(carEngineRepository.findByProductId("P1")).thenReturn(Collections.emptyList());

        assertThrows(OrderException.class, () -> carEngineService.getCarEngineByProductId("P1"));
    }

    @Test
    void getCarEngineByProductId_multipleFound() {
        when(carEngineRepository.findByProductId("P1")).thenReturn(List.of(new CarEngine(), new CarEngine()));

        assertThrows(OrderException.class, () -> carEngineService.getCarEngineByProductId("P1"));
    }
}
