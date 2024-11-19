package com.IMRequest.IMRequest.model.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LoginUserDto {
    @JsonProperty("Username")
    private String username;

    @JsonProperty("Password")
    private String password;
}