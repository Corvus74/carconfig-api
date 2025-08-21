package com.computacenter.carconfig.entities.pool;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "car_rims")
public class CarRim {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 20)
    @Column(name = "order_number", length = 10)
    private String orderNumber;

    @Size(max = 20)
    @Column(name = "name", length = 20)
    private String name;

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