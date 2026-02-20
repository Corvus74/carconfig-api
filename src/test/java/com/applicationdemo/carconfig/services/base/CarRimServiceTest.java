package com.applicationdemo.carconfig.services.base;

import com.applicationdemo.carconfig.dto.load.CarRimLoadDto;
import com.applicationdemo.carconfig.dto.web.CarRimDto;
import com.applicationdemo.carconfig.domain.base.CarRim;
import com.applicationdemo.carconfig.exceptions.ItemAddException;
import com.applicationdemo.carconfig.exceptions.OrderException;
import com.applicationdemo.carconfig.mapper.base.CarRimBaseMapper;
import com.applicationdemo.carconfig.mapper.web.CarRimMapper;
import com.applicationdemo.carconfig.repositories.pool.CarRimRepository;
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
class CarRimServiceTest {

    @Mock
    private CarRimRepository carRimRepository;
    @Mock
    private CarRimMapper carRimMapper;
    @Mock
    private CarRimBaseMapper carRimBaseMapper;

    @InjectMocks
    private CarRimService carRimService;

    @Test
    void getAllCarRimsWeb() {
        when(carRimRepository.findAll()).thenReturn(List.of(new CarRim()));
        when(carRimMapper.toDto(any(CarRim.class))).thenReturn(new CarRimDto());

        List<CarRimDto> result = carRimService.getAllCarRimsWeb();

        assertEquals(1, result.size());
        verify(carRimRepository).findAll();
        verify(carRimMapper).toDto(any(CarRim.class));
    }

    @Test
    void getAllCarRimLoad() {
        when(carRimRepository.findAll()).thenReturn(List.of(new CarRim()));
        when(carRimBaseMapper.toDto(any(CarRim.class))).thenReturn(new CarRimLoadDto());

        List<CarRimLoadDto> result = carRimService.getAllCarRimLoad();

        assertEquals(1, result.size());
        verify(carRimRepository).findAll();
        verify(carRimBaseMapper).toDto(any(CarRim.class));
    }

    @Test
    void addCarRimLoad_success() {
        CarRimLoadDto dto = new CarRimLoadDto();
        dto.setModel("ModelX");
        when(carRimRepository.findByOrderNumberAndNotDeleted(dto.getModel())).thenReturn(Optional.empty());
        when(carRimBaseMapper.toEntity(dto)).thenReturn(new CarRim());

        assertDoesNotThrow(() -> carRimService.addCarRimLoad(dto));
        verify(carRimRepository).save(any(CarRim.class));
    }

    @Test
    void addCarRimLoad_alreadyExists() {
        CarRimLoadDto dto = new CarRimLoadDto();
        dto.setModel("ModelX");
        when(carRimRepository.findByOrderNumberAndNotDeleted(dto.getModel())).thenReturn(Optional.of(new CarRim()));

        assertThrows(ItemAddException.class, () -> carRimService.addCarRimLoad(dto));
        verify(carRimRepository, never()).save(any(CarRim.class));
    }

    @Test
    void addAllCarRims() {
        CarRimLoadDto dto1 = new CarRimLoadDto();
        dto1.setModel("ModelX"); // new
        CarRimLoadDto dto2 = new CarRimLoadDto();
        dto2.setModel("ModelY"); // existing

        when(carRimRepository.findByOrderNumberAndNotDeleted(dto1.getModel())).thenReturn(Optional.empty());
        when(carRimRepository.findByOrderNumberAndNotDeleted(dto2.getModel())).thenReturn(Optional.of(new CarRim()));
        when(carRimBaseMapper.toEntity(dto1)).thenReturn(new CarRim());

        carRimService.addAllCarRims(List.of(dto1, dto2));

        verify(carRimRepository, times(1)).save(any(CarRim.class));
    }

    @Test
    void getCarRimByProductId_success() {
        CarRim rim = new CarRim();
        when(carRimRepository.findByCarRimsByProductIdAndNotDeleted("P1")).thenReturn(List.of(rim));

        CarRim result = carRimService.getCarRimByProductId("P1");

        assertEquals(rim, result);
    }

    @Test
    void getCarRimByProductId_notFound() {
        when(carRimRepository.findByCarRimsByProductIdAndNotDeleted("P1")).thenReturn(Collections.emptyList());

        assertThrows(OrderException.class, () -> carRimService.getCarRimByProductId("P1"));
    }

    @Test
    void getCarRimByProductId_multipleFound() {
        when(carRimRepository.findByCarRimsByProductIdAndNotDeleted("P1")).thenReturn(List.of(new CarRim(), new CarRim()));

        assertThrows(OrderException.class, () -> carRimService.getCarRimByProductId("P1"));
    }
}
