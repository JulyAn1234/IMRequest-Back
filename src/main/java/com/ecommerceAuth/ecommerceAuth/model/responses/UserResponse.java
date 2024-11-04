package com.ecommerceAuth.ecommerceAuth.model.responses;

import com.ecommerceAuth.ecommerceAuth.model.entities.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {
    private String id;
    @JsonProperty("Username")
    private String Username;
    @JsonProperty("Name")
    private String Name;
    @JsonProperty("Unidad")
    private int Unidad;
    @JsonProperty("Rol")
    private int Rol;
    @JsonProperty("isActive")
    private boolean isActive;

    public UserResponse(User user){
        this.id = user.getId();
        this.Username = user.getUsername();
        this.Name = user.getName();
        this.Unidad = user.getUnidad();
        this.Rol = user.getRol();
        this.isActive = user.isActive();
    }

    public UserResponse(String id, String Username, String Name, int Unidad, int Rol, boolean isActive){
        this.id = id;
        this.Username = Username;
        this.Name = Name;
        this.Unidad = Unidad;
        this.Rol = Rol;
        this.isActive = isActive;
    }

}
