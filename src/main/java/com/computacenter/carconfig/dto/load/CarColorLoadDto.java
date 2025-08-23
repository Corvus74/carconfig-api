package com.computacenter.carconfig.dto.load;

import com.computacenter.carconfig.entities.base.CarColor;
import com.computacenter.carconfig.enums.MaterialType;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for {@link CarColor}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarColorLoadDto {
    @Size(max = 20)
    private String orderNumber;
    @Size(max = 20)
    private String colorName;
    @Size(max = 400)
    private String description;
    @Size(max = 20)
    private String productId;
    MaterialType materialType;
    @Size(max = 10)
    private String colorCodeHex;
    private Integer price;
}
