package com.applicationdemo.carconfig.domain.base;

import com.applicationdemo.carconfig.domain.SimpleAuditClasses;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "car_rim")
public class CarRim extends SimpleAuditClasses {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 20)
    @Column(name = "order_number")
    private String orderNumber;

    @Size(max = 20)
    @Column(name = "rim_name")
    private String rimName;

    @Column(name = "inner_diameter")
    private Integer innerDiameter;

    @Size(max = 20)
    @Column(name = "model")
    private String model;

    @Size(max = 400)
    @Column(name = "description")
    private String description;

    @Size(max = 20)
    @Column(name = "product_id")
    private String productId;

    @Column(name = "price")
    private Integer price;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CarRim carRim = (CarRim) o;
        return Objects.equals(id, carRim.id) && Objects.equals(orderNumber, carRim.orderNumber) && Objects.equals(rimName, carRim.rimName) && Objects.equals(innerDiameter, carRim.innerDiameter) && Objects.equals(model, carRim.model) && Objects.equals(description, carRim.description) && Objects.equals(productId, carRim.productId) && Objects.equals(price, carRim.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, orderNumber, rimName, innerDiameter, model, description, productId, price);
    }
}