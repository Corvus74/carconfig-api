package com.applicationdemo.carconfig.domain.order;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "car_config_special_equipments_group")
public class CarConfigSpecialEquipmentsGroup {

    @EmbeddedId
    private CarConfigSpecialEquipmentsGroupId groupId;

    @MapsId("carOrderId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "car_order_id", referencedColumnName = "id", nullable = false)
    private CarOrder carOrder;

    @MapsId("specialEquipmentOrderId")
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "special_equipment_order_id", referencedColumnName = "id", nullable = false)
    private SpecialEquipmentOrder specialEquipmentOrderId;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CarConfigSpecialEquipmentsGroup that = (CarConfigSpecialEquipmentsGroup) o;
        return Objects.equals(groupId, that.groupId) && Objects.equals(carOrder, that.carOrder) && Objects.equals(specialEquipmentOrderId, that.specialEquipmentOrderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupId, carOrder, specialEquipmentOrderId);
    }
}