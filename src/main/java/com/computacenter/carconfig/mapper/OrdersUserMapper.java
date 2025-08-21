package com.computacenter.carconfig.mapper;

import com.computacenter.carconfig.dto.OrderUserDto;
import com.computacenter.carconfig.entities.OrdersUser;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrdersUserMapper {
    OrdersUser toEntity(OrderUserDto orderUserDto);

    OrderUserDto toDto(OrdersUser ordersUser);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    OrdersUser partialUpdate(OrderUserDto orderUserDto, @MappingTarget OrdersUser ordersUser);
}