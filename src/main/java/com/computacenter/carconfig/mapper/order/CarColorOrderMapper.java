package com.computacenter.carconfig.mapper.order;

import com.computacenter.carconfig.dto.order.CarColorOrderDto;
import com.computacenter.carconfig.entities.order.CarColorOrder;
import com.computacenter.carconfig.mapper.web.CarColorMapper;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = CarColorMapper.class)
public interface CarColorOrderMapper {

    CarColorOrder toEntity(CarColorOrderDto carColorOrderDto);
    CarColorOrderDto toDto(CarColorOrder carColorOrder);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    CarColorOrder partialUpdate(CarColorOrderDto carColorOrderDto, @MappingTarget CarColorOrder carColorOrder);
}