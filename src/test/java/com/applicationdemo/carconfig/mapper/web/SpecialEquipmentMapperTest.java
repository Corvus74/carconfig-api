package com.applicationdemo.carconfig.mapper.web;

import com.applicationdemo.carconfig.dto.web.SpecialEquipmentDto;
import com.applicationdemo.carconfig.domain.base.SpecialEquipment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
class SpecialEquipmentMapperTest {

    @Autowired
    private SpecialEquipmentMapper mapper;

    @Test
    void toEntity_mapsAllFields() {
        // Arrange
        SpecialEquipmentDto dto = new SpecialEquipmentDto();
        dto.setEquipmentName("Sunroof");
        dto.setProductId("P-SE01");

        // Act
        SpecialEquipment entity = mapper.toEntity(dto);

        // Assert
        assertThat(entity).isNotNull();
        assertThat(entity.getEquipmentName()).isEqualTo("Sunroof");
        assertThat(entity.getProductId()).isEqualTo("P-SE01");
    }

    @Test
    void toDto_mapsAllFields() {
        // Arrange
        SpecialEquipment entity = new SpecialEquipment();
        entity.setEquipmentName("Heated Seats");
        entity.setProductId("P-SE02");

        // Act
        SpecialEquipmentDto dto = mapper.toDto(entity);

        // Assert
        assertThat(dto).isNotNull();
        assertThat(dto.getEquipmentName()).isEqualTo("Heated Seats");
        assertThat(dto.getProductId()).isEqualTo("P-SE02");
    }

    @Test
    void partialUpdate_updatesOnlyNonNullFields() {
        // Arrange
        SpecialEquipment entity = new SpecialEquipment();
        entity.setEquipmentName("Original Name");
        entity.setPrice(800);

        SpecialEquipmentDto patchDto = new SpecialEquipmentDto();
        patchDto.setEquipmentName("Updated Name");
        patchDto.setPrice(null); // Should be ignored

        // Act
        mapper.partialUpdate(patchDto, entity);

        // Assert
        assertThat(entity.getEquipmentName()).isEqualTo("Updated Name");
        assertThat(entity.getPrice()).isEqualTo(800); // Unchanged
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
