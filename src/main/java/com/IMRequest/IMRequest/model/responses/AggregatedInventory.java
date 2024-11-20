package com.IMRequest.IMRequest.model.responses;

import com.IMRequest.IMRequest.model.entities.Article;
import com.amazonaws.services.dynamodbv2.document.Item;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AggregatedInventory {
    Article article;
    int totalStock;
}
