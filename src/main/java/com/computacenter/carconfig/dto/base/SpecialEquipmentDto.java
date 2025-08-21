package com.computacenter.carconfig.dto.base;

import com.computacenter.carconfig.entities.pool.SpecialEquipment;
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
    private @Size(max = 10) String name;
    private @Size(max = 10) String model;
    private @Size(max = 400) String description;
    private @Size(max = 20) String productId;
    private SpecialEquipmentType specialEquipmentType;
    private Integer price;

}