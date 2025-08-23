package com.computacenter.carconfig.dto;

import com.computacenter.carconfig.entities.order.Order;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO for {@link Order}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderUpdateDto {
    private String orderId;
    private String userMail;
    private String carEngineProductId;
    private String carRimsProductId;
    private String carColorProductId;
    private Integer price;
    private List<String> specialEquipmentProductIds;

}