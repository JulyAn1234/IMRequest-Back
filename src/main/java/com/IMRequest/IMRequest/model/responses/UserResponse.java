package com.IMRequest.IMRequest.model.responses;

import com.IMRequest.IMRequest.model.entities.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

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
    @JsonProperty("Permissions")
    private List<Integer> Permissions;

    public UserResponse(User user){
        this.id = user.getId();
        this.Username = user.getUsername();
        this.Name = user.getName();
        this.Unidad = user.getUnidad();
        this.Rol = user.getRol();
        this.isActive = user.isActive();
        this.Permissions = user.getPermissions();
    }

    public UserResponse(String id, String Username, String Name, int Unidad, int Rol, boolean isActive, List<Integer> Permissions){
        this.id = id;
        this.Username = Username;
        this.Name = Name;
        this.Unidad = Unidad;
        this.Rol = Rol;
        this.isActive = isActive;
        this.Permissions = Permissions;
    }

}
