package com.computacenter.carconfig.mapper;

import com.computacenter.carconfig.dto.base.SpecialEquipmentDto;
import com.computacenter.carconfig.entities.pool.SpecialEquipment;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface SpecialEquipmentMapper {
    SpecialEquipment toEntity(SpecialEquipmentDto specialEquipmentDto);

    SpecialEquipmentDto toDto(SpecialEquipment specialEquipment);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    SpecialEquipment partialUpdate(SpecialEquipmentDto specialEquipmentDto, @MappingTarget SpecialEquipment specialEquipment);
}