package com.applicationdemo.carconfig.entities.base;

import com.applicationdemo.carconfig.entities.SimpleAuditClasses;
import com.applicationdemo.carconfig.enums.EquipmentLocation;
import com.applicationdemo.carconfig.enums.CategoryType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "special_equipment")
public class SpecialEquipment extends SimpleAuditClasses {

    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 20)
    @Column(name = "order_number", length = 20)
    private String orderNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "category_type", length = 20)
    private CategoryType categoryType;

    @Size(max = 30)
    @Column(name = "equipment_name", length = 30)
    private String equipmentName;

    @Size(max = 400)
    @Column(name = "description", length = 400)
    private String description;

    @Size(max = 20)
    @Column(name = "product_id", length = 20)
    private String productId;

    @Enumerated(EnumType.STRING)
    @Column(name = "equipment_location")
    private EquipmentLocation equipmentLocation;

    @Column(name = "price")
    private Integer price;

    @Size(max = 1)
    @Column(name = "delete_flag", length = 1)
    private String deleteFlag;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SpecialEquipment that = (SpecialEquipment) o;
        return Objects.equals(id, that.id) && Objects.equals(orderNumber, that.orderNumber) && categoryType == that.categoryType && Objects.equals(equipmentName, that.equipmentName) && Objects.equals(description, that.description) && Objects.equals(productId, that.productId) && Objects.equals(price, that.price) && Objects.equals(deleteFlag, that.deleteFlag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, orderNumber, categoryType, equipmentName, description, productId, price, deleteFlag);
    }
}