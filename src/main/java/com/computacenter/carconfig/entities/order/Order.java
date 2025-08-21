package com.computacenter.carconfig.entities.order;

import com.computacenter.carconfig.entities.OrdersUser;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    private OrdersUser userId;

    @OneToOne(fetch = FetchType.LAZY)
    private CarEngineOrder carEngineId;

    @OneToOne(fetch = FetchType.LAZY)
    private CarRimOrder carRimsOrder;

    @OneToOne(fetch = FetchType.LAZY)
    private CarColorsOrder carColorsOrder;

    @OneToOne(fetch = FetchType.LAZY)
    private OrderStatus orderStatus;

    @Size(max = 400)
    @Column(name = "description", length = 400)
    private String description;

    @Column(name = "price")
    private Integer price;

    @OneToMany(cascade = CascadeType.ALL)
    private List<SpecialEquipmentOrder> specialEquipmentOrders;

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