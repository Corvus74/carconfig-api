package com.applicationdemo.carconfig.domain.base;

import com.applicationdemo.carconfig.domain.SimpleAuditClasses;
import com.applicationdemo.carconfig.enums.EquipmentLocation;
import com.applicationdemo.carconfig.enums.CategoryType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    @Column(name = "order_number")
    private String orderNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "category_type")
    private CategoryType categoryType;

    @Size(max = 30)
    @Column(name = "equipment_name")
    private String equipmentName;

    @Size(max = 400)
    @Column(name = "description")
    private String description;

    @Size(max = 20)
    @Column(name = "product_id")
    private String productId;

    @Enumerated(EnumType.STRING)
    @Column(name = "equipment_location")
    private EquipmentLocation equipmentLocation;

    @Column(name = "price")
    private Integer price;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SpecialEquipment that = (SpecialEquipment) o;
        return Objects.equals(id, that.id) && Objects.equals(orderNumber, that.orderNumber) && categoryType == that.categoryType && Objects.equals(equipmentName, that.equipmentName) && Objects.equals(description, that.description) && Objects.equals(productId, that.productId) && Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, orderNumber, categoryType, equipmentName, description, productId, price);
    }
}