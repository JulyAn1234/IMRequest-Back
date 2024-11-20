package com.IMRequest.IMRequest.model.entities;

/*
    * This class represents the Inventory entity
    * It relates an article to a warehouse and keeps track of the stock
 */

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "inventories")
public class Inventory {
    @Id
    private String id;

    @DBRef
    @JsonProperty("ArticleId")
    private String articleId;

    @DBRef
    @JsonProperty("WarehouseId")
    private String warehouseId;
    @JsonProperty("Stock")
    private int stock;
}
