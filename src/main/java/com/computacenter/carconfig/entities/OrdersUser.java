package com.computacenter.carconfig.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "orders_user")
public class OrdersUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id", nullable = false)
    private Integer id;

    @Size(max = 20)
    @Column(name = "user_name", length = 20)
    private String userName;

    @Size(max = 20)
    @Column(name = "email", length = 20)
    private String email;

    @Size(max = 1)
    @Column(name = "is_valid", length = 1)
    private boolean isValid;

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