package com.tsg.application.rest.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterResponse {
    private String status;
    private String message;
    private String username;
}
