package com.IMRequest.IMRequest.model.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "warehouses")
public class Warehouse {
    @Id
    private String id;
    @JsonProperty("Name")
    private String name;
    @JsonProperty("isActive")
    private boolean isActive;

    @JsonProperty("Unidad")
    private int unidad;
}
