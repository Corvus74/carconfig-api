package com.computacenter.carconfig.entities.base;

import com.computacenter.carconfig.entities.SimpleAuditClasses;
import com.computacenter.carconfig.enums.FuelType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "car_engine")
public class CarEngine extends SimpleAuditClasses {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 20)
    @Column(name = "order_number", length = 10)
    private String orderNumber;

    @Size(max = 400)
    @Column(name = "description", length = 400)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "fuel_type", length = 20)
    private FuelType fuelType;

    @Column(name = "engine_type")
    private String engineType;

    @Size(max = 20)
    @Column(name = "product_id", length = 20)
    private String productId;

    @Size(max = 20)
    @Column(name = "model", length = 20)
    private String model;

    @Column(name = "price")
    private Integer price;

    @Size(max = 255)
    @Column(name = "car_name")
    private String carName;

    @Column(name = "displacement_l", precision = 4, scale = 2)
    private BigDecimal displacementL;

    @Column(name = "cylinders")
    private Integer cylinders;

    @Column(name = "horsepower_kw", precision = 6, scale = 2)
    private BigDecimal horsepowerKw;

    @Column(name = "torque_nm", precision = 6, scale = 2)
    private BigDecimal torqueNm;

    @Size(max = 50)
    @Column(name = "drivetrain")
    private String drivetrain;

    @Column(name = "co2")
    private BigDecimal co2;

    @Size(max = 1)
    @Column(name = "delete_flag", length = 1)
    private String deleteFlag;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CarEngine carEngine = (CarEngine) o;
        return Objects.equals(id, carEngine.id) && Objects.equals(orderNumber, carEngine.orderNumber) && Objects.equals(description, carEngine.description) && fuelType == carEngine.fuelType && Objects.equals(engineType, carEngine.engineType) && Objects.equals(productId, carEngine.productId) && Objects.equals(model, carEngine.model) && Objects.equals(price, carEngine.price) && Objects.equals(carName, carEngine.carName) && Objects.equals(displacementL, carEngine.displacementL) && Objects.equals(cylinders, carEngine.cylinders) && Objects.equals(horsepowerKw, carEngine.horsepowerKw) && Objects.equals(torqueNm, carEngine.torqueNm) && Objects.equals(drivetrain, carEngine.drivetrain) && Objects.equals(co2, carEngine.co2) && Objects.equals(deleteFlag, carEngine.deleteFlag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, orderNumber, description, fuelType, engineType, productId, model, price, carName, displacementL, cylinders, horsepowerKw, torqueNm, drivetrain, co2, deleteFlag);
    }
}