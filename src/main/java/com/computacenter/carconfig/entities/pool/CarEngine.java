package com.computacenter.carconfig.entities.pool;

import com.computacenter.carconfig.enums.FuelType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "car_engine")
public class CarEngine {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

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

    @Size(max = 20)
    @Column(name = "created_by", length = 20)
    private String createdBy;

    @Column(name = "created_at")
    private Instant createdAt;

    @Size(max = 20)
    @Column(name = "modified_by", length = 20)
    private String modifiedBy;

    @Column(name = "modified_at")
    private Instant modifiedAt;

}