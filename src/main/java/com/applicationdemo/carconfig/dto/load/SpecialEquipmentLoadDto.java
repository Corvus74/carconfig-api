package com.applicationdemo.carconfig.dto.load;

import com.applicationdemo.carconfig.entities.base.SpecialEquipment;
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
public class SpecialEquipmentLoadDto {
    @Size(max = 20)
    private String orderNumber;
    @Size(max = 30)
    private String equipmentName;
    private @Size(max = 400) String description;
    private @Size(max = 20) String productId;
    private CategoryType categoryType;
    private EquipmentLocation equipmentLocation;
    private Integer price;

}