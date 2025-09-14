package com.applicationdemo.carconfig.entities.base;

import com.applicationdemo.carconfig.entities.SimpleAuditClasses;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "car_rim")
public class CarRim extends SimpleAuditClasses {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 20)
    @Column(name = "order_number", length = 20)
    private String orderNumber;

    @Size(max = 20)
    @Column(name = "rim_name", length = 20)
    private String rimName;

    @Column(name = "inner_diameter")
    private Integer innerDiameter;

    @Size(max = 20)
    @Column(name = "model", length = 20)
    private String model;

    @Size(max = 400)
    @Column(name = "description", length = 400)
    private String description;

    @Size(max = 20)
    @Column(name = "product_id", length = 20)
    private String productId;

    @Column(name = "price")
    private Integer price;

    @Size(max = 1)
    @Column(name = "delete_flag", length = 1)
    private String deleteFlag;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CarRim carRim = (CarRim) o;
        return Objects.equals(id, carRim.id) && Objects.equals(orderNumber, carRim.orderNumber) && Objects.equals(rimName, carRim.rimName) && Objects.equals(innerDiameter, carRim.innerDiameter) && Objects.equals(model, carRim.model) && Objects.equals(description, carRim.description) && Objects.equals(productId, carRim.productId) && Objects.equals(price, carRim.price) && Objects.equals(deleteFlag, carRim.deleteFlag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, orderNumber, rimName, innerDiameter, model, description, productId, price, deleteFlag);
    }
}