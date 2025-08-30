package com.computacenter.carconfig.entities.order;

import com.computacenter.carconfig.entities.SimpleAuditClasses;
import com.computacenter.carconfig.entities.base.SpecialEquipment;
import jakarta.persistence.*;
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
    private OrderStatus orderStatus;

    @Size(max = 1)
    @Column(name = "delete_flag", length = 1)
    private String deleteFlag;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SpecialEquipmentOrder that = (SpecialEquipmentOrder) o;
        return Objects.equals(id, that.id) && Objects.equals(specialEquipmentOrderId, that.specialEquipmentOrderId) && Objects.equals(specialEquipment, that.specialEquipment) && Objects.equals(orderStatus, that.orderStatus) && Objects.equals(deleteFlag, that.deleteFlag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, specialEquipmentOrderId, specialEquipment, orderStatus, deleteFlag);
    }
}