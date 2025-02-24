package com.tsg.application.rest.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostResponse {
    private Long id;
    private String title;
    private String content;
    private Long userId;
}
