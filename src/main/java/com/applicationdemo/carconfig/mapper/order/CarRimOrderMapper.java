package com.applicationdemo.carconfig.mapper.order;

import com.applicationdemo.carconfig.dto.order.CarRimOrderDto;
import com.applicationdemo.carconfig.entities.order.CarRimOrder;
import com.applicationdemo.carconfig.mapper.web.CarRimMapper;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = CarRimMapper.class)
public interface CarRimOrderMapper {

    CarRimOrder toEntity(CarRimOrderDto carRimOrderDto);
    CarRimOrderDto toDto(CarRimOrder carRimOrder);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    CarRimOrder partialUpdate(CarRimOrderDto carRimOrderDto, @MappingTarget CarRimOrder carRimOrder);
}