package com.heima.model.common.dtos.user;

import lombok.Data;

@Data
public class LoginDto {
    private String phone;
    private String password;
}
