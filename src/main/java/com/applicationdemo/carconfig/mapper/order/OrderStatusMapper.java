package com.applicationdemo.carconfig.mapper.order;

import com.applicationdemo.carconfig.dto.order.CarOrderStatusDto;
import com.applicationdemo.carconfig.domain.order.OrderStatus;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderStatusMapper {
    OrderStatus toEntity(CarOrderStatusDto carOrderStatusDto);

    CarOrderStatusDto toDto(OrderStatus orderStatus);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    OrderStatus partialUpdate(CarOrderStatusDto carOrderStatusDto, @MappingTarget OrderStatus orderStatus);
}