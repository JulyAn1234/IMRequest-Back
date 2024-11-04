package com.ecommerceAuth.ecommerceAuth.model.entities;

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
@Document(collection = "articles")
public class Article {
    @Id
    private String id;
    @JsonProperty("Partida")
    private int partida;
    @JsonProperty("Name")
    private String name;
    @JsonProperty("Description")
    private String description;
    @JsonProperty("Measure")
    private int measure;

    @JsonProperty("isActive")
    private boolean isActive;

}
