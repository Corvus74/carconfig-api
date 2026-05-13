package com.applicationdemo.carconfig.domain.order;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarConfigSpecialEquipmentsGroupId {

    @Column(name = "car_order_id")
    private Long carOrderId;

    @Column(name = "special_equipment_order_id")
    private Long specialEquipmentOrderId;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CarConfigSpecialEquipmentsGroupId that = (CarConfigSpecialEquipmentsGroupId) o;
        return Objects.equals(carOrderId, that.carOrderId) && Objects.equals(specialEquipmentOrderId, that.specialEquipmentOrderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(carOrderId, specialEquipmentOrderId);
    }
}