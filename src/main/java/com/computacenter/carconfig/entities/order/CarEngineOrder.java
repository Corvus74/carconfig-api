package com.computacenter.carconfig.entities.order;

import com.computacenter.carconfig.entities.base.CarEngine;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "car_engine_order")
public class CarEngineOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 40)
    @Column(name = "order_id")
    private String orderId;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private CarEngine carEngine;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private OrderStatus orderStatus;

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