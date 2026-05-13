package com.applicationdemo.carconfig.mapper;

import com.applicationdemo.carconfig.domain.order.*;
import com.applicationdemo.carconfig.dto.OrderUserDto;
import com.applicationdemo.carconfig.dto.order.*;
import com.applicationdemo.carconfig.dto.web.CarColorDto;
import com.applicationdemo.carconfig.dto.web.CarEngineDto;
import com.applicationdemo.carconfig.dto.web.CarRimDto;
import com.applicationdemo.carconfig.dto.web.SpecialEquipmentDto;
import com.applicationdemo.carconfig.domain.user.OrderUser;
import com.applicationdemo.carconfig.domain.base.CarColor;
import com.applicationdemo.carconfig.domain.base.CarEngine;
import com.applicationdemo.carconfig.domain.base.CarRim;
import com.applicationdemo.carconfig.domain.base.SpecialEquipment;
import com.applicationdemo.carconfig.enums.OrderStatusEnum;
import com.applicationdemo.carconfig.mapper.order.CarOrderMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
class CarOrderMapperTest {

    @Autowired
    private CarOrderMapper orderMapper;

    @Test
    void toDto_mapsAllFieldsCorrectly() {
        // Arrange
        CarOrder entity = new CarOrder();
        entity.setCarOrderId("ORD-123");
        entity.setDescription("Test Description");
        entity.setTotalPrice(50000);

        OrderUser user = new OrderUser();
        user.setUserName("testuser");
        entity.setOrderUser(user);

        CarColorOrder colorOrder = new CarColorOrder();
        CarColor color = new CarColor();
        color.setColorName("Red");
        colorOrder.setCarColor(color);
        entity.setCarColorOrder(colorOrder);

        CarEngineOrder engineOrder = new CarEngineOrder();
        CarEngine engine = new CarEngine();
        engine.setModel("V8");
        engineOrder.setCarEngine(engine);
        entity.setCarEngineOrder(engineOrder);

        CarRimOrder rimOrder = new CarRimOrder();
        CarRim rim = new CarRim();
        rim.setRimName("Sport");
        rimOrder.setCarRim(rim);
        entity.setCarRimOrder(rimOrder);

        SpecialEquipmentOrder seOrder = new SpecialEquipmentOrder();
        SpecialEquipment se = new SpecialEquipment();
        se.setEquipmentName("Sunroof");
        seOrder.setSpecialEquipment(se);
        entity.setSpecialEquipmentOrders(List.of(seOrder));

        OrderStatus status = new OrderStatus();
        status.setCurrentStatus(OrderStatusEnum.COMPLETED);
        entity.setOrderStatus(status);

        // Act
        CarOrderDto dto = orderMapper.toDto(entity);

        // Assert
        assertThat(dto).isNotNull();
        assertThat(dto.getCarOrderId()).isEqualTo("ORD-123");
        assertThat(dto.getDescription()).isEqualTo("Test Description");
        assertThat(dto.getTotalPrice()).isEqualTo(50000);
        assertThat(dto.getOrderUser()).isNotNull();
        assertThat(dto.getOrderUser().getUserName()).isEqualTo("testuser");
        assertThat(dto.getCarColorOrder()).isNotNull();
        assertThat(dto.getCarColorOrder().getCarColor().getColorName()).isEqualTo("Red");
        assertThat(dto.getCarEngineOrder()).isNotNull();
        assertThat(dto.getCarEngineOrder().getCarEngine().getModel()).isEqualTo("V8");
        assertThat(dto.getCarRimOrder()).isNotNull();
        assertThat(dto.getCarRimOrder().getCarRim().getRimName()).isEqualTo("Sport");
        assertThat(dto.getSpecialEquipmentOrders()).hasSize(1);
        assertThat(dto.getSpecialEquipmentOrders().get(0).getSpecialEquipment().getEquipmentName()).isEqualTo("Sunroof");
        assertThat(dto.getOrderStatus()).isNotNull();
        assertThat(dto.getOrderStatus().getCurrentStatus()).isEqualTo(OrderStatusEnum.COMPLETED);
    }

