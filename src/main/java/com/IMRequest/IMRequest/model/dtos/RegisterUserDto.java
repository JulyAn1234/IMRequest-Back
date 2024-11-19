package com.IMRequest.IMRequest.model.dtos;

import ch.qos.logback.core.rolling.helper.IntegerTokenConverter;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;

import java.util.List;

@Data
public class RegisterUserDto {

    @JsonProperty("Username")
    private String username;

    @JsonProperty("Password")
    private String password;

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Unidad")
    private int unidad;

    @JsonProperty("Rol")
    private int rol;

    @JsonProperty("Permissions")
    private List<Integer> permissions;

    private boolean isActive;

}
