package com.computacenter.carconfig.mapper.order;

import com.computacenter.carconfig.dto.order.CarRimOrderDto;
import com.computacenter.carconfig.entities.order.CarRimOrder;
import com.computacenter.carconfig.mapper.web.CarRimMapper;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = CarRimMapper.class)
public interface CarRimOrderMapper {

    CarRimOrder toEntity(CarRimOrderDto carRimOrderDto);
    CarRimOrderDto toDto(CarRimOrder carRimOrder);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    CarRimOrder partialUpdate(CarRimOrderDto carRimOrderDto, @MappingTarget CarRimOrder carRimOrder);
}