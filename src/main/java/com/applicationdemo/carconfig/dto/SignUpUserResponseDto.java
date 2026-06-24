package com.applicationdemo.carconfig.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SignUpUserResponseDto {
    private String email;
    private String userName;
    private String userId;
}
