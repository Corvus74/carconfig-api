package com.applicationdemo.carconfig.dto.web;

import com.applicationdemo.carconfig.enums.FuelType;
import com.applicationdemo.carconfig.domain.base.CarEngine;
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
    @Size(max = 400)
    private String description;
    private FuelType fuelType;
    private String engineType;
    @Size(max = 20)
    private String productId;
    @Size(max = 20)
    private String model;
    private Integer price;
    private BigDecimal displacementL;
    private Integer cylinders;
    private BigDecimal horsepowerKw;
    private BigDecimal torqueNm;
    @Size(max = 50)
    private String drivetrain;
    private BigDecimal co2;
}
