package com.applicationdemo.carconfig.mapper.order;

import com.applicationdemo.carconfig.dto.order.CarColorOrderDto;
import com.applicationdemo.carconfig.domain.order.CarColorOrder;
import com.applicationdemo.carconfig.mapper.web.CarColorMapper;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = CarColorMapper.class)
public interface CarColorOrderMapper {

    CarColorOrder toEntity(CarColorOrderDto carColorOrderDto);
    CarColorOrderDto toDto(CarColorOrder carColorOrder);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    CarColorOrder partialUpdate(CarColorOrderDto carColorOrderDto, @MappingTarget CarColorOrder carColorOrder);
}