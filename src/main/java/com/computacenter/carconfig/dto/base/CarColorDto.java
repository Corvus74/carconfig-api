package com.computacenter.carconfig.dto.base;

import com.computacenter.carconfig.entities.pool.CarColors;
import com.computacenter.carconfig.enums.MaterialType;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for {@link CarColors}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarColorDto {
    private Integer id;
    private @Size(max = 20) String name;
    private @Size(max = 400) String description;
    private @Size(max = 20) String productId;
    private Integer price;
    private MaterialType materialType;
    private String colorCodeHex;

}