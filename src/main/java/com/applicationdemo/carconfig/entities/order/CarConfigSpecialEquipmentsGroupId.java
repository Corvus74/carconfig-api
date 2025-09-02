package com.applicationdemo.carconfig.entities.order;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class CarConfigSpecialEquipmentsGroupId {

    @Column(name = "car_order_id")
    private Long carOrderId;

    @Column(name = "special_equipment_order_id")
    private Long specialEquipmentOrderId;

}