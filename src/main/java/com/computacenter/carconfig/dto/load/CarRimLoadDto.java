package com.computacenter.carconfig.dto.load;

import com.computacenter.carconfig.entities.base.CarRim;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for {@link CarRim}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarRimLoadDto {
    @Size(max = 20)
    private String orderNumber;
    @Size(max = 20)
    private String rimName;
    @Size(max = 20)
    private String model;
    @Size(max = 400)
    private String description;
    @Size(max = 20)
    private String productId;
    private Integer innerDiameter;
    private Integer price;
}