package com.applicationdemo.carconfig.mapper.order;

import com.applicationdemo.carconfig.dto.order.SpecialEquipmentOrderDto;
import com.applicationdemo.carconfig.dto.web.SpecialEquipmentDto;
import com.applicationdemo.carconfig.domain.base.SpecialEquipment;
import com.applicationdemo.carconfig.domain.order.SpecialEquipmentOrder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
class SpecialEquipmentOrderMapperTest {

    @Autowired
    private SpecialEquipmentOrderMapper mapper;

    @Test
    void toEntity_mapsAllFields() {
        // Arrange
        SpecialEquipmentOrderDto dto = new SpecialEquipmentOrderDto();
        dto.setSpecialEquipmentOrderId("seo-1");
        SpecialEquipmentDto seDto = new SpecialEquipmentDto();
        seDto.setEquipmentName("Sunroof");
        dto.setSpecialEquipment(seDto);

        // Act
        SpecialEquipmentOrder entity = mapper.toEntity(dto);

        // Assert
        assertThat(entity).isNotNull();
        assertThat(entity.getSpecialEquipmentOrderId()).isEqualTo("seo-1");
        assertThat(entity.getSpecialEquipment()).isNotNull();
        assertThat(entity.getSpecialEquipment().getEquipmentName()).isEqualTo("Sunroof");
    }

    @Test
    void toDto_mapsAllFields() {
        // Arrange
        SpecialEquipmentOrder entity = new SpecialEquipmentOrder();
        entity.setSpecialEquipmentOrderId("seo-2");
        SpecialEquipment se = new SpecialEquipment();
        se.setEquipmentName("Heated Seats");
        entity.setSpecialEquipment(se);

        // Act
        SpecialEquipmentOrderDto dto = mapper.toDto(entity);

        // Assert
        assertThat(dto).isNotNull();
        assertThat(dto.getSpecialEquipmentOrderId()).isEqualTo("seo-2");
        assertThat(dto.getSpecialEquipment()).isNotNull();
        assertThat(dto.getSpecialEquipment().getEquipmentName()).isEqualTo("Heated Seats");
    }

    @Test
    void partialUpdate_updatesOnlyNonNullFields() {
        // Arrange
        SpecialEquipmentOrder entity = new SpecialEquipmentOrder();
        entity.setSpecialEquipmentOrderId("Original-ID");
        SpecialEquipment originalSE = new SpecialEquipment();
        originalSE.setEquipmentName("Original Name");
        entity.setSpecialEquipment(originalSE);

        SpecialEquipmentOrderDto patchDto = new SpecialEquipmentOrderDto();
        SpecialEquipmentDto patchSEDto = new SpecialEquipmentDto();
        patchSEDto.setEquipmentName("Updated Name");
        patchDto.setSpecialEquipment(patchSEDto);
        patchDto.setSpecialEquipmentOrderId(null); // Should be ignored

        // Act
        mapper.partialUpdate(patchDto, entity);

        // Assert
        assertThat(entity.getSpecialEquipmentOrderId()).isEqualTo("Original-ID"); // Unchanged
        assertThat(entity.getSpecialEquipment().getEquipmentName()).isEqualTo("Updated Name"); // Updated
    }

    @Test
    void toDto_returnsNull_whenEntityIsNull() {
        assertThat(mapper.toDto(null)).isNull();
    }

    @Test
    void toEntity_returnsNull_whenDtoIsNull() {
        assertThat(mapper.toEntity(null)).isNull();
    }
}
