package com.applicationdemo.carconfig.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "orders_user")
public class OrderUser extends SimpleAuditClasses {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 40)
    @Column(name = "user_id", nullable = false)
    private String userId;

    @Size(max = 20)
    @Column(name = "user_name")
    private String userName;

    @Size(max = 20)
    @Column(name = "email")
    private String email;

    @Column(name = "is_valid")
    private boolean isValid;

    @Size(max = 1)
    @Column(name = "delete_flag")
    private String deleteFlag;


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        OrderUser orderUser = (OrderUser) o;
        return isValid == orderUser.isValid && Objects.equals(id, orderUser.id) && Objects.equals(userId, orderUser.userId) && Objects.equals(userName, orderUser.userName) && Objects.equals(email, orderUser.email) && Objects.equals(deleteFlag, orderUser.deleteFlag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, userId, userName, email, isValid, deleteFlag);
    }
}