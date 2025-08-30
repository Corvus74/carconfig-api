package com.computacenter.carconfig.dto.load;

import com.computacenter.carconfig.entities.base.SpecialEquipment;
import com.computacenter.carconfig.enums.CategoryType;
import com.computacenter.carconfig.enums.EquipmentLocation;
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
    @Size(max = 10)
    private String equipmentName;
    private @Size(max = 400) String description;
    private @Size(max = 20) String productId;
    private CategoryType categoryType;
    private EquipmentLocation equipmentLocation;
    private Integer price;

}