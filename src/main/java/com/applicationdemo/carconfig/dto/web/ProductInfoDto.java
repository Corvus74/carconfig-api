package com.applicationdemo.carconfig.dto.web;

import com.applicationdemo.carconfig.entities.order.CarOrder;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO for {@link CarOrder}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductInfoDto {
    private String carEngineProductId;
    private String carRimsProductId;
    private String carColorProductId;
    private List<String> specialEquipmentProductIds;

}