package com.applicationdemo.carconfig.mapper;

import com.applicationdemo.carconfig.dto.order.*;
import com.applicationdemo.carconfig.entities.order.*;
import com.applicationdemo.carconfig.mapper.order.*;
import com.applicationdemo.carconfig.dto.OrderUserDto;
import com.applicationdemo.carconfig.dto.web.CarColorDto;
import com.applicationdemo.carconfig.dto.web.CarEngineDto;
import com.applicationdemo.carconfig.dto.web.CarRimDto;
import com.applicationdemo.carconfig.dto.web.SpecialEquipmentDto;
import com.applicationdemo.carconfig.entities.OrderUser;
import com.applicationdemo.carconfig.enums.CategoryType;
import com.applicationdemo.carconfig.enums.FuelType;
import com.applicationdemo.carconfig.enums.OrderStatusEnum;
import com.applicationdemo.carconfig.mapper.web.CarColorMapper;
import com.applicationdemo.carconfig.mapper.web.CarEngineMapper;
import com.applicationdemo.carconfig.mapper.web.CarRimMapper;
import com.applicationdemo.carconfig.mapper.web.SpecialEquipmentMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


class OrderMapperTest {

    private static OrderMapper createMapper() {
        OrderMapper mapper = Mappers.getMapper(OrderMapper.class);
        // Prepare leaf mappers
        CarEngineMapper carEngineMapper = Mappers.getMapper(CarEngineMapper.class);
        CarColorMapper carColorMapper = Mappers.getMapper(CarColorMapper.class);
        CarRimMapper carRimMapper = Mappers.getMapper(CarRimMapper.class);
        SpecialEquipmentMapper specialEquipmentMapper = Mappers.getMapper(SpecialEquipmentMapper.class);
        OrderUserMapper orderUserMapper = Mappers.getMapper(OrderUserMapper.class);
        OrderStatusMapper orderStatusMapper = Mappers.getMapper(OrderStatusMapper.class);

        // Prepare order sub-mappers and wire their dependencies
        CarEngineOrderMapper carEngineOrderMapper = Mappers.getMapper(CarEngineOrderMapper.class);
        setField(carEngineOrderMapper, "carEngineMapper", carEngineMapper);

        CarColorOrderMapper carColorOrderMapper = Mappers.getMapper(CarColorOrderMapper.class);
        setField(carColorOrderMapper, "carColorMapper", carColorMapper);

        CarRimOrderMapper carRimOrderMapper = Mappers.getMapper(CarRimOrderMapper.class);
        setField(carRimOrderMapper, "carRimMapper", carRimMapper);

        SpecialEquipmentOrderMapper specialEquipmentOrderMapper = Mappers.getMapper(SpecialEquipmentOrderMapper.class);
        setField(specialEquipmentOrderMapper, "specialEquipmentMapper", specialEquipmentMapper);

        // Wire nested mappers into the root OrderMapper
        setField(mapper, "specialEquipmentOrderMapper", specialEquipmentOrderMapper);
        setField(mapper, "orderUserMapper", orderUserMapper);
        setField(mapper, "carEngineOrderMapper", carEngineOrderMapper);
        setField(mapper, "carColorOrderMapper", carColorOrderMapper);
        setField(mapper, "carRimOrderMapper", carRimOrderMapper);
        setField(mapper, "orderStatusMapper", orderStatusMapper);
        return mapper;
    }

    private static void setField(Object target, String fieldName, Object value) {
        try {
            java.lang.reflect.Field f = target.getClass().getDeclaredField(fieldName);
            f.setAccessible(true);
            f.set(target, value);
        } catch (Exception e) {
            throw new RuntimeException("Failed to set field '" + fieldName + "' on " + target.getClass(), e);
        }
    }

    private final OrderMapper orderMapper = createMapper();

    @Test
    @DisplayName("toEntity maps CarOrderDto and nested structures correctly")
    void toEntity_mapsAllFields() {
        // Arrange
        CarOrderDto dto = new CarOrderDto();
        dto.setCarOrderId("ORD-123");
        dto.setDescription("Sample order");
        dto.setTotalPrice(99999);

        OrderUserDto userDto = new OrderUserDto("john.doe","john@doe.tld");
        dto.setOrderUser(userDto);

        CarEngineDto engineDto = new CarEngineDto(
                "V8 engine", FuelType.GASOLINE, "V8",
                "ENG-001", "X1", 25000,
                new BigDecimal("4.0"), 8,
                new BigDecimal("300"), new BigDecimal("500"),
                "AWD", new BigDecimal("150.5")
        );
        CarEngineOrderDto engineOrderDto = new CarEngineOrderDto("Test1",engineDto,
                new CarOrderStatusDto(OrderStatusEnum.IN_PROGRESS, LocalDate.now(), LocalDate.now().plusDays(2)));
        dto.setCarEngineOrder(engineOrderDto);

        CarColorDto colorDto = new CarColorDto("Red", "Metallic red", "COL-01", 1200,
                null, null, "#FF0000");
        CarColorOrderDto colorOrderDto = new CarColorOrderDto("TEST2",colorDto,
                new CarOrderStatusDto(OrderStatusEnum.PENDING, null, null));
        dto.setCarColorOrder(colorOrderDto);

        CarRimDto rimDto = new CarRimDto("Alloy 18", "AL18", "Nice rims", "RIM-18", 17,1500);
        CarRimOrderDto rimOrderDto = new CarRimOrderDto("Test3",rimDto,
                new CarOrderStatusDto(OrderStatusEnum.COMPLETED, null, null));
        dto.setCarRimOrder(rimOrderDto);

        SpecialEquipmentDto seDto = new SpecialEquipmentDto("RoofBox", "Large roof box", "SE-01",
                CategoryType.MISC, null, 300);
        SpecialEquipmentOrderDto seOrderDto = new SpecialEquipmentOrderDto("Test3",seDto,
                new CarOrderStatusDto( OrderStatusEnum.PENDING, null, null));
        dto.setSpecialEquipmentOrders(List.of(seOrderDto));

        dto.setOrderStatus(new CarOrderStatusDto(OrderStatusEnum.PENDING, null, null));

        // Act
        CarOrder entity = orderMapper.toEntity(dto);

        // Assert
        assertThat(entity).isNotNull();
        assertThat(entity.getCarOrderId()).isEqualTo("ORD-123");
        assertThat(entity.getDescription()).isEqualTo("Sample order");
        assertThat(entity.getTotalPrice()).isEqualTo(99999);

        assertThat(entity.getOrderUser()).isNotNull();
        assertThat(entity.getOrderUser().getUserName()).isEqualTo("john.doe");
        assertThat(entity.getOrderUser().getEmail()).isEqualTo("john@doe.tld");

        assertThat(entity.getCarEngineOrder()).isNotNull();
        assertThat(entity.getCarEngineOrder().getCarEngine()).isNotNull();
        assertThat(entity.getCarEngineOrder().getOrderStatus()).isNotNull();

        assertThat(entity.getCarColorOrder()).isNotNull();
        assertThat(entity.getCarColorOrder().getCarColor()).isNotNull();

        assertThat(entity.getCarRimOrder()).isNotNull();
        assertThat(entity.getCarRimOrder().getCarRim()).isNotNull();

        assertThat(entity.getSpecialEquipmentOrders()).hasSize(1);
        assertThat(entity.getOrderStatus()).isNotNull();
    }