    @Test
    void toEntity_mapsAllFieldsCorrectly() {
        // Arrange
        CarOrderDto dto = new CarOrderDto();
        dto.setCarOrderId("ORD-456");
        dto.setOrderUser(new OrderUserDto("testuser", "test@email.com"));

        CarColorDto colorDto = new CarColorDto();
        colorDto.setColorName("Blue");
        CarColorOrderDto colorOrderDto = new CarColorOrderDto();
        colorOrderDto.setCarColor(colorDto);
        dto.setCarColorOrder(colorOrderDto);

        CarEngineDto engineDto = new CarEngineDto();
        engineDto.setModel("V6");
        CarEngineOrderDto engineOrderDto = new CarEngineOrderDto();
        engineOrderDto.setCarEngine(engineDto);
        dto.setCarEngineOrder(engineOrderDto);

        CarRimDto rimDto = new CarRimDto();
        rimDto.setRimName("Classic");
        CarRimOrderDto rimOrderDto = new CarRimOrderDto();
        rimOrderDto.setCarRim(rimDto);
        dto.setCarRimOrder(rimOrderDto);

        SpecialEquipmentDto seDto = new SpecialEquipmentDto();
        seDto.setEquipmentName("Spoiler");
        SpecialEquipmentOrderDto seOrderDto = new SpecialEquipmentOrderDto();
        seOrderDto.setSpecialEquipment(seDto);
        dto.setSpecialEquipmentOrders(List.of(seOrderDto));

        CarOrderStatusDto statusDto = new CarOrderStatusDto();
        statusDto.setCurrentStatus(OrderStatusEnum.PENDING);
        dto.setOrderStatus(statusDto);

        // Act
        CarOrder entity = orderMapper.toEntity(dto);

        // Assert
        assertThat(entity).isNotNull();
        assertThat(entity.getCarOrderId()).isEqualTo("ORD-456");
        assertThat(entity.getOrderUser()).isNotNull();
        assertThat(entity.getOrderUser().getUserName()).isEqualTo("testuser");
        assertThat(entity.getCarColorOrder()).isNotNull();
        assertThat(entity.getCarColorOrder().getCarColor().getColorName()).isEqualTo("Blue");
        assertThat(entity.getCarEngineOrder()).isNotNull();
        assertThat(entity.getCarEngineOrder().getCarEngine().getModel()).isEqualTo("V6");
        assertThat(entity.getCarRimOrder()).isNotNull();
        assertThat(entity.getCarRimOrder().getCarRim().getRimName()).isEqualTo("Classic");
        assertThat(entity.getSpecialEquipmentOrders()).hasSize(1);
        assertThat(entity.getSpecialEquipmentOrders().get(0).getSpecialEquipment().getEquipmentName()).isEqualTo("Spoiler");
        assertThat(entity.getOrderStatus()).isNotNull();
        assertThat(entity.getOrderStatus().getCurrentStatus()).isEqualTo(OrderStatusEnum.PENDING);
    }

    @Test
    void partialUpdate_updatesAllPaths() {
        // Arrange: Create a fully populated original entity
        CarOrder entity = new CarOrder();
        entity.setCarOrderId("ORD-789");
        entity.setDescription("Original Description");
        entity.setTotalPrice(1000);

        OrderUser user = new OrderUser();
        user.setUserName("oldName");
        user.setEmail("old@mail.com");
        entity.setOrderUser(user);

        CarEngineOrder engineOrder = new CarEngineOrder();
        CarEngine engine = new CarEngine();
        engine.setModel("V6");
        engineOrder.setCarEngine(engine);
        entity.setCarEngineOrder(engineOrder);

        CarColorOrder colorOrder = new CarColorOrder();
        CarColor color = new CarColor();
        color.setColorName("Blue");
        colorOrder.setCarColor(color);
        entity.setCarColorOrder(colorOrder);

        // Arrange: Create a patch DTO with sparse updates
        CarOrderDto patchDto = new CarOrderDto();
        patchDto.setDescription("Updated Description"); // Update a top-level field
        patchDto.setTotalPrice(null); // This should be ignored

        OrderUserDto userPatch = new OrderUserDto();
        userPatch.setEmail("new@mail.com"); // Update a nested field
        patchDto.setOrderUser(userPatch);

        CarEngineOrderDto engineOrderPatch = new CarEngineOrderDto();
        CarEngineDto enginePatch = new CarEngineDto();
        enginePatch.setModel("V8"); // Update another nested field
        engineOrderPatch.setCarEngine(enginePatch);
        patchDto.setCarEngineOrder(engineOrderPatch);

        patchDto.setCarColorOrder(null); // This entire nested object should be ignored

        // Act
        orderMapper.partialUpdate(patchDto, entity);

        // Assert
        assertThat(entity.getCarOrderId()).isEqualTo("ORD-789"); // Unchanged
        assertThat(entity.getDescription()).isEqualTo("Updated Description"); // Updated
        assertThat(entity.getTotalPrice()).isEqualTo(1000); // Unchanged

        // Assert user update
        assertThat(entity.getOrderUser()).isNotNull();
        assertThat(entity.getOrderUser().getUserName()).isEqualTo("oldName"); // Unchanged
        assertThat(entity.getOrderUser().getEmail()).isEqualTo("new@mail.com"); // Updated

        // Assert engine update
        assertThat(entity.getCarEngineOrder()).isNotNull();
        assertThat(entity.getCarEngineOrder().getCarEngine().getModel()).isEqualTo("V8"); // Updated

        // Assert color was NOT updated
        assertThat(entity.getCarColorOrder()).isNotNull();
        assertThat(entity.getCarColorOrder().getCarColor().getColorName()).isEqualTo("Blue"); // Unchanged
    }

    @Test
    void toDto_returnsNull_whenEntityIsNull() {
        assertThat(orderMapper.toDto(null)).isNull();
    }

    @Test
    void toEntity_returnsNull_whenDtoIsNull() {
        assertThat(orderMapper.toEntity(null)).isNull();
    }

    @Test
    void partialUpdate_returnsTarget_whenPatchIsNull() {
        CarOrder entity = new CarOrder();
        entity.setDescription("Original");
        orderMapper.partialUpdate(null, entity);
        assertThat(entity.getDescription()).isEqualTo("Original");
    }
}
