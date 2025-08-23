package com.computacenter.carconfig.dto.web;

import com.computacenter.carconfig.entities.base.SpecialEquipment;
import com.computacenter.carconfig.enums.SpecialEquipmentType;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for {@link SpecialEquipment}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpecialEquipmentDto {
    @Size(max = 10)
    private String equipmentName;
    @Size(max = 10)
    private String model;
    @Size(max = 400)
    private String description;
    @Size(max = 20)
    private String productId;
    private SpecialEquipmentType specialEquipmentType;
    private Integer price;

}