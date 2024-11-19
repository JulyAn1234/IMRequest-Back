package com.IMRequest.IMRequest.model.responses;

import com.IMRequest.IMRequest.model.entities.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
    private String Token;

    private long ExpiresIn;

    private UserResponse User;
}