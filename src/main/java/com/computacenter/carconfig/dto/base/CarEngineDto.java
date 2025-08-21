package com.computacenter.carconfig.dto.base;

import com.computacenter.carconfig.enums.FuelType;
import com.computacenter.carconfig.entities.pool.CarEngine;
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
public class CarEngineDto {
    private @Size(max = 400) String description;
    private FuelType fuelType;
    private String engineType;
    private @Size(max = 20) String productId;
    private @Size(max = 20) String model;
    private Integer price;
    private BigDecimal displacementL;
    private Integer cylinders;
    private BigDecimal horsepowerKw;
    private BigDecimal torqueNm;
    private @Size(max = 50) String drivetrain;
    private BigDecimal co2;
}