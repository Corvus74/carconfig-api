package com.computacenter.carconfig.dto.web;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseConfigDto {
    private List<CarColorDto> carColors;
    private List<CarEngineDto> carEngines;
    private List<CarRimDto> carRims;
    private List<SpecialEquipmentDto> specialEquipment;
}
