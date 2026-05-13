package com.applicationdemo.carconfig.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterUserDto {
    private String email;
    private String password;
    private String userName;
    private String userId;
}
