package com.IMRequest.IMRequest.model.dtos;

import lombok.Data;

@Data
public class LoginUserDto {
    private String username;

    private String password;
}