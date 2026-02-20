package com.applicationdemo.carconfig.dto.web;

import com.applicationdemo.carconfig.domain.order.CarOrder;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO for {@link CarOrder}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductInfoDetailDto {
    private CarEngineDto carEngine;
    private CarRimDto carRim;
    private CarColorDto carColor;
    private List<SpecialEquipmentDto> specialEquipment;

}