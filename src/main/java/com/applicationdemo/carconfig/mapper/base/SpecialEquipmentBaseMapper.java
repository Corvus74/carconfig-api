package com.applicationdemo.carconfig.mapper.base;

import com.applicationdemo.carconfig.dto.load.SpecialEquipmentLoadDto;
import com.applicationdemo.carconfig.entities.base.SpecialEquipment;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface SpecialEquipmentBaseMapper {
    SpecialEquipment toEntity(SpecialEquipmentLoadDto specialEquipmentDto);

    SpecialEquipmentLoadDto toDto(SpecialEquipment specialEquipment);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    SpecialEquipment partialUpdate(SpecialEquipmentLoadDto specialEquipmentDto, @MappingTarget SpecialEquipment specialEquipment);
}