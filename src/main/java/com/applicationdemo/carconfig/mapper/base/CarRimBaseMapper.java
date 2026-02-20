package com.applicationdemo.carconfig.mapper.base;

import com.applicationdemo.carconfig.dto.load.CarRimLoadDto;
import com.applicationdemo.carconfig.domain.base.CarRim;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CarRimBaseMapper {
    CarRim toEntity(CarRimLoadDto carRimDto);

    CarRimLoadDto toDto(CarRim carRim);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    CarRim partialUpdate(CarRimLoadDto carRimLoadDto, @MappingTarget CarRim carRim);
}