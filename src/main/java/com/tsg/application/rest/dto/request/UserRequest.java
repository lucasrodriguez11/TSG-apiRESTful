package com.tsg.application.rest.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRequest {
    @NotBlank(message = "El username es obligatorio")
    @NotNull(message = "El username no puede ser nulo")
    @Size(min = 4, max = 50, message = "El username debe tener entre 4 y 50 caracteres")
    private String username;

    @NotBlank(message = "El email es obligatorio")
    @NotNull(message = "El email no puede ser nulo")
    @Email(message = "El email debe ser válido")
    private String email;

    // La contraseña puede ser opcional en la actualización; si se envía, se actualiza.
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    @NotNull(message = "La contraseña no puede ser nulo")
    private String password;
}
