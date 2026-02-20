package com.applicationdemo.carconfig.dto.web;

import com.applicationdemo.carconfig.domain.base.SpecialEquipment;
import com.applicationdemo.carconfig.enums.CategoryType;
import com.applicationdemo.carconfig.enums.EquipmentLocation;
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
    private String equipmentName;
    @Size(max = 400)
    private String description;
    @Size(max = 20)
    private String productId;
    private CategoryType categoryType;
    private EquipmentLocation equipmentLocation;
    private Integer price;

}