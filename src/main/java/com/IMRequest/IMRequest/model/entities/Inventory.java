package com.IMRequest.IMRequest.model.entities;

/*
    * This class represents the Inventory entity
    * It relates an article to a warehouse and keeps track of the stock
 */

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;

public class Inventory {
    @Id
    private String id;
    @JsonProperty("ArticleId")
    private String articleId;
    @JsonProperty("WarehouseId")
    private String warehouseId;
    @JsonProperty("Stock")
    private int stock;
}
