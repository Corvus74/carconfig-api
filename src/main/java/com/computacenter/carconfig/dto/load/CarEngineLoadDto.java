package com.computacenter.carconfig.dto.load;

import com.computacenter.carconfig.entities.base.CarEngine;
import com.computacenter.carconfig.enums.FuelType;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO for {@link CarEngine}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarEngineLoadDto {
    @Size(max = 20)
    private String orderNumber;
    @Size(max = 400)
    private String description;
    private FuelType fuelType;
    private String engineType;
    @Size(max = 20)
    private String productId;
    @Size(max = 20)
    private String model;
    private Integer price;
    @Size(max = 255)
    private String carName;
    private BigDecimal displacementL;
    private Integer cylinders;
    private BigDecimal horsepowerKw;
    private BigDecimal torqueNm;
    @Size(max = 50)
    private String drivetrain;
    private BigDecimal co2;
}