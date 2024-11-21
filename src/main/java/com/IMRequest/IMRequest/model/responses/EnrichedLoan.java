package com.IMRequest.IMRequest.model.responses;

import com.IMRequest.IMRequest.model.entities.LoanItem;
import com.IMRequest.IMRequest.model.entities.User;
import com.IMRequest.IMRequest.model.entities.Warehouse;
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
public class EnrichedLoan {

    @Id
    String id;

    @JsonProperty("User")
    User user;

    @JsonProperty("Warehouse")
    Warehouse warehouse;

    @JsonProperty("Date")
    String date;

    @JsonProperty("Comments")
    String comments;

    @JsonProperty("Items")
    List<EnrichedLoanItem> items;

    boolean isActive;
}

