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
    private @Size(max = 20) String name;
    private @Size(max = 20) String model;
    private @Size(max = 400) String description;
    private @Size(max = 20) String productId;
    private Integer price;
}