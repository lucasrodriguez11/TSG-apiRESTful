package com.tsg.application.rest.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostRequest {

    @NotNull(message = "El título no puede ser nulo")
    @NotBlank(message = "El título es obligatorio")
    @Size(min = 1, max = 100, message = "El título debe tener entre 1 y 100 caracteres")
    private String title;

    @NotNull(message = "El contenido no puede ser nulo")
    @NotBlank(message = "El contenido es obligatorio")
    @Size(min = 1, max = 1000, message = "El contenido debe tener entre 1 y 1000 caracteres")
    private String content;
}
