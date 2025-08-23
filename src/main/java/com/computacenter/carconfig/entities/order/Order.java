package com.computacenter.carconfig.entities.order;

import com.computacenter.carconfig.entities.OrderUser;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 40)
    @Column(name = "order_id")
    private String orderId;

    @OneToOne(fetch = FetchType.LAZY)
    private OrderUser orderUser;

    @OneToOne(fetch = FetchType.LAZY)
    private CarEngineOrder carEngineOrder;

    @OneToOne(fetch = FetchType.LAZY)
    private CarRimOrder carRimOrder;

    @OneToOne(fetch = FetchType.LAZY)
    private CarColorsOrder carColorOrder;

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