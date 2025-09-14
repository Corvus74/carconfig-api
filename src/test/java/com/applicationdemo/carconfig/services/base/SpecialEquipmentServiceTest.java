package com.applicationdemo.carconfig.services.base;

import com.applicationdemo.carconfig.dto.load.SpecialEquipmentLoadDto;
import com.applicationdemo.carconfig.dto.web.SpecialEquipmentDto;
import com.applicationdemo.carconfig.entities.base.SpecialEquipment;
import com.applicationdemo.carconfig.exceptions.ItemAddException;
import com.applicationdemo.carconfig.exceptions.OrderException;
import com.applicationdemo.carconfig.mapper.base.SpecialEquipmentBaseMapper;
import com.applicationdemo.carconfig.mapper.web.SpecialEquipmentMapper;
import com.applicationdemo.carconfig.repository.pool.SpecialEquipmentRepository;
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
class SpecialEquipmentServiceTest {

    @Mock
    private SpecialEquipmentRepository specialEquipmentRepository;
    @Mock
    private SpecialEquipmentMapper specialEquipmentMapper;
    @Mock
    private SpecialEquipmentBaseMapper specialEquipmentBaseMapper;

    @InjectMocks
    private SpecialEquipmentService specialEquipmentService;

    @Test
    void getAllSpecialEquipmentsWeb() {
        when(specialEquipmentRepository.findAll()).thenReturn(List.of(new SpecialEquipment()));
        when(specialEquipmentMapper.toDto(any(SpecialEquipment.class))).thenReturn(new SpecialEquipmentDto());

        List<SpecialEquipmentDto> result = specialEquipmentService.getAllSpecialEquipmentsWeb();

        assertEquals(1, result.size());
        verify(specialEquipmentRepository).findAll();
        verify(specialEquipmentMapper).toDto(any(SpecialEquipment.class));
    }

    @Test
    void getAllSpecialEquipmentsLoad() {
        when(specialEquipmentRepository.findAll()).thenReturn(List.of(new SpecialEquipment()));
        when(specialEquipmentBaseMapper.toDto(any(SpecialEquipment.class))).thenReturn(new SpecialEquipmentLoadDto());

        List<SpecialEquipmentLoadDto> result = specialEquipmentService.getAllSpecialEquipmentsLoad();

        assertEquals(1, result.size());
        verify(specialEquipmentRepository).findAll();
        verify(specialEquipmentBaseMapper).toDto(any(SpecialEquipment.class));
    }

    @Test
    void addSpecialEquipment_success() {
        SpecialEquipmentLoadDto dto = new SpecialEquipmentLoadDto();
        dto.setOrderNumber("123");
        when(specialEquipmentRepository.findByOrderNumberAndNotDeleted("123")).thenReturn(Optional.empty());
        when(specialEquipmentBaseMapper.toEntity(dto)).thenReturn(new SpecialEquipment());

        assertDoesNotThrow(() -> specialEquipmentService.addSpecialEquipment(dto));
        verify(specialEquipmentRepository).save(any(SpecialEquipment.class));
    }

    @Test
    void addSpecialEquipment_alreadyExists() {
        SpecialEquipmentLoadDto dto = new SpecialEquipmentLoadDto();
        dto.setOrderNumber("123");
        when(specialEquipmentRepository.findByOrderNumberAndNotDeleted("123")).thenReturn(Optional.of(new SpecialEquipment()));

        assertThrows(ItemAddException.class, () -> specialEquipmentService.addSpecialEquipment(dto));
        verify(specialEquipmentRepository, never()).save(any(SpecialEquipment.class));
    }

    @Test
    void addAllSpecialEquipment() {
        SpecialEquipmentLoadDto dto1 = new SpecialEquipmentLoadDto();
        dto1.setOrderNumber("123"); // new
        SpecialEquipmentLoadDto dto2 = new SpecialEquipmentLoadDto();
        dto2.setOrderNumber("456"); // existing

        when(specialEquipmentRepository.findByOrderNumberAndNotDeleted("123")).thenReturn(Optional.empty());
        when(specialEquipmentRepository.findByOrderNumberAndNotDeleted("456")).thenReturn(Optional.of(new SpecialEquipment()));
        when(specialEquipmentBaseMapper.toEntity(dto1)).thenReturn(new SpecialEquipment());

        specialEquipmentService.addAllSpecialEquipment(List.of(dto1, dto2));

        verify(specialEquipmentRepository, times(1)).save(any(SpecialEquipment.class));
    }

    @Test
    void getSpecialEquipmentByProductId_success() {
        SpecialEquipment equipment = new SpecialEquipment();
        when(specialEquipmentRepository.findBySpecialEquipmentByProductIdAndNotDeleted("P1")).thenReturn(List.of(equipment));

        SpecialEquipment result = specialEquipmentService.getSpecialEquipmentByProductId("P1");

        assertEquals(equipment, result);
    }

    @Test
    void getSpecialEquipmentByProductId_notFound() {
        when(specialEquipmentRepository.findBySpecialEquipmentByProductIdAndNotDeleted("P1")).thenReturn(Collections.emptyList());

        assertThrows(OrderException.class, () -> specialEquipmentService.getSpecialEquipmentByProductId("P1"));
    }

    @Test
    void getSpecialEquipmentByProductId_multipleFound() {
        when(specialEquipmentRepository.findBySpecialEquipmentByProductIdAndNotDeleted("P1")).thenReturn(List.of(new SpecialEquipment(), new SpecialEquipment()));

        assertThrows(OrderException.class, () -> specialEquipmentService.getSpecialEquipmentByProductId("P1"));
    }
}
