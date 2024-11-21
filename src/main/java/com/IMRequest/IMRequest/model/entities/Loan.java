package com.IMRequest.IMRequest.model.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "loans")
public class Loan {
    @Id
    String id;

    @JsonProperty("UserId")
    String userId;

    @JsonProperty("WarehouseId")
    String warehouseId;

    @JsonProperty("Date")
    String date;

    @JsonProperty("Comments")
    String comments;

    @JsonProperty("Items")
    List<LoanItem> items;

    boolean isActive;
}
