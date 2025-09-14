package com.applicationdemo.carconfig.mapper.base;

import com.applicationdemo.carconfig.dto.load.SpecialEquipmentLoadDto;
import com.applicationdemo.carconfig.entities.base.SpecialEquipment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
class SpecialEquipmentBaseMapperTest {

    @Autowired
    private SpecialEquipmentBaseMapper mapper;

    @Test
    void toEntity_mapsAllFields() {
        // Arrange
        SpecialEquipmentLoadDto dto = new SpecialEquipmentLoadDto();
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
        SpecialEquipmentLoadDto dto = mapper.toDto(entity);

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

        SpecialEquipmentLoadDto patchDto = new SpecialEquipmentLoadDto();
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