    @Test
    @DisplayName("toDto maps CarOrder entity and nested structures correctly")
    void toDto_mapsAllFields() {
        // Arrange (build a minimal entity graph)
        CarOrder entity = new CarOrder();
        entity.setCarOrderId("ORD-999");
        entity.setDescription("Another order");
        entity.setTotalPrice(12345);

        OrderUser user = new OrderUser();
        user.setUserName("alice");
        user.setEmail("alice@example.com");
        entity.setOrderUser(user);

        CarEngineOrder engineOrder = new CarEngineOrder();
        engineOrder.setOrderStatus(new OrderStatus());
        entity.setCarEngineOrder(engineOrder);

        CarColorOrder colorOrder = new CarColorOrder();
        colorOrder.setOrderStatus(new OrderStatus());
        entity.setCarColorOrder(colorOrder);

        CarRimOrder rimOrder = new CarRimOrder();
        rimOrder.setOrderStatus(new OrderStatus());
        entity.setCarRimOrder(rimOrder);

        SpecialEquipmentOrder seOrder = new SpecialEquipmentOrder();
        seOrder.setOrderStatus(new OrderStatus());
        entity.setSpecialEquipmentOrders(List.of(seOrder));

        OrderStatus rootStatus = new OrderStatus();
        rootStatus.setCurrentStatus(OrderStatusEnum.PENDING);
        entity.setOrderStatus(rootStatus);

        // Act
        CarOrderDto dto = orderMapper.toDto(entity);

        // Assert
        assertThat(dto).isNotNull();
        assertThat(dto.getCarOrderId()).isEqualTo("ORD-999");
        assertThat(dto.getDescription()).isEqualTo("Another order");
        assertThat(dto.getTotalPrice()).isEqualTo(12345);
        assertThat(dto.getOrderUser()).isNotNull();
        assertThat(dto.getOrderUser().getUserName()).isEqualTo("alice");
        assertThat(dto.getOrderUser().getEmail()).isEqualTo("alice@example.com");
        assertThat(dto.getCarEngineOrder()).isNotNull();
        assertThat(dto.getCarColorOrder()).isNotNull();
        assertThat(dto.getCarRimOrder()).isNotNull();
        assertThat(dto.getSpecialEquipmentOrders()).hasSize(1);
        assertThat(dto.getOrderStatus()).isNotNull();
        assertThat(dto.getOrderStatus().getCurrentStatus()).isEqualTo(OrderStatusEnum.PENDING);
    }

    @Test
    @DisplayName("partialUpdate updates only non-null fields and preserves existing values, with nested updates")
    void partialUpdate_ignoresNulls_andUpdatesNested() {
        // Arrange: existing entity
        CarOrder entity = new CarOrder();
        entity.setCarOrderId("E-1");
        entity.setDescription("old");
        entity.setTotalPrice(100);

        OrderUser user = new OrderUser();
        user.setUserName("oldName");
        user.setEmail("old@mail");
        entity.setOrderUser(user);

        // DTO with partial updates: only description and nested user email
        CarOrderDto patch = new CarOrderDto();
        patch.setDescription("new");
        OrderUserDto patchUser = new OrderUserDto(null, "new@mail");
        patch.setOrderUser(patchUser);
        patch.setTotalPrice(null); // should be ignored

        // Act
        CarOrder updated = orderMapper.partialUpdate(patch, entity);

        // Assert: same instance updated
        assertThat(updated).isSameAs(entity);
        assertThat(updated.getDescription()).isEqualTo("new");
        assertThat(updated.getTotalPrice()).isEqualTo(100); // unchanged
        assertThat(updated.getCarOrderId()).isEqualTo("E-1"); // unchanged

        assertThat(updated.getOrderUser()).isNotNull();
        assertThat(updated.getOrderUser().getUserName()).isEqualTo("oldName"); // unchanged because null in DTO
        assertThat(updated.getOrderUser().getEmail()).isEqualTo("new@mail"); // updated
    }
}
