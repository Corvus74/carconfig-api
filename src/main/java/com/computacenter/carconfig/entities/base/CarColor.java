package com.computacenter.carconfig.entities.base;

import com.computacenter.carconfig.entities.SimpleAuditClasses;
import com.computacenter.carconfig.enums.MaterialType;
import com.computacenter.carconfig.enums.PaintingType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "car_color")
public class CarColor extends SimpleAuditClasses {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 20)
    @Column(name = "order_number", length = 10)
    private String orderNumber;

    @Size(max = 20)
    @Column(name = "color_name", length = 20)
    private String colorName;

    @Size(max = 400)
    @Column(name = "description", length = 400)
    private String description;

    @Size(max = 20)
    @Column(name = "product_id", length = 20)
    private String productId;

    @Enumerated(EnumType.STRING)
    @Column(name = "material_type")
    private MaterialType materialType;

    @Enumerated(EnumType.STRING)
    @Column(name = "painting_type")
    private PaintingType paintingType;

    @Size(max = 10)
    @Column(name = "color_code_hex", length = 10)
    private String colorCodeHex;

    @Column(name = "price")
    private Integer price;

    @Size(max = 1)
    @Column(name = "delete_flag", length = 1)
    private String deleteFlag;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CarColor carColor = (CarColor) o;
        return Objects.equals(id, carColor.id) && Objects.equals(orderNumber, carColor.orderNumber) && Objects.equals(colorName, carColor.colorName) && Objects.equals(description, carColor.description) && Objects.equals(productId, carColor.productId) && materialType == carColor.materialType && paintingType == carColor.paintingType && Objects.equals(colorCodeHex, carColor.colorCodeHex) && Objects.equals(price, carColor.price) && Objects.equals(deleteFlag, carColor.deleteFlag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, orderNumber, colorName, description, productId, materialType, paintingType, colorCodeHex, price, deleteFlag);
    }
}