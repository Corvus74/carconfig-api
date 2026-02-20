package com.applicationdemo.carconfig.mapper.web;

import com.applicationdemo.carconfig.dto.web.SpecialEquipmentDto;
import com.applicationdemo.carconfig.domain.base.SpecialEquipment;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface SpecialEquipmentMapper {
    SpecialEquipment toEntity(SpecialEquipmentDto specialEquipmentDto);

    SpecialEquipmentDto toDto(SpecialEquipment specialEquipment);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    SpecialEquipment partialUpdate(SpecialEquipmentDto specialEquipmentDto, @MappingTarget SpecialEquipment specialEquipment);
}