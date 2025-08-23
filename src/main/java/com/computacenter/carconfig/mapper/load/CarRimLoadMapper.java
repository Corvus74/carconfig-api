package com.computacenter.carconfig.mapper.load;

import com.computacenter.carconfig.dto.load.CarRimLoadDto;
import com.computacenter.carconfig.entities.base.CarRim;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CarRimLoadMapper {
    CarRim toEntity(CarRimLoadDto carRimDto);

    CarRimLoadDto toDto(CarRim carRim);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    CarRim partialUpdate(CarRimLoadDto carRimLoadDto, @MappingTarget CarRim carRim);
}