package com.computacenter.carconfig.mapper;

import com.computacenter.carconfig.entities.pool.CarRim;
import com.computacenter.carconfig.dto.base.CarRimDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CarRimMapper {
    CarRim toEntity(CarRimDto carRimDto);

    CarRimDto toDto(CarRim carRim);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    CarRim partialUpdate(CarRimDto carRimDto, @MappingTarget CarRim carRim);
}