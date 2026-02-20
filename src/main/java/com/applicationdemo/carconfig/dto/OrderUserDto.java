package com.applicationdemo.carconfig.dto;

import com.applicationdemo.carconfig.domain.OrderUser;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for {@link OrderUser}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderUserDto {
    private @Size(max = 20) String userName;
    private @Size(max = 20) String email;
}