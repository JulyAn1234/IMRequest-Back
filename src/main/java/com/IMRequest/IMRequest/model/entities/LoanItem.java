package com.IMRequest.IMRequest.model.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoanItem {
    @JsonProperty("ArticleId")
    String articleId;
    @JsonProperty("quantity")
    int quantity;

    boolean isActive;
}
