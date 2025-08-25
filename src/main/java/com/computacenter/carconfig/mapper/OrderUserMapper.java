package com.computacenter.carconfig.mapper;

import com.computacenter.carconfig.dto.OrderUserDto;
import com.computacenter.carconfig.entities.OrderUser;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderUserMapper {
    OrderUser toEntity(OrderUserDto orderUserDto);

    OrderUserDto toDto(OrderUser orderUser);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    OrderUser partialUpdate(OrderUserDto orderUserDto, @MappingTarget OrderUser orderUser);
}