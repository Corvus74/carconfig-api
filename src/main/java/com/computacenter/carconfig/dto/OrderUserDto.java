package com.computacenter.carconfig.dto;

import com.computacenter.carconfig.entities.OrdersUser;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for {@link OrdersUser}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderUserDto {
    private @Size(max = 20) String userName;
    private @Size(max = 20) String email;
}