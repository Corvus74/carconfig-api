package com.applicationdemo.carconfig.dto.load;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseConfigLoadDto {
    private List<CarColorLoadDto> carColorLoadDtos;
    private List<CarEngineLoadDto> carEngineLoadDtos;
    private List<CarRimLoadDto> carRimLoadDtos;
    private List<SpecialEquipmentLoadDto> specialEquipmentLoadDtos;
}
