package com.applicationdemo.carconfig.mapper.order;

import com.applicationdemo.carconfig.dto.order.CarColorOrderDto;
import com.applicationdemo.carconfig.dto.web.CarColorDto;
import com.applicationdemo.carconfig.entities.base.CarColor;
import com.applicationdemo.carconfig.entities.order.CarColorOrder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
class CarColorOrderMapperTest {

    @Autowired
    private CarColorOrderMapper mapper;

    @Test
    void toEntity_mapsAllFields() {
        // Arrange
        CarColorOrderDto dto = new CarColorOrderDto();
        dto.setCarColorOrderId("cco-1");
        CarColorDto colorDto = new CarColorDto();
        colorDto.setColorName("Red");
        dto.setCarColor(colorDto);

        // Act
        CarColorOrder entity = mapper.toEntity(dto);

        // Assert
        assertThat(entity).isNotNull();
        assertThat(entity.getCarColorOrderId()).isEqualTo("cco-1");
        assertThat(entity.getCarColor()).isNotNull();
        assertThat(entity.getCarColor().getColorName()).isEqualTo("Red");
    }

    @Test
    void toDto_mapsAllFields() {
        // Arrange
        CarColorOrder entity = new CarColorOrder();
        entity.setCarColorOrderId("cco-2");
        CarColor color = new CarColor();
        color.setColorName("Blue");
        entity.setCarColor(color);

        // Act
        CarColorOrderDto dto = mapper.toDto(entity);

        // Assert
        assertThat(dto).isNotNull();
        assertThat(dto.getCarColorOrderId()).isEqualTo("cco-2");
        assertThat(dto.getCarColor()).isNotNull();
        assertThat(dto.getCarColor().getColorName()).isEqualTo("Blue");
    }

    @Test
    void partialUpdate_updatesOnlyNonNullFields() {
        // Arrange
        CarColorOrder entity = new CarColorOrder();
        entity.setCarColorOrderId("Original-ID");
        CarColor originalColor = new CarColor();
        originalColor.setColorName("Original Color");
        entity.setCarColor(originalColor);

        CarColorOrderDto patchDto = new CarColorOrderDto();
        CarColorDto patchColorDto = new CarColorDto();
        patchColorDto.setColorName("Updated Color");
        patchDto.setCarColor(patchColorDto);
        patchDto.setCarColorOrderId(null); // Should be ignored

        // Act
        mapper.partialUpdate(patchDto, entity);

        // Assert
        assertThat(entity.getCarColorOrderId()).isEqualTo("Original-ID"); // Unchanged
        assertThat(entity.getCarColor().getColorName()).isEqualTo("Updated Color"); // Updated
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
