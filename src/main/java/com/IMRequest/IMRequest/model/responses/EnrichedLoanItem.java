package com.IMRequest.IMRequest.model.responses;

import com.IMRequest.IMRequest.model.entities.Article;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EnrichedLoanItem {
    @JsonProperty("Article")
    Article article;
    @JsonProperty("quantity")
    int quantity;

    boolean isActive;
}
