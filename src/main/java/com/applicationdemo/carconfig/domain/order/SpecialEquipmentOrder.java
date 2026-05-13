package com.applicationdemo.carconfig.domain.order;

import com.applicationdemo.carconfig.domain.SimpleAuditClasses;
import com.applicationdemo.carconfig.domain.base.SpecialEquipment;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "special_equipment_order")
public class SpecialEquipmentOrder extends SimpleAuditClasses {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 40)
    @Column(name = "special_equipment_order_id")
    private String specialEquipmentOrderId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "special_equipment_id")
    private SpecialEquipment specialEquipment;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "order_status_id")
    private OrderStatus orderStatus;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SpecialEquipmentOrder that = (SpecialEquipmentOrder) o;
        return Objects.equals(id, that.id) && Objects.equals(specialEquipmentOrderId, that.specialEquipmentOrderId) && Objects.equals(specialEquipment, that.specialEquipment) && Objects.equals(orderStatus, that.orderStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, specialEquipmentOrderId, specialEquipment, orderStatus);
    }
}