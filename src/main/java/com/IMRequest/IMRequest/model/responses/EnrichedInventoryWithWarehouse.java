package com.IMRequest.IMRequest.model.responses;

import com.IMRequest.IMRequest.model.entities.Article;
import com.IMRequest.IMRequest.model.entities.Warehouse;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EnrichedInventoryWithWarehouse {
    Warehouse warehouse;
    int stock;
}
