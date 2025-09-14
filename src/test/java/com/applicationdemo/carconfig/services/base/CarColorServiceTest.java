package com.applicationdemo.carconfig.services.base;

import com.applicationdemo.carconfig.dto.load.CarColorLoadDto;
import com.applicationdemo.carconfig.dto.web.CarColorDto;
import com.applicationdemo.carconfig.entities.base.CarColor;
import com.applicationdemo.carconfig.exceptions.ItemAddException;
import com.applicationdemo.carconfig.exceptions.OrderException;
import com.applicationdemo.carconfig.mapper.base.CarColorBaseMapper;
import com.applicationdemo.carconfig.mapper.web.CarColorMapper;
import com.applicationdemo.carconfig.repository.pool.CarColorRepository;
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
class CarColorServiceTest {

    @Mock
    private CarColorRepository carColorRepository;
    @Mock
    private CarColorMapper carColorMapper;
    @Mock
    private CarColorBaseMapper carColorBaseMapper;

    @InjectMocks
    private CarColorService carColorService;

    @Test
    void getAllCarColorLoad() {
        when(carColorRepository.findAll()).thenReturn(List.of(new CarColor()));
        when(carColorBaseMapper.toDto(any(CarColor.class))).thenReturn(new CarColorLoadDto());

        List<CarColorLoadDto> result = carColorService.getAllCarColorLoad();

        assertEquals(1, result.size());
        verify(carColorRepository).findAll();
        verify(carColorBaseMapper).toDto(any(CarColor.class));
    }

    @Test
    void getAllCarColorsWeb() {
        when(carColorRepository.findAll()).thenReturn(List.of(new CarColor()));
        when(carColorMapper.toDto(any(CarColor.class))).thenReturn(new CarColorDto());

        List<CarColorDto> result = carColorService.getAllCarColorsWeb();

        assertEquals(1, result.size());
        verify(carColorRepository).findAll();
        verify(carColorMapper).toDto(any(CarColor.class));
    }

    @Test
    void addCarColor_success() {
        CarColorLoadDto dto = new CarColorLoadDto();
        dto.setOrderNumber("123");
        when(carColorRepository.findByOrderNumberAndNotDeleted("123")).thenReturn(Optional.empty());
        when(carColorBaseMapper.toEntity(dto)).thenReturn(new CarColor());

        assertDoesNotThrow(() -> carColorService.addCarColor(dto));
        verify(carColorRepository).save(any(CarColor.class));
    }

    @Test
    void addCarColor_alreadyExists() {
        CarColorLoadDto dto = new CarColorLoadDto();
        dto.setOrderNumber("123");
        when(carColorRepository.findByOrderNumberAndNotDeleted("123")).thenReturn(Optional.of(new CarColor()));

        assertThrows(ItemAddException.class, () -> carColorService.addCarColor(dto));
        verify(carColorRepository, never()).save(any(CarColor.class));
    }

    @Test
    void addAllCarColors() {
        CarColorLoadDto dto1 = new CarColorLoadDto();
        dto1.setOrderNumber("123"); // new
        CarColorLoadDto dto2 = new CarColorLoadDto();
        dto2.setOrderNumber("456"); // existing

        when(carColorRepository.findByOrderNumberAndNotDeleted("123")).thenReturn(Optional.empty());
        when(carColorRepository.findByOrderNumberAndNotDeleted("456")).thenReturn(Optional.of(new CarColor()));
        when(carColorBaseMapper.toEntity(dto1)).thenReturn(new CarColor());

        carColorService.addAllCarColors(List.of(dto1, dto2));

        verify(carColorRepository, times(1)).save(any(CarColor.class));
    }

    @Test
    void getColorByProductId_success() {
        CarColor color = new CarColor();
        when(carColorRepository.findByCarColorByProductIdAndNotDeleted("P1")).thenReturn(List.of(color));

        CarColor result = carColorService.getColorByProductId("P1");

        assertEquals(color, result);
    }

    @Test
    void getColorByProductId_notFound() {
        when(carColorRepository.findByCarColorByProductIdAndNotDeleted("P1")).thenReturn(Collections.emptyList());

        assertThrows(OrderException.class, () -> carColorService.getColorByProductId("P1"));
    }

    @Test
    void getColorByProductId_multipleFound() {
        when(carColorRepository.findByCarColorByProductIdAndNotDeleted("P1")).thenReturn(List.of(new CarColor(), new CarColor()));

        assertThrows(OrderException.class, () -> carColorService.getColorByProductId("P1"));
    }
}
