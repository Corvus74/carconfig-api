package com.computacenter.carconfig.dto.order;

import com.computacenter.carconfig.entities.order.CarOrder;
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
public class CarOrderUpdateDto {
    private String orderId;
    private String userMail;
    private String carEngineProductId;
    private String carRimsProductId;
    private String carColorProductId;
    private Integer price;
    private List<String> specialEquipmentProductIds;

}