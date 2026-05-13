package com.applicationdemo.carconfig.dto;

import com.applicationdemo.carconfig.domain.user.OrderUser;
import com.applicationdemo.carconfig.domain.user.Roles;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for {@link OrderUser}
 */
@Data
@NoArgsConstructor
@Builder
public class OrderUserDto {
    private @Size(max = 20) String userName;
    private @Size(max = 20) String email;
    private Roles role;

    public OrderUserDto(String userName, String email) {
        this.userName = userName;
        this.email = email;
    }

    public OrderUserDto(String userName, String email, Roles role) {
        this.userName = userName;
        this.email = email;
        this.role = role;
    }
}