package com.applicationdemo.carconfig.mapper.order;

import com.applicationdemo.carconfig.dto.order.CarEngineOrderDto;
import com.applicationdemo.carconfig.dto.web.CarEngineDto;
import com.applicationdemo.carconfig.entities.base.CarEngine;
import com.applicationdemo.carconfig.entities.order.CarEngineOrder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
class CarEngineOrderMapperTest {

    @Autowired
    private CarEngineOrderMapper mapper;

    @Test
    void toEntity_mapsAllFields() {
        // Arrange
        CarEngineOrderDto dto = new CarEngineOrderDto();
        dto.setCarEngineOrderId("ceo-1");
        CarEngineDto engineDto = new CarEngineDto();
        engineDto.setModel("V8");
        dto.setCarEngine(engineDto);

        // Act
        CarEngineOrder entity = mapper.toEntity(dto);

        // Assert
        assertThat(entity).isNotNull();
        assertThat(entity.getCarEngineOrderId()).isEqualTo("ceo-1");
        assertThat(entity.getCarEngine()).isNotNull();
        assertThat(entity.getCarEngine().getModel()).isEqualTo("V8");
    }

    @Test
    void toDto_mapsAllFields() {
        // Arrange
        CarEngineOrder entity = new CarEngineOrder();
        entity.setCarEngineOrderId("ceo-2");
        CarEngine engine = new CarEngine();
        engine.setModel("V6");
        entity.setCarEngine(engine);

        // Act
        CarEngineOrderDto dto = mapper.toDto(entity);

        // Assert
        assertThat(dto).isNotNull();
        assertThat(dto.getCarEngineOrderId()).isEqualTo("ceo-2");
        assertThat(dto.getCarEngine()).isNotNull();
        assertThat(dto.getCarEngine().getModel()).isEqualTo("V6");
    }

    @Test
    void partialUpdate_updatesOnlyNonNullFields() {
        // Arrange
        CarEngineOrder entity = new CarEngineOrder();
        entity.setCarEngineOrderId("Original-ID");
        CarEngine originalEngine = new CarEngine();
        originalEngine.setModel("Original Model");
        entity.setCarEngine(originalEngine);

        CarEngineOrderDto patchDto = new CarEngineOrderDto();
        CarEngineDto patchEngineDto = new CarEngineDto();
        patchEngineDto.setModel("Updated Model");
        patchDto.setCarEngine(patchEngineDto);
        patchDto.setCarEngineOrderId(null); // Should be ignored

        // Act
        mapper.partialUpdate(patchDto, entity);

        // Assert
        assertThat(entity.getCarEngineOrderId()).isEqualTo("Original-ID"); // Unchanged
        assertThat(entity.getCarEngine().getModel()).isEqualTo("Updated Model"); // Updated
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
