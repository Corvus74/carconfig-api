package com.applicationdemo.carconfig.mapper;

import com.applicationdemo.carconfig.dto.OrderUserDto;
import com.applicationdemo.carconfig.entities.OrderUser;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderUserMapper {
    OrderUser toEntity(OrderUserDto orderUserDto);

    OrderUserDto toDto(OrderUser orderUser);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    OrderUser partialUpdate(OrderUserDto orderUserDto, @MappingTarget OrderUser orderUser);
}